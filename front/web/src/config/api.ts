import axios from 'axios';

//delete api.defaults.headers.common['Authorization'];
export const api = axios.create({
  baseURL: process.env.REACT_APP_API,
  timeout: 10000, // se backend demorar mais de 10s para responder, a requisição será cancelada
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('@AppJuridico:token');
  if (token && config.headers) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

api.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (error.response && error.response.status === 401) {
      // token expirou ou é inválido, então desloga o usuário.
      localStorage.removeItem('@AppJuridico:token');
      localStorage.removeItem('@AppJuridico:user');

      if (window.location.pathname !== '/auth/login') {
        window.location.href = '/auth/login';
      }

      return Promise.reject(error);
    }
  },
);
