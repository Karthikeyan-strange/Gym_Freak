import Modal from './Modal';
import { AlertTriangle } from 'lucide-react';

export default function ConfirmDialog({ title, message, onCancel, onConfirm, busy = false }) {
  return (
    <Modal title={title} onClose={onCancel}>
      <div className="confirm-content">
        <div className="confirm-icon"><AlertTriangle size={22} /></div>
        <p>{message}</p>
      </div>
      <div className="modal-actions">
        <button type="button" className="button button-secondary" onClick={onCancel} disabled={busy}>Cancel</button>
        <button type="button" className="button button-danger" onClick={onConfirm} disabled={busy}>{busy ? 'Deleting…' : 'Delete'}</button>
      </div>
    </Modal>
  );
}
