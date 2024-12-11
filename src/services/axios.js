import axios from 'axios';

export const springAPI = axios.create({
  baseURL: 'http://localhost:8253:/api/v1',
});

export const lambdaAPI = axios.create({
  baseURL: 'https://kobrxuzr12.execute-api.ap-northeast-2.amazonaws.com/dev',
});
