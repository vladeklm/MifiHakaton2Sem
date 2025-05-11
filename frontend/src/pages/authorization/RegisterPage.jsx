import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { register } from '../../api/auth';
import './LoginPage.css';

const RegisterPage = () => {
  const [login, setLogin] = useState('');
  const [password, setPassword] = useState('');
  const [firstName, setFirstName] = useState('');
  const [patronymic, setPatronymic] = useState('');
  const [secondName, setSecondName] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    try {
      await register(login, password, firstName, patronymic, secondName);
      setSuccess(true);
      setTimeout(() => navigate('/login'), 2000);
    } catch (err) {
      setError('Ошибка регистрации. Пользователь уже существует или данные некорректны.');
    }
  };

  return (
    <div className="login-wrapper">
      <div className="login-container">
        <div className="login-image-section" />
        <div className="login-form-section">
          <h2>Регистрация</h2>
          <form onSubmit={handleSubmit}>
          <input
              type="text"
              placeholder="Имя"
              value={firstName}
              onChange={(e) => setFirstName(e.target.value)}
              required
            />
            <input
              type="text"
              placeholder="Фамилия"
              value={secondName}
              onChange={(e) => setSecondName(e.target.value)}
              required
            />
            <input
              type="text"
              placeholder="Отчество (необязательно)"
              value={patronymic}
              onChange={(e) => setPatronymic(e.target.value)}
            />
            <input
              type="text"
              placeholder="Логин"
              value={login}
              onChange={(e) => setLogin(e.target.value)}
              required
            />
            <input
              type="password"
              placeholder="Пароль"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
            <button type="submit">Зарегистрироваться</button>
            {error && <p className="error">{error}</p>}
            {success && <p className="success">Успешно! Перенаправление...</p>}
          </form>
          <p onClick={() => navigate('/login')}>Уже есть аккаунт? Войти</p>
        </div>
      </div>
    </div>
  );
};

export default RegisterPage;