import React, { useState } from 'react';
import './TransactionModal.css';
import InputMask from 'react-input-mask';


const TransactionModal = ({ transaction, onClose, onSave, isEditingInitial = false }) => {
  const [isEditing, setIsEditing] = useState(isEditingInitial);
  const [editedData, setEditedData] = useState(() => {
    if (!transaction) return {};
  
    return {
      ...transaction,
      dateTimeOperation: transaction.dateTimeOperation
        ? new Date(transaction.dateTimeOperation).toISOString().slice(0, 16)
        : ''
    };
  });

  console.log("transactions", transaction)

  const handleEdit = () => {
    if (isEditing) {
      onSave(editedData);
    }
    setIsEditing(!isEditing);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setEditedData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const formatDateTime = (dateTimeOperation) => {
    return new Intl.DateTimeFormat('ru-RU', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit'
    }).format(new Date(dateTimeOperation));
  };

  const formatAmount = (amount) => {
    return new Intl.NumberFormat('ru-RU', {
      style: 'currency',
      currency: 'RUB',
      minimumFractionDigits: 0,
      maximumFractionDigits: 0
    }).format(amount);
  };

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content" onClick={e => e.stopPropagation()}>
        <div className="modal-header">
          <h2>
            {transaction.operationTypeName === 'Поступление' ? '⬇' : '⬆'} 
            {' '}
            {transaction.operationTypeName}
          </h2>
          <button className="close-button" onClick={onClose}>✕</button>
        </div>

        <div className="modal-body">
          <div className="transaction-info">
            <div className="info-group">
              <h3>Основная информация</h3>
              <div className="info-row">
                <span className="label">Дата и время:</span>
                {isEditing ? (
                  <input
                    type="datetime-local"
                    name="dateTimeOperation"
                    value={editedData.dateTimeOperation}
                    onChange={handleChange}
                  />
                ) : (
                  <span className="value">{formatDateTime(transaction.dateTimeOperation)}</span>
                )}
              </div>
              <div className="info-row">
                <span className="label">Сумма:</span>
                {isEditing ? (
                  <input
                    type="number"
                    name="amount"
                    value={editedData.amount}
                    onChange={handleChange}
                    min="0"
                    step="0.01"
                  />
                ) : (
                  <span className={`value ${transaction.operationTypeName === 'Поступление' ? 'income' : 'expense'}`}>
                    {formatAmount(transaction.amount)}
                  </span>
                )}
              </div>
              <div className="info-row">
                <span className="label">Статус:</span>
                <span className={`status ${transaction.operationStatusName.toLowerCase().replace(/\s/g, '-')}`}>
                  {transaction.operationStatusName}
                </span>
              </div>
              <div className="info-row">
                <span className="label">Категория:</span>
                {isEditing ? (
                  <select
                    name="operationCategoryName"
                    value={editedData.operationCategoryName}
                    onChange={handleChange}
                  >
                    <option value="">Выберите категорию</option>
                    <option value="Зарплата">Зарплата</option>
                    <option value="Пополнение счета">Пополнение счета</option>
                    <option value="Возврат средств">Возврат средств</option>
                    <option value="Налоговый вычет">Налоговый вычет</option>
                    <option value="Перевод между счетами">Перевод между счетами</option>
                    <option value="Оплата услуг">Оплата услуг</option>
                    <option value="Кредитный платеж">Кредитный платеж</option>
                    <option value="Налоговый платеж">Налоговый платеж</option>
                  </select>
                ) : (
                  <span className="value">{transaction.operationCategoryName}</span>
                )}
              </div>
            </div>

            <div className="info-group">
              <h3>Детали контрагента</h3>
              <div className="info-row">
                <span className="label">Тип лица:</span>
                {isEditing ? (
                  <select
                    name="clientTypeName"
                    value={editedData.clientTypeName}
                    onChange={handleChange}
                  >
                    <option value="Физическое">Физическое</option>
                    <option value="Юридическое">Юридическое</option>
                  </select>
                ) : (
                  <span className="value">{transaction.clientTypeName}</span>
                )}
              </div>
              <div className="info-row">
                <span className="label">ИНН:</span>
                {isEditing ? (
                  <input
                    type="text"
                    name="inn"
                    value={editedData.inn}
                    onChange={(e) => {
                      const onlyDigits = e.target.value.replace(/\D/g, '');
                      if (onlyDigits.length <= 11) {
                        handleChange({ target: { name: 'inn', value: onlyDigits } });
                      }
                    }}
                  />
                ) : (
                  <span className="value">{transaction.inn}</span>
                )}
              </div>
              <div className="info-row">
                <span className="label">Телефон:</span>
                {isEditing ? (
                  <InputMask
                    mask="+7 (999) 999-99-99"
                    value={editedData.phoneNumber}
                    onChange={(e) => setEditedData(prev => ({ ...prev, phoneNumber: e.target.value }))}
                  >
                    {(inputProps) => <input type="tel" name="phoneNumber" {...inputProps} />}
                  </InputMask>
                ) : (
                  <span className="value">{transaction.phoneNumber}</span>
                )}
              </div>
            </div>

            <div className="info-group">
              <h3>Банковские реквизиты</h3>
              <div className="info-row">
                <span className="label">Банк отправителя:</span>
                {isEditing ? (
                  <input
                    type="text"
                    name="senderBank"
                    value={editedData.senderBank}
                    onChange={handleChange}
                  />
                ) : (
                  <span className="value">{transaction.senderBank}</span>
                )}
              </div>
              <div className="info-row">
                <span className="label">Счет отправителя:</span>
                {isEditing ? (
                  <input
                    type="text"
                    name="account"
                    value={editedData.account}
                    onChange={handleChange}
                  />
                ) : (
                  <span className="value">{transaction.account}</span>
                )}
              </div>
              <div className="info-row">
                <span className="label">Банк получателя:</span>
                {isEditing ? (
                  <input
                    type="text"
                    name="receiverBank"
                    value={editedData.receiverBank}
                    onChange={handleChange}
                  />
                ) : (
                  <span className="value">{transaction.receiverBank}</span>
                )}
              </div>
              <div className="info-row">
                <span className="label">Счет получателя:</span>
                {isEditing ? (
                  <input
                    type="text"
                    name="receiverAccount"
                    value={editedData.receiverAccount}
                    onChange={handleChange}
                  />
                ) : (
                  <span className="value">{transaction.receiverAccount}</span>
                )}
              </div>
            </div>

            <div className="info-group">
              <h3>Дополнительно</h3>
              <div className="info-row">
                <span className="label">Комментарий:</span>
                {isEditing ? (
                  <textarea
                    name="comment"
                    value={editedData.comment}
                    onChange={handleChange}
                    rows={3}
                  />
                ) : (
                  <span className="value">{transaction.comment}</span>
                )}
              </div>
            </div>
          </div>
        </div>

        <div className="modal-footer">
          {transaction.operationStatusName === 'Новая' && (
            <button 
              className={`edit-button ${isEditing ? 'save' : ''}`}
              onClick={handleEdit}
            >
              {isEditing ? 'Сохранить' : 'Редактировать'}
            </button>
          )}
          <button className="close-button" onClick={onClose}>
            Закрыть
          </button>
        </div>
      </div>
    </div>
  );
};

export default TransactionModal; 