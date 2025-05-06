import axios from 'axios';

const api = axios.create({
    baseURL: process.env.NODE_ENV === 'development'
        ? 'http://localhost:8080/api/v1'
        : '/api/v1',
  headers: {
    'Content-Type': 'application/json'
  }
});

// interceptor для обработки ошибок
api.interceptors.response.use(
  response => response,
  error => {
    if (error.response) {
      // Обработка ошибок от сервера
      console.error('API Error:', error.response.data);
    } else if (error.request) {
      // Ошибка запроса
      console.error('Request Error:', error.request);
    } else {
      // Ошибка настройки запроса
      console.error('Error:', error.message);
    }
    return Promise.reject(error);
  }
);

api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('authToken');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export default api;