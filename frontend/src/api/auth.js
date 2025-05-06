import api from './config';

export const login = async (login, password) => {
  const response = await api.post('/auth/login', {
    login,
    password
  });

  console.log('Login response:', response.data);

  const token = response.data["jwt-token"];
  console.log('Token:', token);
  localStorage.setItem('authToken', token);
  return response.data;
};

export const register = async (login, password) => {
    return api.post('/auth/register', {
      login,
      password
    });
  };