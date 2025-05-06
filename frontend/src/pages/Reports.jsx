import React, { useEffect, useState } from "react";
import { operationsApi } from "../api/operations";
import FilterPanel from "../components/operations/FilterPanel";
import "./Reports.css";
import * as XLSX from "xlsx";
import jsPDF from "jspdf";
import "jspdf-autotable";

const Reports = () => {
  const [operations, setOperations] = useState([]);
  const [filtered, setFiltered] = useState([]);
  const [filters, setFilters] = useState({});
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const load = async () => {
      setLoading(true);
      try {
        const res = await operationsApi.getOperations(1);
        setOperations(res.data.content || []);
      } catch (err) {
        console.error("Ошибка при загрузке операций:", err);
      } finally {
        setLoading(false);
      }
    };
    load();
  }, []);

  useEffect(() => {
    const applyFilters = () => {
      let result = [...operations];

      if (filters.dateRange) {
        const { start, end } = filters.dateRange;
        if (start)
          result = result.filter(
            (op) => new Date(op.dateTimeOperation) >= new Date(start)
          );
        if (end)
          result = result.filter(
            (op) => new Date(op.dateTimeOperation) <= new Date(end)
          );
      }

      if (filters.transactionType)
        result = result.filter(
          (op) => op.operationTypeName === filters.transactionType
        );

      if (filters.status)
        result = result.filter(
          (op) => op.operationStatusName === filters.status
        );

      if (filters.category)
        result = result.filter(
          (op) => op.operationCategoryName === filters.category
        );

      if (filters.inn)
        result = result.filter((op) => op.inn?.includes(filters.inn));

      if (filters.bank)
        result = result.filter(
          (op) =>
            op.bankFromId?.toString().includes(filters.bank) ||
            op.bankToId?.toString().includes(filters.bank)
        );

      if (filters.amount) {
        const { min, max } = filters.amount;
        if (min) result = result.filter((op) => op.amount >= Number(min));
        if (max) result = result.filter((op) => op.amount <= Number(max));
      }

      setFiltered(result);
    };

    applyFilters();
  }, [operations, filters]);

  const handleApplyFilters = (f) => setFilters(f);
  const handleResetFilters = () => setFilters({});

  const formatAmount = (amount) => {
    return new Intl.NumberFormat("ru-RU", {
      style: "currency",
      currency: "RUB",
      minimumFractionDigits: 2,
      maximumFractionDigits: 2,
    }).format(amount);
  };

  const formatDateTime = (datetime) => {
    return new Intl.DateTimeFormat("ru-RU", {
      year: "numeric",
      month: "2-digit",
      day: "2-digit",
      hour: "2-digit",
      minute: "2-digit",
    }).format(new Date(datetime));
  };

  const handleExportExcel = () => {
    const data = filtered.map((op) => ({
      "Дата и время": formatDateTime(op.dateTimeOperation),
      Тип: op.operationTypeName,
      Сумма: op.amount,
      Категория: op.operationCategoryName,
      Статус: op.operationStatusName,
      "Тип клиента": op.clientTypeName,
    }));
    const worksheet = XLSX.utils.json_to_sheet(data);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, "Отчет");
    XLSX.writeFile(workbook, "отчет.xlsx");
  };

  const handleExportPDF = () => {
    const doc = new jsPDF();
    const tableColumn = [
      "Дата и время",
      "Тип",
      "Сумма",
      "Категория",
      "Статус",
      "Тип клиента",
    ];
    const tableRows = filtered.map((op) => [
      formatDateTime(op.dateTimeOperation),
      op.operationTypeName,
      formatAmount(op.amount),
      op.operationCategoryName,
      op.operationStatusName,
      op.clientTypeName,
    ]);
    doc.autoTable({ head: [tableColumn], body: tableRows });
    doc.save("отчет.pdf");
  };

  return (
    <div className="operations">
      {loading && <div className="loading">Загрузка...</div>}

      <FilterPanel
        onApplyFilters={handleApplyFilters}
        onResetFilters={handleResetFilters}
      />

      <div
        className="report-actions"
        style={{ display: "flex", gap: "1rem", marginBottom: "1rem" }}
      >
        <button className="apply-btn" onClick={handleExportExcel}>
          Выгрузить в Excel
        </button>
        <button className="apply-btn" onClick={handleExportPDF}>
          Выгрузить в PDF
        </button>
      </div>

      <div className="table-container">
        <table className="operations-table">
          <thead>
            <tr>
              <th>Дата и время</th>
              <th>Тип</th>
              <th>Сумма</th>
              <th>Категория</th>
              <th>Статус</th>
              <th>Тип клиента</th>
            </tr>
          </thead>
          <tbody>
            {filtered.map((op) => (
              <tr key={op.id}>
                <td>{formatDateTime(op.dateTimeOperation)}</td>
                <td
                  className={
                    op.operationTypeName === "Поступление"
                      ? "income"
                      : "expense"
                  }
                >
                  {op.operationTypeName}
                </td>
                <td
                  className={
                    op.operationTypeName === "Поступление"
                      ? "income"
                      : "expense"
                  }
                >
                  {formatAmount(op.amount)}
                </td>
                <td>{op.operationCategoryName}</td>
                <td>
                  <span
                    className={`status ${op.operationStatusName
                      .toLowerCase()
                      .replace(/\s/g, "-")}`}
                  >
                    {op.operationStatusName}
                  </span>
                </td>
                <td>{op.clientTypeName}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Reports;
