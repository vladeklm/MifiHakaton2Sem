import React, { useState } from 'react';
import './TransactionModal.css';

const TransactionModal = ({ transaction, onClose, onSave }) => {
  const [isEditing, setIsEditing] = useState(false);
  const [editedData, setEditedData] = useState({
    ...transaction,
    datetime: new Date(transaction.datetime).toISOString().slice(0, 16)
  });

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

  const formatDateTime = (datetime) => {
    return new Intl.DateTimeFormat('ru-RU', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit'
    }).format(new Date(datetime));
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
            {transaction.transactionType === 'Поступление' ? '⬇' : '⬆'} 
            {' '}
            {transaction.transactionType}
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
                    name="datetime"
                    value={editedData.datetime}
                    onChange={handleChange}
                  />
                ) : (
                  <span className="value">{formatDateTime(transaction.datetime)}</span>
                )}
              </div>
              <div className="info-row">
                <span className="label">Сумма:</span>
                <span className={`value ${transaction.transactionType === 'Поступление' ? 'income' : 'expense'}`}>
                  {formatAmount(transaction.amount)}
                </span>
              </div>
              <div className="info-row">
                <span className="label">Статус:</span>
                <span className={`status ${transaction.status.toLowerCase()}`}>
                  {transaction.status}
                </span>
              </div>
              <div className="info-row">
                <span className="label">Категория:</span>
                {isEditing ? (
                  <input
                    type="text"
                    name="category"
                    value={editedData.category}
                    onChange={handleChange}
                  />
                ) : (
                  <span className="value">{transaction.category}</span>
                )}
              </div>
            </div>

            <div className="info-group">
              <h3>Детали контрагента</h3>
              <div className="info-row">
                <span className="label">Тип лица:</span>
                {isEditing ? (
                  <select
                    name="personType"
                    value={editedData.personType}
                    onChange={handleChange}
                  >
                    <option value="Физическое">Физическое</option>
                    <option value="Юридическое">Юридическое</option>
                  </select>
                ) : (
                  <span className="value">{transaction.personType}</span>
                )}
              </div>
              <div className="info-row">
                <span className="label">ИНН:</span>
                {isEditing ? (
                  <input
                    type="text"
                    name="receiverINN"
                    value={editedData.receiverINN}
                    onChange={handleChange}
                  />
                ) : (
                  <span className="value">{transaction.receiverINN}</span>
                )}
              </div>
              <div className="info-row">
                <span className="label">Телефон:</span>
                {isEditing ? (
                  <input
                    type="text"
                    name="receiverPhone"
                    value={editedData.receiverPhone}
                    onChange={handleChange}
                  />
                ) : (
                  <span className="value">{transaction.receiverPhone}</span>
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
          {transaction.status === 'Новая' && (
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