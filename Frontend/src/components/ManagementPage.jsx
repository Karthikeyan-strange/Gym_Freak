import { useCallback, useEffect, useMemo, useRef, useState } from 'react';
import { Plus, Search } from 'lucide-react';
import DataTable from './DataTable';
import EntityFormModal from './EntityFormModal';
import ConfirmDialog from './ConfirmDialog';
import LoadingState from './LoadingState';
import StatusBadge from './StatusBadge';
import { useToast } from './ToastProvider';
import { getErrorMessage } from '../utils/errors';

export function statusColumn(key, label = 'Status') {
  return { key, label, render: (row) => <StatusBadge status={row[key]} /> };
}

export default function ManagementPage({ config }) {
  const { notify } = useToast();
  const [records, setRecords] = useState([]);
  const [related, setRelated] = useState({});
  const [loading, setLoading] = useState(true);
  const [pageError, setPageError] = useState('');
  const [relatedError, setRelatedError] = useState('');
  const [search, setSearch] = useState('');
  const [filters, setFilters] = useState({});
  const [modal, setModal] = useState(null);
  const [modalLoading, setModalLoading] = useState(false);
  const [pendingDelete, setPendingDelete] = useState(null);
  const [busy, setBusy] = useState(false);
  const modalRequest = useRef(0);

  const loadData = useCallback(async () => {
    setLoading(true);
    setPageError('');
    const relatedEntries = Object.entries(config.related || {});
    try {
      const list = await config.service.list();
      const relatedResults = await Promise.allSettled(relatedEntries.map(([, service]) => service.list()));
      setRecords(Array.isArray(list) ? list : []);
      const failedOptions = [];
      setRelated(Object.fromEntries(relatedEntries.map(([key], index) => {
        const result = relatedResults[index];
        if (result.status === 'rejected') failedOptions.push(key);
        return [key, result.status === 'fulfilled' && Array.isArray(result.value) ? result.value : []];
      })));
      setRelatedError(failedOptions.length ? `Could not load selection options for: ${failedOptions.join(', ')}.` : '');
    } catch (error) {
      setPageError(getErrorMessage(error));
    } finally {
      setLoading(false);
    }
  }, [config]);

  useEffect(() => { loadData(); }, [loadData]);

  const visibleRecords = useMemo(() => {
    const normalizedSearch = search.trim().toLowerCase();
    return records.filter((record) => {
      const matchesSearch = !normalizedSearch || config.searchFields.some((field) =>
        String(record[field] ?? (field === 'memberName' ? record.member?.name : field === 'trainerName' ? record.trainer?.name : '') ?? '').toLowerCase().includes(normalizedSearch));
      const matchesFilters = (config.filters || []).every((filter) =>
        !filters[filter.key] || String(record[filter.key]) === filters[filter.key]);
      return matchesSearch && matchesFilters;
    });
  }, [config, filters, records, search]);

  const openView = async (record) => {
    const requestId = modalRequest.current + 1;
    modalRequest.current = requestId;
    setModalLoading(true);
    setModal({ mode: 'view', record });
    try {
      const freshRecord = await config.service.get(record[config.idField]);
      if (modalRequest.current === requestId) setModal({ mode: 'view', record: freshRecord });
    } catch (error) {
      if (modalRequest.current === requestId) notify(getErrorMessage(error), 'error');
    } finally {
      if (modalRequest.current === requestId) setModalLoading(false);
    }
  };

  const saveRecord = async (payload) => {
    setBusy(true);
    try {
      if (modal.mode === 'edit') await config.service.update(modal.record[config.idField], payload);
      else await config.service.create(payload);
      notify(`${config.singular} ${modal.mode === 'edit' ? 'updated' : 'created'} successfully.`);
      setModal(null);
      await loadData();
    } catch (error) {
      notify(getErrorMessage(error), 'error');
    } finally {
      setBusy(false);
    }
  };

  const deleteRecord = async () => {
    setBusy(true);
    try {
      await config.service.remove(pendingDelete[config.idField]);
      notify(`${config.singular} deleted successfully.`);
      setPendingDelete(null);
      await loadData();
    } catch (error) {
      notify(getErrorMessage(error), 'error');
    } finally {
      setBusy(false);
    }
  };

  const handleRenew = async (record) => {
    setBusy(true);
    try {
      await config.renew(record[config.idField]);
      notify('Membership renewed successfully.');
      await loadData();
    } catch (error) {
      notify(getErrorMessage(error), 'error');
    } finally {
      setBusy(false);
    }
  };

  const fields = useMemo(() => config.fields.map((field) => (
    field.relatedKey
      ? { ...field, options: (related[field.relatedKey] || []).map((item) => ({
        value: item[field.optionValue],
        label: item[field.optionLabel],
      })) }
      : field
  )), [config, related]);

  return (
    <section className="management-page">
      <div className="page-intro">
        <div>
          <p className="eyebrow">MANAGE YOUR GYM</p>
          <h2>{config.title}</h2>
          <p>{config.description}</p>
        </div>
        <button type="button" className="button button-primary" onClick={() => setModal({ mode: 'create', record: null })}>
          <Plus size={18} /> Add {config.singular}
        </button>
      </div>

      <div className="panel">
        <div className="table-toolbar">
          <label className="search-box">
            <Search size={17} />
            <input value={search} onChange={(event) => setSearch(event.target.value)} placeholder={`Search ${config.title.toLowerCase()}…`} aria-label={`Search ${config.title}`} />
          </label>
          {(config.filters || []).map((filter) => (
            <select className="filter-select" key={filter.key} value={filters[filter.key] || ''} onChange={(event) => setFilters((current) => ({ ...current, [filter.key]: event.target.value }))} aria-label={filter.label}>
              <option value="">All {filter.label}</option>
              {(typeof filter.options === 'function' ? filter.options(related, records) : filter.options).map((option) => <option value={option.value} key={option.value}>{option.label}</option>)}
            </select>
          ))}
          <span className="record-count">{visibleRecords.length} {visibleRecords.length === 1 ? 'record' : 'records'}</span>
        </div>

        {loading ? <LoadingState /> : pageError ? (
          <div className="error-state">
            <strong>Couldn’t load {config.title.toLowerCase()}.</strong>
            <span>{pageError}</span>
            <button type="button" className="button button-secondary" onClick={loadData}>Try again</button>
          </div>
        ) : (
          <>
          {relatedError && <p className="inline-warning" role="status">{relatedError} Check the backend connection and try again.</p>}
          <DataTable
            columns={config.columns}
            rows={visibleRecords}
            rowId={config.idField}
            onView={openView}
            onEdit={(record) => setModal({ mode: 'edit', record })}
            onDelete={setPendingDelete}
            extraActions={config.renew ? (record) => (
              <button type="button" className="text-action" disabled={busy} onClick={() => handleRenew(record)}>Renew</button>
            ) : undefined}
            emptyMessage={search || Object.values(filters).some(Boolean) ? 'No records match your search or filters.' : `No ${config.title.toLowerCase()} yet. Add your first ${config.singular.toLowerCase()} to get started.`}
          />
          </>
        )}
      </div>

      {modal && (
        <EntityFormModal
          title={modal.mode === 'view' ? `View ${config.singular}` : `${modal.mode === 'edit' ? 'Edit' : 'Add'} ${config.singular}`}
          fields={fields}
          record={modal.record}
          readOnly={modal.mode === 'view'}
          loading={modalLoading}
          submitting={busy}
          onClose={() => { modalRequest.current += 1; setModal(null); }}
          onSubmit={saveRecord}
        />
      )}
      {pendingDelete && (
        <ConfirmDialog
          title={`Delete ${config.singular.toLowerCase()}?`}
          message={`This will permanently delete ${pendingDelete[config.displayNameField] || `this ${config.singular.toLowerCase()}`}. This action cannot be undone.`}
          onCancel={() => setPendingDelete(null)}
          onConfirm={deleteRecord}
          busy={busy}
        />
      )}
      {busy && modal && <span className="sr-only" role="status">Saving record…</span>}
    </section>
  );
}
