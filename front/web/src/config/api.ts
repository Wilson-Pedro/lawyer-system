import axios from 'axios';

export const api = axios.create({
  baseURL: process.env.REACT_APP_API,
});

// Intercepta TODAS as requisições antes de saírem e injeta o token
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token && config.headers) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});
