import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { login, register } from '../../api/auth';
import './LoginPage.css';

const LoginPage = ({ setIsAuthenticated }) => {
  const [loginInput, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    console.log(loginInput, password);
    try {
      await login(loginInput, password);
      setIsAuthenticated(true);
      navigate('/dashboard');
    } catch (err) {
      setError('Неверные учетные данные');
      console.error(err);
    }
  };

  return (
    <div className="login-wrapper">
      <div className="login-container">
        <div className="login-image-section" />
        <div className="login-form-section">
          <h2>Вход</h2>
          <form onSubmit={handleSubmit}>
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
            <button type="submit">Войти</button>
            {error && <p className="error">{error}</p>}
          </form>
          <p onClick={() => navigate('/register')}>Нет аккаунта? Зарегистрироваться</p>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;