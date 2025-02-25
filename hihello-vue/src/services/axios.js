import axios from 'axios';
import { useUserStore } from "@/stores/UserStore.js";

const baseUrl = import.meta.env.VITE_API_BASE_URL;

const springAPI = axios.create({
  baseURL: baseUrl,
  withCredentials: true,
});

// 요청 인터셉터
springAPI.interceptors.request.use(
    (config) => {
      // console.log('요청 인터셉터:', config);

      const userStore = useUserStore();
      if (userStore.accessToken) {
        config.headers.Authorization = `Bearer ${userStore.accessToken}`;
      }

      return config;
    },
    (error) => {
      console.log('요청 인터셉터 에러:', error);
      return Promise.reject(error);
    }
);

// 응답 인터셉터
springAPI.interceptors.response.use(
    (response) => {
      console.log('응답 인터셉터', response);

      return response;
    },
    async (error) => {
      console.log('응답 인터셉터 에러:', error);

      const userStore = useUserStore();

      // 토큰 만료 메세지 에러 처리
      if (error.response?.data.message === '엑세스 토큰이 만료되었습니다.') {
        try {
          const response = await springAPI.post(`/refresh`, {}, {
            withCredentials: true,
            headers: {
              'RefreshToken': `Bearer ${userStore.refreshToken}`
            }
          });
          console.log(response);

          const newAccessToken = response.headers['accesstoken'];
          const newRefreshToken = response.headers['refreshtoken'];

          if (newAccessToken && newRefreshToken) {
            console.log('새로운 토큰 수신됨');

            // 새로운 토큰으로 상태 저장
            userStore.setAccessToken(newAccessToken);
            userStore.setRefreshToken(newRefreshToken);

            // 새 토큰으로 원래 요청 재시도
            error.config.headers.Authorization = `Bearer ${newAccessToken}`;
            return springAPI(error.config);
          }
        } catch (error) {
          // 토큰 갱신 실패 시 로그아웃
          userStore.logout();
          throw error;
        }
      }

      return Promise.reject(error);
    }
);

export { springAPI }

export const lambdaAPI = axios.create({
  baseURL: import.meta.env.VITE_LAMBDA_API_BASE_URL,
});

export const fastAPI = axios.create({
  baseURL: import.meta.env.VITE_FAST_API_BASE_URL,
});
