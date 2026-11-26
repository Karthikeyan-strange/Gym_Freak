import api, { useMockData } from './api';
import {
  createMock, deleteMock, getMock, listMock, listMockActiveMemberships,
  listMockExpiredMemberships, renewMock, updateMock,
} from './mockData';

const path = '/api/memberships';
const collection = 'memberships';

export const listMemberships = () => useMockData ? listMock(collection) : api.get(path).then(({ data }) => data);
export const getMembership = (id) => useMockData ? getMock(collection, id) : api.get(`${path}/${id}`).then(({ data }) => data);
export const createMembership = (membership) => useMockData ? createMock(collection, membership) : api.post(path, membership).then(({ data }) => data);
export const updateMembership = (id, membership) => useMockData ? updateMock(collection, id, membership) : api.put(`${path}/${id}`, membership).then(({ data }) => data);
export const deleteMembership = (id) => useMockData ? deleteMock(collection, id) : api.delete(`${path}/${id}`);
export const renewMembership = (id) => useMockData ? renewMock(id) : api.post(`${path}/${id}/renew`).then(({ data }) => data);
export const listActiveMemberships = () => useMockData ? listMockActiveMemberships() : api.get(`${path}/active`).then(({ data }) => data);
export const listExpiredMemberships = () => useMockData ? listMockExpiredMemberships() : api.get(`${path}/expired`).then(({ data }) => data);
