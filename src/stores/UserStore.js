import { defineStore } from 'pinia';
import axios from 'axios';
import { springAPI } from '@/services/axios';

export const useUserStore = defineStore('user', {
    state: () => ({
        accessToken: localStorage.getItem('accesstoken') || null,
        refreshToken: localStorage.getItem('refreshtoken') || null,
        isAuthenticated: !!localStorage.getItem('refreshtoken'),
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
                    localStorage.setItem('accesstoken', this.accessToken);
                    localStorage.setItem('refreshtoken', this.refreshToken);
        
                    // Axios 기본 헤더 설정
                    axios.defaults.headers.common['Authorization'] = `Bearer ${this.accessToken}`;
                } else {
                    console.error('Tokens are missing in the response headers');
                }
        
                return true;
            } catch (error) {
                console.error('Login failed:', error.response?.data || error.message);
                return false;
            }
        },
        async logout() {
            try {
                // 서버에 로그아웃 요청 (Authorization 헤더에 accessToken 포함)
                if (this.accessToken) {
                    await springAPI.post('/logout', {}, {
                        headers: {
                            Authorization: `Bearer ${this.accessToken}`,
                        },
                    });
                }
            } catch (error) {
                console.error('Server logout failed:', error.response?.data || error.message);
            } finally {
                this.accessToken = null;
                this.refreshToken = null;
                this.isAuthenticated = false;

                delete axios.defaults.headers.common['Authorization'];

                localStorage.removeItem('accesstoken');
                localStorage.removeItem('refreshtoken');
            }
        },
        initializeInterceptors() {
            springAPI.interceptors.response.use(
                (response) => response,
                async (error) => {
                    if (error.response?.status === 401 && this.refreshToken) {
                        try {
                            const res = await springAPI.post('/token/refresh', {
                                refreshToken: this.refreshToken,
                            });

                            this.accessToken = res.data.accessToken;
                            localStorage.setItem('accesstoken', res.data.accessToken);

                            axios.defaults.headers.common['Authorization'] = `Bearer ${this.accessToken}`;
                            return springAPI.request(error.config); // 원래 요청 재시도
                        } catch (err) {
                            console.error('Token refresh failed:', err);
                            this.logout();
                            return Promise.reject(err);
                        }
                    }
                    return Promise.reject(error);
                }
            );
        },
    },
});
