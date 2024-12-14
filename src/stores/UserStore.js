import {defineStore} from 'pinia';
import axios from 'axios';
import {springAPI} from '@/services/axios';

export const useUserStore = defineStore('user', {
    state: () => ({
        accessToken: localStorage.getItem('accessToken') || null,
        refreshToken: localStorage.getItem('refreshToken') || null,
        isAuthenticated: !!localStorage.getItem('refreshToken'),
        employeeInfo: null
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
                        await springAPI.post('/employee/logout');
                    }
                } catch (error) {
                    console.error('Server logout failed:', error.response?.data || error.message);
                } finally {
                    this.accessToken = null;
                    this.refreshToken = null;
                    this.isAuthenticated = false;

                    delete axios.defaults.headers.common['Authorization'];

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
                                }
                            });

                            this.accessToken = res.headers['accesstoken'];
                            this.refreshToken = res.headers['refreshtoken'];

                            localStorage.setItem('accessToken', this.accessToken);
                            localStorage.setItem('refreshToken', this.refreshToken);

                            springAPI.defaults.headers.common['Authorization'] = `Bearer ${this.accessToken}`;

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
        getTokenPayload() {
            if (!this.accessToken) {
                console.error('Token이 존재하지 않습니다.');
                return null;
            }
            try {
                const payloadBase64 = this.accessToken.split('.')[1];
                const payload = JSON.parse(atob(payloadBase64));
                return payload;
            } catch (error) {
                console.error('토큰에서 Payload를 추출할 수 없습니다.', error);
                return null;
            }
        },
        getEmployeeInfo() {
            const payload = this.getTokenPayload();
            if (payload) {
                this.employeeInfo = {
                    employSeq: payload.employeeSeq,
                    employeeNum: payload.employeeNum,
                    employeeRole: payload.employeeRole,
                    departmentSeq: payload.departmentSeq
                };
                return this.employeeInfo;
            }
            console.error('Employee 정보가 존재하지 않습니다.');
            return null;
        },

    },
});
