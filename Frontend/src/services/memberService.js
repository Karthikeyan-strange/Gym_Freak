import api, { useMockData } from './api';
import { createMock, deleteMock, getMock, listMock, updateMock } from './mockData';

const path = '/api/members';
const collection = 'members';

export const listMembers = () => useMockData ? listMock(collection) : api.get(path).then(({ data }) => data);
export const getMember = (id) => useMockData ? getMock(collection, id) : api.get(`${path}/${id}`).then(({ data }) => data);
export const createMember = (member) => useMockData ? createMock(collection, member) : api.post(path, member).then(({ data }) => data);
export const updateMember = (id, member) => useMockData ? updateMock(collection, id, member) : api.put(`${path}/${id}`, member).then(({ data }) => data);
export const deleteMember = (id) => useMockData ? deleteMock(collection, id) : api.delete(`${path}/${id}`);
