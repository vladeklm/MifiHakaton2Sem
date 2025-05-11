import React, { useEffect, useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import './App.css';
import MainLayout from './pages/MainLayout';
import LoginPage from './pages/authorization/LoginPage';
import RegisterPage from './pages/authorization/RegisterPage';
import Reports from './pages/reports/Reports';
import Operations from './pages/operations/Operations';
import Dashboard from './pages/dashboard/Dashboard'

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem('authToken');
    setIsAuthenticated(!!token);
  }, []);

  if (isAuthenticated === null) return null;

  return (
    <Router>
      <Routes>
        <Route path="/login" element={
          isAuthenticated ? (
            <Navigate to="/dashboard" replace />
          ) : (
            <LoginPage setIsAuthenticated={setIsAuthenticated} />
          )
        } />
        <Route path="/register" element={<RegisterPage />} />
        <Route
          path="/"
          element={
            isAuthenticated ? (
              <MainLayout setIsAuthenticated={setIsAuthenticated} />
            ) : (
              <Navigate to="/login" replace />
            )
          }
        >
          <Route index element={<Navigate to="dashboard" />} />
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/operations" element={<Operations />} />
          <Route path="/reports" element={<Reports />} />
        </Route>
      </Routes>
    </Router>
  );
}

export default App;
