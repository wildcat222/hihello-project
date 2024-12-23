import {createPinia, defineStore} from 'pinia';
import {springAPI} from '@/services/axios';
import router from "@/router/index.js";
import {useSSEStore} from "@/stores/sse.js";

const route = router;
const pinia = createPinia()
const sseStore = useSSEStore(pinia);

export const useUserStore = defineStore('user', {
    state: () => ({
        accessToken: localStorage.getItem('accessToken') || null,
        refreshToken: localStorage.getItem('refreshToken') || null,
        isAuthenticated: !!localStorage.getItem('refreshToken'),
        employeeInfo: null,
        interceptorsInitialized: false
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

                // console.log('header : ', response.headers);
                // console.log('Access Token:', this.accessToken); // 디버깅용
                // console.log('Refresh Token:', this.refreshToken); // 디버깅용

                if (this.accessToken && this.refreshToken) {
                    this.isAuthenticated = true;

                    // 로컬 스토리지에 토큰 저장
                    localStorage.setItem('accessToken', this.accessToken);
                    localStorage.setItem('refreshToken', this.refreshToken);

                    // Axios 기본 헤더 설정
                    springAPI.defaults.headers.common['Authorization'] = `Bearer ${this.accessToken}`;
                    await sseStore.connectSSE();

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
                // console.log('엑세스 토큰 : ' + this.accessToken);
                if (!this.accessToken) {
                    console.warn("이미 로그아웃된 상태입니다.");
                    return;
                }

                try {
                    // 서버에 로그아웃 요청
                    await springAPI.post("/employee/logout");

                } catch (error) {
                    console.error('Server logout failed:', error.response?.data || error.message);
                } finally {
                    this.accessToken = null;
                    this.refreshToken = null;
                    this.isAuthenticated = false;

                    delete springAPI.defaults.headers.common['Authorization'];

                    localStorage.removeItem('accessToken');
                    localStorage.removeItem('refreshToken');

                    sseStore.disconnectSSE();

                    if (router.currentRoute.value.path !== "/") { // 이미 "/"에 있다면 이동하지 않음
                        route.push('/').then(() => {
                            alert('로그아웃 되었습니다.');
                        }).catch((error) => {
                            console.error("Navigation failed:", error);
                        });
                    }
                }
            }
            // 로그아웃 로딩 스피너 처리
            performLogout().finally(() => {
                console.log('User logged out.');
            });
        },
        initializeInterceptors() {
            // 한 번만 호출되도록 초기화 보장
            if (this.interceptorsInitialized) return;
            this.interceptorsInitialized = true;
            springAPI.interceptors.response.use(
                (response) => response,
                async (error) => {
                    console.log(error.response.data.message);
                    if (error.response?.data.message === '엑세스 토큰이 만료되었습니다.') {
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

                            return await springAPI.request;

                        } catch (err) {
                            console.error('인터셉터 과정 중 에러 발생:', err);
                            alert('세션이 만료되었습니다. 다시 로그인해주세요.');
                            await route.push("/");
                            return Promise.reject(err);
                        }
                    }
                    return Promise.reject(error);
                },
            );
        },
        getTokenPayload() {
            if (!this.accessToken) {
                // console.error("accessToken이 없어 Employee 정보를 가져올 수 없습니다.");
                return;
            }

            try {
                const base64 = this.accessToken.split('.')[1];
                const decodedToken = atob(base64);
                // console.log(decodedToken);
                // UTF-8 디코딩
                const bytes = new Uint8Array(decodedToken.split('').map(char => char.charCodeAt(0)));
                const decodedText = new TextDecoder('utf-8').decode(bytes);
                return JSON.parse(decodedText);
            } catch (error) {
                console.error("추출이 불가능합니다.", error);
                return null;
            }
        },
        getEmployeeInfo() {
            const payload = this.getTokenPayload();
            if (payload) {
                this.employeeInfo = {
                    employeeSeq: payload.sub,
                    employeeRole: payload.employeeRole,
                    employeeDepartmentName: payload.employeeDepartmentName,
                    employeePositionName: payload.employeePositionName,
                };
                return this.employeeInfo;
            }
        },

    },
});
