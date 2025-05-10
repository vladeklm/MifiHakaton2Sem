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

  const [errors, setErrors] = useState({});

  const validate = () => {
    const newErrors = {};
    if (!editedData.dateTimeOperation) newErrors.dateTimeOperation = 'Укажите дату и время';
    if (!editedData.amount || Number(editedData.amount) <= 0) newErrors.amount = 'Введите сумму больше 0';
    if (!editedData.inn || editedData.inn.length !== 11) newErrors.inn = 'ИНН должен содержать 11 цифр';
    if (!editedData.operationCategoryName) newErrors.operationCategoryName = 'Выберите категорию';
    if (!editedData.clientTypeName) newErrors.clientTypeName = 'Выберите тип клиента';
    return newErrors;
  };

  const handleEdit = () => {
    if (isEditing) {
      const validationErrors = validate();
      if (Object.keys(validationErrors).length > 0) {
        setErrors(validationErrors);
        return;
      }
      onSave(editedData);
    }
    setIsEditing(!isEditing);
    setErrors({});
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
                <span className="label">Дата и время:<span style={{ color: 'red' }}>*</span></span>
                {isEditing ? (
                  <div style={{ width: '100%' }}>
                    <input
                      type="datetime-local"
                      name="dateTimeOperation"
                      value={editedData.dateTimeOperation}
                      onChange={handleChange}
                    />
                    {errors.dateTimeOperation && <div className="field-error">{errors.dateTimeOperation}</div>}
                  </div>
                ) : (
                  <span className="value">{formatDateTime(transaction.dateTimeOperation)}</span>
                )}
              </div>

              <div className="info-row">
                <span className="label">Сумма:<span style={{ color: 'red' }}>*</span></span>
                {isEditing ? (
                  <div style={{ width: '100%' }}>
                    <input
                      type="number"
                      name="amount"
                      value={editedData.amount}
                      onChange={handleChange}
                      min="0"
                      step="0.01"
                    />
                    {errors.amount && <div className="field-error">{errors.amount}</div>}
                  </div>
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
                <span className="label">Категория:<span style={{ color: 'red' }}>*</span></span>
                {isEditing ? (
                  <div style={{ width: '100%' }}>
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
                    {errors.operationCategoryName && <div className="field-error">{errors.operationCategoryName}</div>}
                  </div>
                ) : (
                  <span className="value">{transaction.operationCategoryName}</span>
                )}
              </div>
            </div>

            <div className="info-group">
              <h3>Детали контрагента</h3>
              <div className="info-row">
                <span className="label">Тип лица:<span style={{ color: 'red' }}>*</span></span>
                {isEditing ? (
                  <div style={{ width: '100%' }}>
                    <select
                      name="clientTypeName"
                      value={editedData.clientTypeName}
                      onChange={handleChange}
                    >
                      <option value="">Выберите тип</option>
                      <option value="Физическое">Физическое</option>
                      <option value="Юридическое">Юридическое</option>
                    </select>
                    {errors.clientTypeName && <div className="field-error">{errors.clientTypeName}</div>}
                  </div>
                ) : (
                  <span className="value">{transaction.clientTypeName}</span>
                )}
              </div>

              <div className="info-row">
                <span className="label">ИНН:</span>
                <div style={{ flex: 1 }}>
                  {isEditing ? (
                    <>
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
                      {errors.inn && <div className="field-error">{errors.inn}</div>}
                    </>
                  ) : (
                    <span className="value">{transaction.inn}</span>
                  )}
                </div>
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
                    name="bankFromId"
                    value={editedData.bankFromId}
                    onChange={handleChange}
                  />
                ) : (
                  <span className="value">{transaction.bankFromId}</span>
                )}
              </div>
              <div className="info-row">
                <span className="label">Банк получателя:</span>
                {isEditing ? (
                  <input
                    type="text"
                    name="bankToId"
                    value={editedData.bankToId}
                    onChange={handleChange}
                  />
                ) : (
                  <span className="value">{transaction.bankToId}</span>
                )}
              </div>
              <div className="info-row">
                <span className="label">Счет получателя:</span>
                {isEditing ? (
                  <input
                    type="text"
                    name="bankRecipientAccount"
                    value={editedData.bankRecipientAccount}
                    onChange={handleChange}
                  />
                ) : (
                  <span className="value">{transaction.bankRecipientAccount}</span>
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
