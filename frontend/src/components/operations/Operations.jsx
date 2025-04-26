import React, { useState } from 'react';
import './Operations.css';
import TransactionModal from './TransactionModal';
import FilterPanel from './FilterPanel';

const mockData = [
  {
    id: 1,
    personType: 'Физическое',
    datetime: '2024-03-20 14:30',
    transactionType: 'Поступление',
    comment: 'Зарплата за март',
    amount: 150000,
    status: 'Подтвержденная',
    senderBank: 'Сбербанк',
    account: '40817810099910004312',
    receiverBank: 'Тинькофф',
    receiverINN: '7710140679',
    receiverAccount: '40817810099910004312',
    category: 'Зарплата',
    receiverPhone: '+7 (999) 123-45-67'
  },
  {
    id: 2,
    personType: 'Юридическое',
    datetime: '2024-03-19 11:20',
    transactionType: 'Списание',
    comment: 'Оплата аренды офиса',
    amount: 75000,
    status: 'Новая',
    senderBank: 'Тинькофф',
    account: '40817810099910004312',
    receiverBank: 'ВТБ',
    receiverINN: '7702070139',
    receiverAccount: '40817810099910004312',
    category: 'Аренда',
    receiverPhone: '+7 (999) 765-43-21'
  },
  // Добавьте больше мокированных данных по необходимости
];

const createEmptyTransaction = () => ({
  id: Date.now(), // временный ID
  personType: 'Физическое',
  datetime: new Date().toISOString().slice(0, 16),
  transactionType: 'Поступление',
  comment: '',
  amount: 0,
  status: 'Новая',
  senderBank: '',
  account: '',
  receiverBank: '',
  receiverINN: '',
  receiverAccount: '',
  category: '',
  receiverPhone: ''
});

const Operations = () => {
  const [operations, setOperations] = useState(mockData);
  const [selectedOperation, setSelectedOperation] = useState(null);
  const [filters, setFilters] = useState({
    dateRange: { start: '', end: '' },
    transactionType: '',
    status: '',
    category: '',
    personType: '',
    amount: { min: '', max: '' },
    bank: '',
    inn: ''
  });

  const handleApplyFilters = (newFilters) => {
    setFilters(newFilters);
  };

  const handleResetFilters = () => {
    setFilters({
      dateRange: { start: '', end: '' },
      transactionType: '',
      status: '',
      category: '',
      personType: '',
      amount: { min: '', max: '' },
      bank: '',
      inn: ''
    });
  };

  const formatAmount = (amount) => {
    return new Intl.NumberFormat('ru-RU', {
      style: 'currency',
      currency: 'RUB',
      minimumFractionDigits: 0,
      maximumFractionDigits: 0
    }).format(amount);
  };

  const formatDateTime = (datetime) => {
    return new Intl.DateTimeFormat('ru-RU', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit'
    }).format(new Date(datetime));
  };

  const handleEdit = (id) => {
    const operation = operations.find(op => op.id === id);
    if (operation) {
      setSelectedOperation(operation);
    }
  };

  const handleDelete = (id) => {
    if (window.confirm('Вы уверены, что хотите удалить эту операцию?')) {
      setOperations(prev => prev.filter(op => op.id !== id));
    }
  };

  const handleDetails = (id) => {
    const operation = operations.find(op => op.id === id);
    if (operation) {
      setSelectedOperation(operation);
    }
  };

  const handleAddTransaction = () => {
    const newTransaction = createEmptyTransaction();
    setSelectedOperation(newTransaction);
  };

  const handleSave = (updatedOperation) => {
    if (operations.find(op => op.id === updatedOperation.id)) {
      // Обновление существующей операции
      setOperations(prev => 
        prev.map(op => 
          op.id === updatedOperation.id ? updatedOperation : op
        )
      );
    } else {
      // Добавление новой операции
      setOperations(prev => [...prev, updatedOperation]);
    }
    setSelectedOperation(null);
  };

  const formatPhoneNumber = (phone) => {
    // Убираем все нецифровые символы
    const cleaned = phone.replace(/\D/g, '');
    
    // Форматируем телефон: +7 (XXX) XXX-XX-XX
    const match = cleaned.match(/^7?(\d{3})(\d{3})(\d{2})(\d{2})$/);
    
    if (match) {
      return `+7 (${match[1]}) ${match[2]}-${match[3]}-${match[4]}`;
    }
    
    return phone;
  };

  const filteredOperations = operations.filter(op => {
    const dateInRange = (!filters.dateRange.start || new Date(op.datetime) >= new Date(filters.dateRange.start)) &&
                       (!filters.dateRange.end || new Date(op.datetime) <= new Date(filters.dateRange.end));
    
    const amountInRange = (!filters.amount?.min || op.amount >= Number(filters.amount.min)) &&
                         (!filters.amount?.max || op.amount <= Number(filters.amount.max));

    const bankMatches = !filters.bank || 
                       op.senderBank.toLowerCase().includes(filters.bank.toLowerCase()) ||
                       op.receiverBank.toLowerCase().includes(filters.bank.toLowerCase());

    const innMatches = !filters.inn || op.receiverINN.includes(filters.inn);
    
    return dateInRange &&
           amountInRange &&
           bankMatches &&
           innMatches &&
           (!filters.personType || op.personType === filters.personType) &&
           (!filters.transactionType || op.transactionType === filters.transactionType) &&
           (!filters.status || op.status === filters.status) &&
           (!filters.category || op.category === filters.category);
  });

  return (
    <div className="operations">
      <FilterPanel 
        onApplyFilters={handleApplyFilters}
        onResetFilters={handleResetFilters}
        onAddTransaction={handleAddTransaction}
      />

      <div className="table-container">
        <table className="operations-table">
          <thead>
            <tr>
              <th>Дата и время</th>
              <th>Тип</th>
              <th>Сумма</th>
              <th>Категория</th>
              <th>Статус</th>
              <th>Контрагент</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {filteredOperations.map(op => (
              <tr key={op.id}>
                <td>{formatDateTime(op.datetime)}</td>
                <td className={op.transactionType === 'Поступление' ? 'income' : 'expense'}>
                  {op.transactionType}
                </td>
                <td className={op.transactionType === 'Поступление' ? 'income' : 'expense'}>
                  {formatAmount(op.amount)}
                </td>
                <td>{op.category}</td>
                <td>
                  <span className={`status ${op.status.toLowerCase()}`}>
                    {op.status}
                  </span>
                </td>
                <td>{op.transactionType === 'Поступление' ? op.senderBank : op.receiverBank}</td>
                <td className="actions">
                  <button 
                    className="edit-btn"
                    onClick={() => handleEdit(op.id)}
                    title="Редактировать"
                  >
                    ✎
                  </button>
                  {op.status === 'Новая' && (
                    <button 
                      className="delete-btn"
                      onClick={() => handleDelete(op.id)}
                      title="Удалить"
                    >
                      ✕
                    </button>
                  )}
                  <button 
                    className="details-btn"
                    onClick={() => handleDetails(op.id)}
                    title="Подробнее"
                  >
                    ⋯
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {selectedOperation && (
        <TransactionModal
          transaction={selectedOperation}
          onClose={() => setSelectedOperation(null)}
          onSave={handleSave}
        />
      )}
    </div>
  );
};

export default Operations; 