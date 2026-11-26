import api, { useMockData } from './api';
import { createMock, deleteMock, getMock, listMock, listMockPlansByMember, listMockPlansByTrainer, updateMock } from './mockData';

const path = '/api/workout-plans';
const collection = 'workoutPlans';

export const listWorkoutPlans = () => useMockData ? listMock(collection) : api.get(path).then(({ data }) => data);
export const getWorkoutPlan = (id) => useMockData ? getMock(collection, id) : api.get(`${path}/${id}`).then(({ data }) => data);
export const createWorkoutPlan = (plan) => useMockData ? createMock(collection, plan) : api.post(path, plan).then(({ data }) => data);
export const updateWorkoutPlan = (id, plan) => useMockData ? updateMock(collection, id, plan) : api.put(`${path}/${id}`, plan).then(({ data }) => data);
export const deleteWorkoutPlan = (id) => useMockData ? deleteMock(collection, id) : api.delete(`${path}/${id}`);
export const listPlansByMember = (id) => useMockData ? listMockPlansByMember(id) : api.get(`${path}/member/${id}`).then(({ data }) => data);
export const listPlansByTrainer = (id) => useMockData ? listMockPlansByTrainer(id) : api.get(`${path}/trainer/${id}`).then(({ data }) => data);
