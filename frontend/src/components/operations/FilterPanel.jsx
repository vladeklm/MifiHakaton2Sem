import React, { useState } from 'react';
import './FilterPanel.css';

const FilterPanel = ({ onApplyFilters, onResetFilters, onAddTransaction }) => {
  const [dateRange, setDateRange] = useState({ start: '', end: '' });
  const [transactionType, setTransactionType] = useState('');
  const [status, setStatus] = useState('');
  const [category, setCategory] = useState('');
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
          <button className="apply-btn" onClick={handleApply}>Применить</button>
          <button className="reset-btn" onClick={handleReset}>Сбросить</button>
        </div>
      </div>

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