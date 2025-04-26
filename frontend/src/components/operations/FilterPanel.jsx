import React, { useState } from 'react';
import './FilterPanel.css';

const FilterPanel = ({ onApplyFilters, onResetFilters, onAddTransaction }) => {
  const [dateRange, setDateRange] = useState({ start: '', end: '' });
  const [transactionType, setTransactionType] = useState('');
  const [status, setStatus] = useState('');
  const [category, setCategory] = useState('');
  const [showAdvanced, setShowAdvanced] = useState(false);
  const [advancedFilters, setAdvancedFilters] = useState({
    bank: '',
    inn: '',
    amount: { min: '', max: '' }
  });

  const handleApply = () => {
    onApplyFilters({
      dateRange,
      transactionType,
      status,
      category,
      ...advancedFilters
    });
  };

  const handleReset = () => {
    setDateRange({ start: '', end: '' });
    setTransactionType('');
    setStatus('');
    setCategory('');
    setAdvancedFilters({
      bank: '',
      inn: '',
      amount: { min: '', max: '' }
    });
    onResetFilters();
  };

  const handleAdvancedFilterChange = (e) => {
    const { name, value } = e.target;
    if (name === 'min' || name === 'max') {
      setAdvancedFilters(prev => ({
        ...prev,
        amount: {
          ...prev.amount,
          [name]: value
        }
      }));
    } else {
      setAdvancedFilters(prev => ({
        ...prev,
        [name]: value
      }));
    }
  };

  const handleAmountChange = (e) => {
    const { name, value } = e.target;
    const numValue = value === '' ? '' : Number(value);

    if (name === 'min') {
      if (advancedFilters.amount.max !== '' && numValue > Number(advancedFilters.amount.max)) {
        // Если минимальная сумма больше максимальной, очищаем максимальную
        setAdvancedFilters(prev => ({
          ...prev,
          amount: { min: value, max: '' }
        }));
      } else {
        setAdvancedFilters(prev => ({
          ...prev,
          amount: { ...prev.amount, min: value }
        }));
      }
    } else {
      if (advancedFilters.amount.min !== '' && numValue < Number(advancedFilters.amount.min)) {
        // Если максимальная сумма меньше минимальной, очищаем минимальную
        setAdvancedFilters(prev => ({
          ...prev,
          amount: { min: '', max: value }
        }));
      } else {
        setAdvancedFilters(prev => ({
          ...prev,
          amount: { ...prev.amount, max: value }
        }));
      }
    }
  };

  const handleDateChange = (e, field) => {
    const { value } = e.target;
    
    if (field === 'start') {
      if (dateRange.end && value > dateRange.end) {
        setDateRange({ start: value, end: '' });
      } else {
        setDateRange({ ...dateRange, start: value });
      }
    } else {
      if (dateRange.start && value < dateRange.start) {
        setDateRange({ start: '', end: value });
      } else {
        setDateRange({ ...dateRange, end: value });
      }
    }
  };

  return (
    <div className="filter-panel">
      <div className="filters-container">
        <div className="filter-group">
          <div className="date-range">
            <input
              type="date"
              value={dateRange.start}
              onChange={(e) => handleDateChange(e, 'start')}
              max={dateRange.end || undefined}
              placeholder="От"
            />
            <span className="date-separator">—</span>
            <input
              type="date"
              value={dateRange.end}
              onChange={(e) => handleDateChange(e, 'end')}
              min={dateRange.start || undefined}
              placeholder="До"
            />
          </div>
        </div>

        <div className="filter-group">
          <select
            value={transactionType}
            onChange={(e) => setTransactionType(e.target.value)}
          >
            <option value="">Тип операции</option>
            <option value="Поступление">Поступление</option>
            <option value="Списание">Списание</option>
          </select>
        </div>

        <div className="filter-group">
          <select
            value={status}
            onChange={(e) => setStatus(e.target.value)}
          >
            <option value="">Статус</option>
            <option value="Новая">Новая</option>
            <option value="Подтвержденная">Подтвержденная</option>
          </select>
        </div>

        <div className="filter-group">
          <select
            value={category}
            onChange={(e) => setCategory(e.target.value)}
          >
            <option value="">Категория</option>
            <option value="Зарплата">Зарплата</option>
            <option value="Аренда">Аренда</option>
            <option value="Услуги">Услуги</option>
          </select>
        </div>

        <div className="filter-actions">
          <button 
            className={`advanced-filters-btn ${showAdvanced ? 'active' : ''}`}
            onClick={() => setShowAdvanced(!showAdvanced)}
          >
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M12 15C13.6569 15 15 13.6569 15 12C15 10.3431 13.6569 9 12 9C10.3431 9 9 10.3431 9 12C9 13.6569 10.3431 15 12 15Z" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
              <path d="M19.4 15C19.1277 15.6171 19.2583 16.3378 19.73 16.82L19.79 16.88C20.1656 17.2551 20.3766 17.7642 20.3766 18.295C20.3766 18.8258 20.1656 19.3349 19.79 19.71C19.4149 20.0856 18.9058 20.2966 18.375 20.2966C17.8442 20.2966 17.3351 20.0856 16.96 19.71L16.9 19.65C16.4178 19.1783 15.6971 19.0477 15.08 19.32C14.4755 19.5791 14.0826 20.1724 14.08 20.83V21C14.08 22.1046 13.1846 23 12.08 23C10.9754 23 10.08 22.1046 10.08 21V20.91C10.0642 20.2327 9.63587 19.6339 9 19.4C8.38291 19.1277 7.66219 19.2583 7.18 19.73L7.12 19.79C6.74485 20.1656 6.23582 20.3766 5.705 20.3766C5.17418 20.3766 4.66515 20.1656 4.29 19.79C3.91435 19.4149 3.70336 18.9058 3.70336 18.375C3.70336 17.8442 3.91435 17.3351 4.29 16.96L4.35 16.9C4.82167 16.4178 4.95231 15.6971 4.68 15.08C4.42093 14.4755 3.82764 14.0826 3.17 14.08H3C1.89543 14.08 1 13.1846 1 12.08C1 10.9754 1.89543 10.08 3 10.08H3.09C3.76733 10.0642 4.36613 9.63587 4.6 9C4.87231 8.38291 4.74167 7.66219 4.27 7.18L4.21 7.12C3.83435 6.74485 3.62336 6.23582 3.62336 5.705C3.62336 5.17418 3.83435 4.66515 4.21 4.29C4.58515 3.91435 5.09418 3.70336 5.625 3.70336C6.15582 3.70336 6.66485 3.91435 7.04 4.29L7.1 4.35C7.58219 4.82167 8.30291 4.95231 8.92 4.68H9C9.60447 4.42093 9.99738 3.82764 10 3.17V3C10 1.89543 10.8954 1 12 1C13.1046 1 14 1.89543 14 3V3.09C14.0026 3.74764 14.3955 4.34093 15 4.6C15.6171 4.87231 16.3378 4.74167 16.82 4.27L16.88 4.21C17.2551 3.83435 17.7642 3.62336 18.295 3.62336C18.8258 3.62336 19.3349 3.83435 19.71 4.21C20.0856 4.58515 20.2966 5.09418 20.2966 5.625C20.2966 6.15582 20.0856 6.66485 19.71 7.04L19.65 7.1C19.1783 7.58219 19.0477 8.30291 19.32 8.92V9C19.5791 9.60447 20.1724 9.99738 20.83 10H21C22.1046 10 23 10.8954 23 12C23 13.1046 22.1046 14 21 14H20.91C20.2524 14.0026 19.6591 14.3955 19.4 15Z" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
            </svg>
          </button>
          <button className="apply-btn" onClick={handleApply}>Применить</button>
          <button className="reset-btn" onClick={handleReset}>Сбросить</button>
        </div>
      </div>

      {showAdvanced && (
        <div className="advanced-filters">
          <div className="filter-group">
            <input
              type="text"
              name="bank"
              value={advancedFilters.bank}
              onChange={handleAdvancedFilterChange}
              placeholder="Банк"
            />
          </div>
          <div className="filter-group">
            <input
              type="text"
              name="inn"
              value={advancedFilters.inn}
              onChange={handleAdvancedFilterChange}
              placeholder="ИНН"
            />
          </div>
          <div className="filter-group amount-range">
            <input
              type="number"
              name="min"
              value={advancedFilters.amount.min}
              onChange={handleAmountChange}
              placeholder="Сумма от"
              min="0"
              max={advancedFilters.amount.max || undefined}
            />
            <span className="amount-separator">—</span>
            <input
              type="number"
              name="max"
              value={advancedFilters.amount.max}
              onChange={handleAmountChange}
              placeholder="до"
              min={advancedFilters.amount.min || 0}
            />
          </div>
        </div>
      )}

      <button className="add-transaction-btn" onClick={onAddTransaction}>
        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
          <path d="M12 5V19M5 12H19" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
        </svg>
        Добавить операцию
      </button>
    </div>
  );
};

export default FilterPanel; 