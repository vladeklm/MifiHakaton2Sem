import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { login, register } from '../../api/auth';
import './LoginPage.css';

const LoginPage = () => {
  const [loginInput, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [isRegistering, setIsRegistering] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    console.log(loginInput, password);
    try {
      await login(loginInput, password);
      navigate('/');
    } catch (err) {
      setError('Неверные учетные данные');
      console.error(err);
    }
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    setError('');
    try {
      await register(loginInput, password);
      // Можешь сразу авторизовать или перевести в режим логина:
      setIsRegistering(false);
    } catch (err) {
      setError('Ошибка при регистрации. Возможно, пользователь уже существует.');
      console.error(err);
    }
  };

  return (
    <div className="login-wrapper">
      <div className={`login-container ${isRegistering ? 'register' : ''}`}>
        <div className="login-image-section" />
        <div className="login-form-section">
          <h2>{isRegistering ? 'Регистрация' : 'Вход'}</h2>
          <form onSubmit={isRegistering ? handleRegister : handleSubmit}>
            <input
              type="text"
              placeholder="Логин"
              value={loginInput}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
            <input
              type="password"
              placeholder="Пароль"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
            <button type="submit">{isRegistering ? 'Зарегистрироваться' : 'Войти'}</button>
            {error && <p className="error">{error}</p>}
          </form>
          <p onClick={() => setIsRegistering(!isRegistering)}>
            {isRegistering ? 'Уже есть аккаунт? Войти' : 'Нет аккаунта? Зарегистрироваться'}
          </p>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;