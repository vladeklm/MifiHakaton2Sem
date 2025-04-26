import React, { useState } from "react";
import "./MainLayout.css";
import Dashboard from "../pages/Dashboard";
import Operations from "./operations/Operations";

const MainLayout = () => {
  const [activeTab, setActiveTab] = useState("dashboard");

  const menuItems = [
    { id: "dashboard", label: "Дашборд" },
    { id: "operations", label: "Операции" },
    { id: "reports", label: "Отчеты" },
    { id: "references", label: "Справочники" },
    { id: "settings", label: "Настройки" },
  ];

  const renderContent = () => {
    switch (activeTab) {
      case "dashboard":
        return <Dashboard />;
      case "operations":
        return <Operations />
      case "reports":
        return <div className="placeholder">Страница отчетов</div>;
      case "references":
        return <div className="placeholder">Страница справочников</div>;
      case "settings":
        return <div className="placeholder">Страница настроек</div>;
      default:
      // return <Dashboard />;
    }
  };

  return (
    <div className="main-layout">
      <aside className="sidebar">
        <div className="logo">
          <span>Финансовый менеджер</span>
        </div>
        <nav className="side-nav">
          {menuItems.map((item) => (
            <button
              key={item.id}
              className={`nav-button ${activeTab === item.id ? "active" : ""}`}
              onClick={() => setActiveTab(item.id)}
            >
              {item.label}
            </button>
          ))}
        </nav>
      </aside>
      <div className="content-wrapper">
        <header className="main-header">
          <h1 className="page-title">
            {menuItems.find((item) => item.id === activeTab)?.label}
          </h1>
          <button className="logout-button">
            <span>Выйти</span>
          </button>
        </header>
        <main className="main-content">{renderContent()}</main>
      </div>
    </div>
  );
};

export default MainLayout;