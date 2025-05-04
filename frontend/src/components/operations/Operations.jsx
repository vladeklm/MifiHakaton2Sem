import React, { useState, useEffect } from 'react';
import './Operations.css';
import TransactionModal from './TransactionModal';
import FilterPanel from './FilterPanel';
import { operationsApi } from '../../api/operations';
import ConfirmModal from './ConfirmModal';

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

  useEffect(() => {
    const fetchOperations = async () => {
      try {
        setLoading(true);
        setError(null);
        const response = await operationsApi.getOperations(1); // TODO: заменить 1 на реальный clientId
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
      setOperationToDelete(null); // закрываем модалку
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
      bankAccountId: null,
      inn: '',
      phoneNumber: '',
      operationCategoryName: ''
    });
  };

  const handleSave = async (updatedOperation) => {
    const categoryMap = {
      'Зарплата': 1,
      'Пополнение счета': 2,
      'Возврат средств': 3,
      'Налоговый вычет': 4,
      'Перевод между счетами': 5,
      'Оплата услуг': 6,
      'Кредитный платеж': 7,
      'Налоговый платеж': 8
    };
  
    const typeMap = {
      'Поступление': 1,
      'Списание': 2
    };
  
    const statusMap = {
      'Новая': 1,
      'Подтвержденная': 2,
      'В обработке': 3,
      'Отменена': 4,
      'Платеж выполнен': 5,
      'Платеж удален': 6,
      'Возврат': 7
    };
  
    const clientTypeMap = {
      'Физическое': 1,
      'Юридическое': 2
    };
  
    try {
      const operationToSave = {
        client: {
          user: {
            id: updatedOperation.client?.user?.id || updatedOperation.clientUserId || 1
          }
        },
        operationType: { id: typeMap[updatedOperation.operationTypeName] },
        operationCategory: { id: categoryMap[updatedOperation.operationCategoryName] },
        operationStatus: { id: statusMap[updatedOperation.operationStatusName] },
        clientType: { id: clientTypeMap[updatedOperation.clientTypeName] },
        dateTimeOperation: updatedOperation.dateTimeOperation,
        amount: Number(updatedOperation.amount),
        comment: updatedOperation.comment,
        phoneNumber: updatedOperation.phoneNumber.replace(/\D/g, ''),
        inn: updatedOperation.inn,
        bankFromId: updatedOperation.bankFromId,
        bankToId: updatedOperation.bankToId,
        bankRecipientAccountId: updatedOperation.bankRecipientAccountId,
        bankAccount: updatedOperation.bankAccountId ? { id: updatedOperation.bankAccountId } : null,
      };

      console.log('Отправляется в API:', JSON.stringify(operationToSave, null, 2));

      if (updatedOperation.id) {
        await operationsApi.updateOperation(updatedOperation.id, operationToSave);
      } else {
        await operationsApi.createOperation(operationToSave);
      }
      setSelectedOperation(null);
      const response = await operationsApi.getOperations(1);
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