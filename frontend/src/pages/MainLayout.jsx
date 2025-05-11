import React, { useState } from "react";
import "./MainLayout.css";
import { NavLink, Outlet, useNavigate } from "react-router-dom";

const menuItems = [
  { id: "dashboard", label: "Дашборд", path: "/dashboard" },
  { id: "operations", label: "Операции", path: "/operations" },
  { id: "reports", label: "Отчеты", path: "/reports" },
  { id: "references", label: "Справочники", path: "/references" },
  { id: "settings", label: "Настройки", path: "/settings" },
];

const MainLayout = ({ setIsAuthenticated }) => {

  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("authToken");
    setIsAuthenticated(false);
    navigate("/login");
  };

  return (
    <div className="main-layout">
      <aside className="sidebar">
        <div className="logo">
          <span>Финансовый менеджер</span>
        </div>
        <nav className="side-nav">
          {menuItems.map((item) => (
            <NavLink
              key={item.id}
              to={item.path}
              className={({ isActive }) => `nav-button ${isActive ? "active" : ""}`}
            >
              {item.label}
            </NavLink>
          ))}
        </nav>
      </aside>
      <div className="content-wrapper">
        <header className="main-header">
          <h1 className="page-title">Финансовый менеджер</h1>
          <button className="logout-button" onClick={handleLogout}>
            <span>Выйти</span>
          </button>
        </header>
        <main className="main-content">
          <Outlet />
        </main>
      </div>
    </div>
  );
};

export default MainLayout;