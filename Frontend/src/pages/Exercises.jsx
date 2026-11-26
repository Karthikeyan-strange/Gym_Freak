import ManagementPage from '../components/ManagementPage';
import * as exerciseService from '../services/exerciseService';
import * as workoutPlanService from '../services/workoutPlanService';

const muscleGroups = ['Chest', 'Back', 'Shoulders', 'Biceps', 'Triceps', 'Legs', 'Abs', 'Full Body'];
const config = {
  title: 'Exercises',
  singular: 'Exercise',
  description: 'Add exercises and details to your workout plans.',
  service: { list: exerciseService.listExercises, get: exerciseService.getExercise, create: exerciseService.createExercise, update: exerciseService.updateExercise, remove: exerciseService.deleteExercise },
  related: { plans: { list: workoutPlanService.listWorkoutPlans } },
  idField: 'exerciseId',
  displayNameField: 'exerciseName',
  searchFields: ['exerciseId', 'exerciseName', 'planName', 'muscleGroup'],
  filters: [{ key: 'planId', label: 'workout plan', options: (related) => (related.plans || []).map((plan) => ({ value: plan.planId, label: plan.planName })) }],
  columns: [
    { key: 'exerciseId', label: 'Exercise ID' },
    { key: 'exerciseName', label: 'Exercise name' },
    { key: 'planName', label: 'Workout plan' },
    { key: 'muscleGroup', label: 'Muscle group' },
    { key: 'sets', label: 'Sets' },
    { key: 'reps', label: 'Reps' },
    { key: 'restSeconds', label: 'Rest' , render: (row) => `${row.restSeconds ?? '—'} sec` },
    { key: 'weight', label: 'Weight', render: (row) => row.weight !== null && row.weight !== undefined && row.weight !== '' ? `${row.weight} kg` : '—' },
  ],
  fields: [
    { name: 'planId', label: 'Workout plan', type: 'select', valueType: 'number', required: true, relatedKey: 'plans', optionValue: 'planId', optionLabel: 'planName' },
    { name: 'exerciseName', label: 'Exercise name', required: true },
    { name: 'muscleGroup', label: 'Muscle group', type: 'select', required: true, options: muscleGroups.map((value) => ({ value, label: value })) },
    { name: 'sets', label: 'Sets', type: 'number', min: 1, required: true },
    { name: 'reps', label: 'Reps', type: 'number', min: 1, required: true },
    { name: 'restSeconds', label: 'Rest time (seconds)', type: 'number', min: 0, required: true },
    { name: 'weight', label: 'Weight (kg)', type: 'number', min: 0, step: 'any' },
    { name: 'instructions', label: 'Instructions', type: 'textarea', fullWidth: true },
  ],
};

export default function Exercises() {
  return <ManagementPage config={config} />;
}
