import axios from 'axios';

export const springAPI = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  headers: {
    Authorization: `Bearer ${localStorage.getItem('accessToken')}`
  }
});

export const lambdaAPI = axios.create({
  baseURL: import.meta.env.VITE_LAMBDA_API_BASE_URL,
});

export const fastAPI = axios.create({
  baseURL: import.meta.env.VITE_FAST_API_BASE_URL,
})
