import ManagementPage from '../components/ManagementPage';
import * as memberService from '../services/memberService';
import { formatDate } from '../utils/format';

const config = {
  title: 'Members',
  singular: 'Member',
  description: 'Manage your gym members and their profiles.',
  service: { list: memberService.listMembers, get: memberService.getMember, create: memberService.createMember, update: memberService.updateMember, remove: memberService.deleteMember },
  idField: 'memberId',
  displayNameField: 'name',
  searchFields: ['memberId', 'name', 'email', 'phone'],
  columns: [
    { key: 'memberId', label: 'Member ID' },
    { key: 'name', label: 'Name' },
    { key: 'email', label: 'Email' },
    { key: 'phone', label: 'Phone' },
    { key: 'age', label: 'Age' },
    { key: 'joinDate', label: 'Join date', render: (row) => formatDate(row.joinDate) },
  ],
  fields: [
    { name: 'name', label: 'Full name', required: true },
    { name: 'email', label: 'Email', type: 'email', required: true },
    { name: 'phone', label: 'Phone', type: 'tel', required: true },
    { name: 'gender', label: 'Gender', type: 'select', required: true, options: [
      { value: 'Male', label: 'Male' }, { value: 'Female', label: 'Female' }, { value: 'Other', label: 'Other' },
    ] },
    { name: 'age', label: 'Age', type: 'number', min: 1, max: 120, required: true },
    { name: 'height', label: 'Height (cm)', type: 'number', min: 0, step: 'any' },
    { name: 'weight', label: 'Weight (kg)', type: 'number', min: 0, step: 'any' },
    { name: 'joinDate', label: 'Join date', type: 'date', required: true },
  ],
};

export default function Members() {
  return <ManagementPage config={config} />;
}
