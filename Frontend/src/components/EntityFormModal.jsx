import { useEffect, useState } from 'react';
import { LoaderCircle } from 'lucide-react';
import Modal from './Modal';

function initialValues(fields, record) {
  return Object.fromEntries(fields.map((field) => {
    const value = record?.[field.name];
    return [field.name, field.type === 'date' && value ? String(value).slice(0, 10) : value ?? ''];
  }));
}

export default function EntityFormModal({ title, fields, record, readOnly = false, loading = false, submitting = false, onClose, onSubmit }) {
  const [values, setValues] = useState(() => initialValues(fields, record));

  useEffect(() => setValues(initialValues(fields, record)), [fields, record]);

  const handleChange = (event) => {
    setValues((current) => ({ ...current, [event.target.name]: event.target.value }));
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    const payload = Object.fromEntries(fields.map((field) => {
      const value = values[field.name];
      if (field.type === 'number') return [field.name, value === '' ? null : Number(value)];
      if (field.valueType === 'number') return [field.name, value === '' ? null : Number(value)];
      return [field.name, value];
    }));
    onSubmit(payload);
  };

  return (
    <Modal title={title} onClose={onClose} wide>
      {loading ? (
        <div className="modal-loading"><LoaderCircle className="spin" size={24} /> Loading record…</div>
      ) : (
        <form className="entity-form" onSubmit={handleSubmit}>
          <div className="form-grid">
            {fields.map((field) => (
              <label className={`form-field${field.fullWidth ? ' field-full' : ''}`} key={field.name}>
                <span>{field.label}{field.required && !readOnly ? <b className="required-mark"> *</b> : ''}</span>
                {field.type === 'select' ? (
                  <select
                    name={field.name}
                    value={values[field.name]}
                    onChange={handleChange}
                    required={!readOnly && field.required}
                    disabled={readOnly}
                  >
                    <option value="">{field.placeholder || `Select ${field.label.toLowerCase()}`}</option>
                    {(field.options || []).map((option) => (
                      <option key={option.value} value={option.value}>{option.label}</option>
                    ))}
                  </select>
                ) : field.type === 'textarea' ? (
                  <textarea name={field.name} value={values[field.name]} onChange={handleChange} required={!readOnly && field.required} readOnly={readOnly} rows={3} />
                ) : (
                  <input
                    name={field.name}
                    type={field.type || 'text'}
                    value={values[field.name]}
                    onChange={handleChange}
                    required={!readOnly && field.required}
                    readOnly={readOnly}
                    min={field.min}
                    max={field.max}
                    step={field.step}
                    placeholder={field.placeholder}
                  />
                )}
              </label>
            ))}
          </div>
          <div className="modal-actions">
            <button type="button" className="button button-secondary" onClick={onClose} disabled={submitting}>{readOnly ? 'Close' : 'Cancel'}</button>
            {!readOnly && <button type="submit" className="button button-primary" disabled={submitting}>{submitting ? 'Saving…' : title.startsWith('Edit') ? 'Save changes' : 'Create record'}</button>}
          </div>
        </form>
      )}
    </Modal>
  );
}
