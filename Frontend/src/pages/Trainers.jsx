import ManagementPage from '../components/ManagementPage';
import * as trainerService from '../services/trainerService';

const config = {
  title: 'Trainers',
  singular: 'Trainer',
  description: 'Keep your coaching team and their details up to date.',
  service: { list: trainerService.listTrainers, get: trainerService.getTrainer, create: trainerService.createTrainer, update: trainerService.updateTrainer, remove: trainerService.deleteTrainer },
  idField: 'trainerId',
  displayNameField: 'name',
  searchFields: ['trainerId', 'name', 'email', 'phone', 'specialization'],
  filters: [{ key: 'specialization', label: 'specialization', options: (_, records) => [...new Set(records.map((trainer) => trainer.specialization).filter(Boolean))].sort().map((value) => ({ value, label: value })) }],
  columns: [
    { key: 'trainerId', label: 'Trainer ID' },
    { key: 'name', label: 'Name' },
    { key: 'email', label: 'Email' },
    { key: 'phone', label: 'Phone' },
    { key: 'specialization', label: 'Specialization' },
    { key: 'experience', label: 'Experience', render: (row) => `${row.experience ?? '—'} yrs` },
  ],
  fields: [
    { name: 'name', label: 'Full name', required: true },
    { name: 'email', label: 'Email', type: 'email', required: true },
    { name: 'phone', label: 'Phone', type: 'tel', required: true },
    { name: 'specialization', label: 'Specialization', required: true },
    { name: 'experience', label: 'Experience (years)', type: 'number', min: 0, required: true },
    { name: 'salary', label: 'Salary', type: 'number', min: 0, step: 'any' },
    { name: 'joiningDate', label: 'Joining date', type: 'date', required: true },
  ],
};

export default function Trainers() {
  return <ManagementPage config={config} />;
}
