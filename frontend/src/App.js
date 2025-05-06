import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import './App.css';
import MainLayout from './components/MainLayout';
import LoginPage from './pages/authorization/LoginPage';
import RegisterPage from './pages/authorization/RegisterPage';
import Operations from './components/operations/Operations';
import Dashboard from './pages/Dashboard'

function App() {
  const isAuthenticated = !!localStorage.getItem('authToken');

  return (
    <Router>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route
          path="/"
          element={isAuthenticated ? <MainLayout /> : <Navigate to="/login" />}
        >
          <Route path="dashboard" element={<Dashboard />} />
          <Route path="operations" element={<Operations />} />
          {/* можно добавить остальные вкладки */}
          <Route index element={<Navigate to="dashboard" />} />
        </Route>
      </Routes>
    </Router>
  );
}

export default App;
