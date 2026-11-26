import { Eye, Pencil, Trash2 } from 'lucide-react';

export default function DataTable({ columns, rows, rowId, onView, onEdit, onDelete, extraActions, showActions = true, emptyMessage = 'No records found.' }) {
  return (
    <div className="table-scroll">
      <table className="data-table">
        <thead>
          <tr>
            {columns.map((column) => <th key={column.key}>{column.label}</th>)}
            {showActions && <th className="actions-heading">Actions</th>}
          </tr>
        </thead>
        <tbody>
          {rows.length ? rows.map((row, index) => (
            <tr key={row[rowId] ?? index}>
              {columns.map((column) => (
                <td key={column.key}>{column.render ? column.render(row) : (row[column.key] ?? '—')}</td>
              ))}
              {showActions && <td>
                <div className="row-actions">
                  {extraActions?.(row)}
                  {onView && <button type="button" className="icon-button action-view" aria-label="View record" title="View" onClick={() => onView(row)}><Eye size={16} /></button>}
                  {onEdit && <button type="button" className="icon-button action-edit" aria-label="Edit record" title="Edit" onClick={() => onEdit(row)}><Pencil size={15} /></button>}
                  {onDelete && <button type="button" className="icon-button action-delete" aria-label="Delete record" title="Delete" onClick={() => onDelete(row)}><Trash2 size={16} /></button>}
                </div>
              </td>}
            </tr>
          )) : (
            <tr><td className="empty-cell" colSpan={columns.length + (showActions ? 1 : 0)}>{emptyMessage}</td></tr>
          )}
        </tbody>
      </table>
    </div>
  );
}
