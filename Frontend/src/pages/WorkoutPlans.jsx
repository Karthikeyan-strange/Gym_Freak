import ManagementPage, { statusColumn } from '../components/ManagementPage';
import * as workoutPlanService from '../services/workoutPlanService';
import * as memberService from '../services/memberService';
import * as trainerService from '../services/trainerService';
import { displayName, formatDate } from '../utils/format';

const statuses = ['ACTIVE', 'COMPLETED', 'CANCELLED'].map((value) => ({ value, label: value }));
const goals = ['Muscle Gain', 'Weight Loss', 'Strength', 'Endurance', 'General Fitness'].map((value) => ({ value, label: value }));
const config = {
  title: 'Workout Plans',
  singular: 'Workout Plan',
  description: 'Create and assign training plans to members and trainers.',
  service: { list: workoutPlanService.listWorkoutPlans, get: workoutPlanService.getWorkoutPlan, create: workoutPlanService.createWorkoutPlan, update: workoutPlanService.updateWorkoutPlan, remove: workoutPlanService.deleteWorkoutPlan },
  related: {
    members: { list: memberService.listMembers },
    trainers: { list: trainerService.listTrainers },
  },
  idField: 'planId',
  displayNameField: 'planName',
  searchFields: ['planId', 'planName', 'memberName', 'trainerName', 'goal', 'status'],
  filters: [{ key: 'status', label: 'status', options: statuses }],
  columns: [
    { key: 'planId', label: 'Plan ID' },
    { key: 'planName', label: 'Plan name' },
    { key: 'member', label: 'Member', render: (row) => displayName(row, 'member') },
    { key: 'trainer', label: 'Trainer', render: (row) => displayName(row, 'trainer') },
    { key: 'goal', label: 'Goal' },
    { key: 'startDate', label: 'Start date', render: (row) => formatDate(row.startDate) },
    { key: 'endDate', label: 'End date', render: (row) => formatDate(row.endDate) },
    statusColumn('status'),
  ],
  fields: [
    { name: 'planName', label: 'Plan name', required: true },
    { name: 'memberId', label: 'Member', type: 'select', valueType: 'number', required: true, relatedKey: 'members', optionValue: 'memberId', optionLabel: 'name' },
    { name: 'trainerId', label: 'Trainer', type: 'select', valueType: 'number', required: true, relatedKey: 'trainers', optionValue: 'trainerId', optionLabel: 'name' },
    { name: 'goal', label: 'Goal', type: 'select', required: true, options: goals },
    { name: 'startDate', label: 'Start date', type: 'date', required: true },
    { name: 'endDate', label: 'End date', type: 'date', required: true },
    { name: 'status', label: 'Status', type: 'select', required: true, options: statuses },
    { name: 'description', label: 'Description', type: 'textarea', fullWidth: true },
  ],
};

export default function WorkoutPlans() {
  return <ManagementPage config={config} />;
}
