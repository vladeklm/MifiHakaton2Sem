import React, { useState, useEffect } from 'react';
import './Operations.css';
import TransactionModal from './TransactionModal';
import FilterPanel from './FilterPanel';
import { operationsApi } from '../../api/operations';
import ConfirmModal from './ConfirmModal';
import { getUserId } from '../../utils/authHelper';

const PAGE_SIZE = 10;

const Operations = () => {
  const [operations, setOperations] = useState([]);
  const [selectedOperation, setSelectedOperation] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [currentPage, setCurrentPage] = useState(0);
  const [filters, setFilters] = useState({
    dateRange: { start: '', end: '' },
    operationTypeName: '',
    operationStatusName: '',
    operationCategoryName: '',
    clientTypeName: '',
    amount: { min: '', max: '' },
    bank: '',
    inn: ''
  });
  const [operationToDelete, setOperationToDelete] = useState(null);
  const [banks, setBanks] = useState([]);

  useEffect(() => {
    operationsApi.getBanks()
      .then(res => setBanks(res.data))
      .catch(err => console.error('Ошибка при загрузке банков:', err));
  }, []);

  const clientId = getUserId();

  const [clientAccounts, setClientAccounts] = useState([]);
  useEffect(() => {
    const fetchClientAccounts = async () => {
      try {
        const res = await operationsApi.getClientAccounts(false);
        setClientAccounts(res.data);
      } catch (err) {
        console.error('Ошибка при загрузке счетов клиента:', err);
      }
    };
    fetchClientAccounts();
  }, []);

  const [recipientAccounts, setRecipientAccounts] = useState([]);
  useEffect(() => {
    const fetchRecipientAccounts = async () => {
      try {
        const res = await operationsApi.getClientAccounts(true);
        setRecipientAccounts(res.data);
      } catch (err) {
        console.error('Ошибка при загрузке счетов контрагента:', err);
      }
    };
    fetchRecipientAccounts();
  }, []);

  const [categories, setCategories] = useState([]);

  useEffect(() => {
    operationsApi.getCategories()
      .then(res => setCategories(res.data))
      .catch(err => console.error('Ошибка при загрузке категорий:', err));
  }, []);

  const [clientTypes, setClientTypes] = useState([]);

  useEffect(() => {
    operationsApi.getClientTypes()
      .then(res => setClientTypes(res.data))
      .catch(err => console.error('Ошибка при загрузке типов клиентов:', err));
  }, []);

  useEffect(() => {
    const fetchOperations = async () => {
      try {
        setLoading(true);
        setError(null);
        const response = await operationsApi.getOperations(clientId);
        setOperations(response.data.content);
      } catch (err) {
        setError('Ошибка при загрузке операций');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };
    fetchOperations();
  }, []);

  const handleApplyFilters = (newFilters) => {
    setFilters(newFilters);
    setCurrentPage(0);
  };

  const handleResetFilters = () => {
    setFilters({
      dateRange: { start: '', end: '' },
      operationTypeName: '',
      operationStatusName: '',
      operationCategoryName: '',
      clientTypeName: '',
      amount: { min: '', max: '' },
      bank: '',
      inn: ''
    });
    setCurrentPage(0);
  };

  const formatAmount = (amount) => {
    return new Intl.NumberFormat('ru-RU', {
      style: 'currency',
      currency: 'RUB',
      minimumFractionDigits: 2,
      maximumFractionDigits: 2
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

  const handleEdit = async (id) => {
    try {
      const response = await operationsApi.getOperation(id);
      setSelectedOperation(response.data);
    } catch (err) {
      setError('Ошибка при загрузке операции');
      console.error(err);
    }
  };

  const handleDelete = async (id) => {
    setOperationToDelete(id);
  };

  const confirmDelete = async () => {
    try {
      await operationsApi.deleteOperation(operationToDelete);
      setOperations(prev => prev.filter(op => op.id !== operationToDelete));
    } catch (err) {
      setError('Ошибка при удалении операции');
      console.error(err);
    } finally {
      setOperationToDelete(null);
    }
  };

  const handleDetails = async (id) => {
    try {
      const response = await operationsApi.getOperation(id);
      setSelectedOperation(response.data);
    } catch (err) {
      setError('Ошибка при загрузке деталей операции');
      console.error(err);
    }
  };

  const handleAddTransaction = () => {
    setSelectedOperation({
      client: 1, // TODO: заменить на реальный clientId
      clientTypeName: 'Физическое',
      dateTimeOperation: new Date().toISOString().slice(0, 16),
      operationTypeName: 'Поступление',
      comment: '',
      amount: 0,
      operationStatusName: 'Новая',
      bankFromId: null,
      bankToId: null,
      bankRecipientAccountId: null,
      bankAccount: null,
      inn: '',
      phoneNumber: '',
      operationCategoryName: ''
    });
  };

  const handleSave = async (updatedOperation) => {
    try {
      const operationToSave = {
        bankFromName: updatedOperation.bankFromName || '',
        bankToName: updatedOperation.bankToName || '',
        bankRecipientAccountNumber: updatedOperation.bankRecipientAccount || '',
        bankaccountNumber: updatedOperation.bankAccount || '',
        clientTypeName: updatedOperation.clientTypeName || '',
        operationStatusName: updatedOperation.operationStatusName || '',
        inn: updatedOperation.inn || '',
        amount: Number(updatedOperation.amount || 0),
        phoneNumber: updatedOperation.phoneNumber?.replace(/\D/g, '') || '',
        operationTypeName: updatedOperation.operationTypeName || '',
        operationCategoryName: updatedOperation.operationCategoryName || '',
        comment: updatedOperation.comment || '',
        statusName: updatedOperation.statusName || '',
        dateTimeOperation: updatedOperation.dateTimeOperation
      };
  
      console.log('Отправляется в API:', JSON.stringify(operationToSave, null, 2));
  
      if (updatedOperation.id) {
        await operationsApi.updateOperation(updatedOperation.id, operationToSave);
      } else {
        await operationsApi.createOperation(operationToSave);
      }
  
      setSelectedOperation(null);
      const response = await operationsApi.getOperations(clientId);
      setOperations(response.data.content);
    } catch (err) {
      setError('Ошибка при сохранении операции');
      console.error(err);
    }
  };

  const filteredOperations = operations.filter(op => {
    const dateInRange = (!filters.dateRange.start || new Date(op.dateTimeOperation) >= new Date(filters.dateRange.start)) &&
                       (!filters.dateRange.end || new Date(op.dateTimeOperation) <= new Date(filters.dateRange.end));
    const amountInRange = (!filters.amount?.min || op.amount >= Number(filters.amount.min)) &&
                         (!filters.amount?.max || op.amount <= Number(filters.amount.max));
    const innMatches = !filters.inn || op.inn.includes(filters.inn);
    return dateInRange &&
           amountInRange &&
           innMatches &&
           (!filters.clientTypeName || op.clientTypeName === filters.clientTypeName) &&
           (!filters.operationTypeName || op.operationTypeName === filters.operationTypeName) &&
           (!filters.operationStatusName || op.operationStatusName === filters.operationStatusName) &&
           (!filters.operationCategoryName || op.operationCategoryName === filters.operationCategoryName);
  });

  const totalPages = Math.ceil(filteredOperations.length / PAGE_SIZE);
  const paginatedOperations = filteredOperations.slice(currentPage * PAGE_SIZE, (currentPage + 1) * PAGE_SIZE);

  return (
    <div className="operations">
      {error && <div className="error-message">{error}</div>}
      {loading && <div className="loading">Загрузка...</div>}

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
              <th>Тип клиента</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {paginatedOperations.map(op => (
              <tr key={op.id}>
                <td>{formatDateTime(op.dateTimeOperation)}</td>
                <td className={op.operationTypeName === 'Поступление' ? 'income' : 'expense'}>
                  {op.operationTypeName}
                </td>
                <td className={op.operationTypeName === 'Поступление' ? 'income' : 'expense'}>
                  {formatAmount(op.amount)}
                </td>
                <td>{op.operationCategoryName}</td>
                <td>
                  <span className={`status ${op.operationStatusName.toLowerCase().replace(/\s/g, '-')}`}>
                    {op.operationStatusName}
                  </span>
                </td>
                <td>{op.clientTypeName}</td>
                <td className="actions">
                  {op.operationStatusName === 'Новая' && (
                    <button 
                      className="edit-btn"
                      onClick={() => handleEdit(op.id)}
                      title="Редактировать"
                    >
                      ✎
                    </button>
                  )}
                  {op.operationStatusName === 'Новая' && (
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

      <div className="pagination">
        <button 
          onClick={() => setCurrentPage(prev => Math.max(0, prev - 1))}
          disabled={currentPage === 0}
        >
          Назад
        </button>
        <span>Страница {currentPage + 1} из {totalPages}</span>
        <button 
          onClick={() => setCurrentPage(prev => Math.min(totalPages - 1, prev + 1))}
          disabled={currentPage === totalPages - 1 || totalPages === 0}
        >
          Вперед
        </button>
      </div>

      {selectedOperation && (
        <TransactionModal
          transaction={selectedOperation}
          onClose={() => setSelectedOperation(null)}
          onSave={handleSave}
          isEditingInitial={!selectedOperation.id}
          banks={banks}
          clientAccounts={clientAccounts}
          recipientAccounts={recipientAccounts}
          categories={categories}
          clientTypes={clientTypes}
        />
      )}

      {operationToDelete !== null && (
        <ConfirmModal
          message="Вы уверены, что хотите удалить эту операцию?"
          onConfirm={confirmDelete}
          onCancel={() => setOperationToDelete(null)}
        />
      )}
    </div>
  );
};

export default Operations;