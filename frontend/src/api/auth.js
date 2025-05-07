import api from './config';

export const login = async (login, password) => {
  const response = await api.post('/auth/login', {
    login,
    password
  });

  console.log('Login response:', response.data);

  const token = response.data["jwt-token"];
  localStorage.setItem('authToken', token);
  return response.data;
};

export const register = async (login, password, firstName, patronymic, secondName) => {
    return api.post('/auth/register', {
      login,
      password,
      firstName,
      patronymic,
      secondName
    });
  };