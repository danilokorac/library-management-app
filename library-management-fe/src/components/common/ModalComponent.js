import React from "react";
import "./ModalComponent.css";

const ModalComponent = ({
  isOpen,
  onClose,
  title,
  children,
  onConfirm,
  confirmText = "Confirm",
  cancelText = "Cancel",
}) => {
  if (!isOpen) return null;

  return (
    <div
      className="modal-overlay"
      onClick={onClose}
      role="dialog"
      aria-modal="true"
    >
      <div
        className="modal-content"
        onClick={(e) => e.stopPropagation()}
        tabIndex="0"
      >
        <button
          className="modal-close"
          onClick={onClose}
          aria-label="Close modal"
        >
          &times;
        </button>
        <h2>{title}</h2>
        <div className="modal-body">{children}</div>
        <div className="modal-actions">
          <button onClick={onConfirm}>{confirmText}</button>
          <button onClick={onClose}>{cancelText}</button>
        </div>
      </div>
    </div>
  );
};

export default ModalComponent;
