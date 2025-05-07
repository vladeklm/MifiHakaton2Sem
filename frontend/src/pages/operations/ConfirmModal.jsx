import React from 'react';
import './ConfirmModal.css';

const ConfirmModal = ({ message, onConfirm, onCancel }) => {
  return (
    <div className="confirm-modal-overlay">
      <div className="confirm-modal">
        <p>{message}</p>
        <div className="modal-buttons">
          <button className="confirm-btn" onClick={onConfirm}>Удалить</button>
          <button className="cancel-btn" onClick={onCancel}>Отмена</button>
        </div>
      </div>
    </div>
  );
};

export default ConfirmModal;