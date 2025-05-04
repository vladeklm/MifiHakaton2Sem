import React from "react";
import GridLayout from "react-grid-layout";
import "react-grid-layout/css/styles.css";
import "react-resizable/css/styles.css";
import "./Dashboard.css";
import { Doughnut } from "react-chartjs-2";
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from "chart.js";

ChartJS.register(ArcElement, Tooltip, Legend);

const Dashboard = () => {
  const income = 800000;
  const expenses = 600000;
  const balance = income - expenses;

  const layout = [
    { i: "income", x: 0, y: 0, w: 4, h: 2, minW: 2, minH: 1 },
    { i: "expenses", x: 4, y: 0, w: 4, h: 2, minW: 3, minH: 2 },
    { i: "balance", x: 8, y: 0, w: 4, h: 2, minW: 3, minH: 2 },
    { i: "chart", x: 0, y: 2, w: 12, h: 4, minW: 6, minH: 4 },
  ];

  const chartData = {
    labels: ["Доходы", "Расходы"],
    datasets: [
      {
        data: [income, expenses],
        backgroundColor: ["#4CAF50", "#F44336"],
        hoverOffset: 4,
      },
    ],
  };

  return (
    <div style={{ padding: "20px", maxWidth: "1400px", margin: "0 auto" }}>
      <h2>Дашборд</h2>
      <GridLayout
        className="layout"
        layout={layout}
        cols={12}
        rowHeight={100}
        width={1200}
        isDraggable
        isResizable
        preventCollision={true}
        compactType="vertical"
      >
        <div key="income" className="card green">
          <h4>Всего поступлений</h4>
          <p>+{income.toLocaleString()} ₽</p>
        </div>

        <div key="expenses" className="card red">
          <h4>Всего расходов</h4>
          <p>{expenses.toLocaleString()} ₽</p>
        </div>

        <div key="balance" className="card blue">
          <h4>Баланс</h4>
          <p>{balance.toLocaleString()} ₽</p>
        </div>

        <div key="chart" className="card chart">
          <h4>Соотношение доходов и расходов</h4>
          <div style={{ width: "100%", height: "300px" }}>
            <Doughnut
              data={chartData}
              options={{
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                  legend: { position: "bottom" },
                },
              }}
            />
          </div>
        </div>
      </GridLayout>
    </div>
  );
};

export default Dashboard;
