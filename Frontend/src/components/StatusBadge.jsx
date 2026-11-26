const statusClass = (status) => String(status || 'unknown').toLowerCase().replaceAll('_', '-');

export default function StatusBadge({ status }) {
  const label = String(status || 'Unknown').replaceAll('_', ' ');
  return <span className={`status-badge status-${statusClass(status)}`}>{label}</span>;
}
