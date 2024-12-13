import { defineStore } from 'pinia';
import axios from 'axios';
import { springAPI } from '@/services/axios';

export const useUserStore = defineStore('user', {
    state: () => ({
        accessToken: localStorage.getItem('accessToken') || null,
        refreshToken: localStorage.getItem('refreshToken') || null,
        isAuthenticated: !!localStorage.getItem('refreshToken'),
    }),
    actions: {
        async login(employeeNum, employeePassword) {
            try {
                const response = await springAPI.post('/login', {
                    employeeNum,
                    employeePassword,
                });
        
                // 응답 헤더에서 토큰 가져오기
                this.accessToken = response.headers['accesstoken'];
                this.refreshToken = response.headers['refreshtoken'];
        
                console.log('header : ', response.headers);
                console.log('Access Token:', this.accessToken); // 디버깅용
                console.log('Refresh Token:', this.refreshToken); // 디버깅용
        
                if (this.accessToken && this.refreshToken) {
                    this.isAuthenticated = true;
        
                    // 로컬 스토리지에 토큰 저장
                    localStorage.setItem('accessToken', this.accessToken);
                    localStorage.setItem('refreshToken', this.refreshToken);
        
                    // Axios 기본 헤더 설정
                    axios.defaults.headers.common['Authorization'] = `Bearer ${this.accessToken}`;
                    axios.defaults.headers.common['tokenType'] = 'access'
                } else {
                    console.error('Tokens are missing in the response headers');
                }
        
                return true;
            } catch (error) {
                console.error('Login failed:', error.response?.data || error.message);
                return false;
            }
        },
        logout() {
            const performLogout = async () => {
                try {
                    // 서버에 로그아웃 요청
                    if (this.accessToken) {
                        await springAPI.post('/logout');
                    }
                } catch (error) {
                    console.error('Server logout failed:', error.response?.data || error.message);
                } finally {
                    this.accessToken = null;
                    this.refreshToken = null;
                    this.isAuthenticated = false;

                    delete axios.defaults.headers.common['Authorization'];
                    delete axios.defaults.headers.common['tokenType'];

                    localStorage.removeItem('accessToken');
                    localStorage.removeItem('refreshToken');
                }
            }
            // 로그아웃 로딩 스피너 처리
            performLogout().finally(() => {
                console.log('User logged out.');
            });
        },
        initializeInterceptors() {
            springAPI.interceptors.response.use(
                (response) => response,
                async (error) => {
                    if (error.response?.status === 401 && this.refreshToken) {
                        try {
                            const res = await springAPI.request({
                                ...error.config,
                                headers: {
                                    RefreshToken: `Bearer ${this.refreshToken}`,
                                    tokenType: 'refresh'
                                }
                            });

                            this.accessToken = res.headers['accesstoken'];
                            this.refreshToken = res.headers['refreshtoken'];

                            localStorage.setItem('accessToken', this.accessToken);
                            localStorage.setItem('refreshToken', this.refreshToken);

                            springAPI.defaults.headers.common['Authorization'] = `Bearer ${this.accessToken}`;
                            springAPI.defaults.headers.common['tokenType'] = 'access';

                            return res;

                        } catch (err) {
                            console.error('Token refresh failed:', err);
                            this.logout();
                            alert('세션이 만료되었습니다. 다시 로그인해주세요.');
                            return Promise.reject(err);
                        }
                    }
                    return Promise.reject(error);
                }
            );
        },
    },
});
