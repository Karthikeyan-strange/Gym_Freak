import api, { useMockData } from './api';
import { createMock, deleteMock, getMock, listMock, updateMock } from './mockData';

const path = '/api/trainers';
const collection = 'trainers';

export const listTrainers = () => useMockData ? listMock(collection) : api.get(path).then(({ data }) => data);
export const getTrainer = (id) => useMockData ? getMock(collection, id) : api.get(`${path}/${id}`).then(({ data }) => data);
export const createTrainer = (trainer) => useMockData ? createMock(collection, trainer) : api.post(path, trainer).then(({ data }) => data);
export const updateTrainer = (id, trainer) => useMockData ? updateMock(collection, id, trainer) : api.put(`${path}/${id}`, trainer).then(({ data }) => data);
export const deleteTrainer = (id) => useMockData ? deleteMock(collection, id) : api.delete(`${path}/${id}`);
