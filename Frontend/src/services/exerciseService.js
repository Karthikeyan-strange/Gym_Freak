import api, { useMockData } from './api';
import { createMock, deleteMock, getMock, listMock, listMockExercisesByPlan, updateMock } from './mockData';

const path = '/api/exercises';
const collection = 'exercises';

export const listExercises = () => useMockData ? listMock(collection) : api.get(path).then(({ data }) => data);
export const getExercise = (id) => useMockData ? getMock(collection, id) : api.get(`${path}/${id}`).then(({ data }) => data);
export const createExercise = (exercise) => useMockData ? createMock(collection, exercise) : api.post(path, exercise).then(({ data }) => data);
export const updateExercise = (id, exercise) => useMockData ? updateMock(collection, id, exercise) : api.put(`${path}/${id}`, exercise).then(({ data }) => data);
export const deleteExercise = (id) => useMockData ? deleteMock(collection, id) : api.delete(`${path}/${id}`);
export const listExercisesByPlan = (id) => useMockData ? listMockExercisesByPlan(id) : api.get(`${path}/plan/${id}`).then(({ data }) => data);
