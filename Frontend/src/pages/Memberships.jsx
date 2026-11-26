import ManagementPage, { statusColumn } from '../components/ManagementPage';
import StatusBadge from '../components/StatusBadge';
import * as membershipService from '../services/membershipService';
import * as memberService from '../services/memberService';
import { displayName, formatCurrency, formatDate } from '../utils/format';

const planOptions = ['Monthly', 'Quarterly', 'Half-Yearly', 'Yearly'].map((value) => ({ value, label: value }));
const statusOptions = ['ACTIVE', 'EXPIRED', 'CANCELLED'].map((value) => ({ value, label: value }));
const config = {
  title: 'Memberships',
  singular: 'Membership',
  description: 'Track membership plans, payments and renewals.',
  service: { list: membershipService.listMemberships, get: membershipService.getMembership, create: membershipService.createMembership, update: membershipService.updateMembership, remove: membershipService.deleteMembership },
  renew: membershipService.renewMembership,
  related: { members: { list: memberService.listMembers } },
  idField: 'membershipId',
  displayNameField: 'memberName',
  searchFields: ['membershipId', 'memberName', 'planName', 'paymentStatus', 'membershipStatus'],
  filters: [{ key: 'membershipStatus', label: 'status', options: statusOptions }],
  columns: [
    { key: 'membershipId', label: 'Membership ID' },
    { key: 'member', label: 'Member', render: (row) => displayName(row, 'member') },
    { key: 'planName', label: 'Plan' },
    { key: 'amount', label: 'Amount', render: (row) => formatCurrency(row.amount) },
    { key: 'startDate', label: 'Start date', render: (row) => formatDate(row.startDate) },
    { key: 'endDate', label: 'End date', render: (row) => formatDate(row.endDate) },
    { key: 'paymentStatus', label: 'Payment', render: (row) => <span className="badge-cell"><StatusBadge status={row.paymentStatus} /></span> },
    statusColumn('membershipStatus', 'Status'),
  ],
  fields: [
    { name: 'memberId', label: 'Member', type: 'select', valueType: 'number', required: true, relatedKey: 'members', optionValue: 'memberId', optionLabel: 'name' },
    { name: 'planName', label: 'Plan name', type: 'select', required: true, options: planOptions },
    { name: 'amount', label: 'Amount', type: 'number', min: 0, step: 'any', required: true },
    { name: 'startDate', label: 'Start date', type: 'date', required: true },
    { name: 'endDate', label: 'End date', type: 'date', required: true },
    { name: 'paymentStatus', label: 'Payment status', type: 'select', required: true, options: [
      { value: 'PAID', label: 'Paid' }, { value: 'PENDING', label: 'Pending' }, { value: 'FAILED', label: 'Failed' },
    ] },
  ],
};

export default function Memberships() {
  return <ManagementPage config={config} />;
}
