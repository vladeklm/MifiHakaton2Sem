import api from './config';

export const operationsApi = {
  getOperations: (clientId) =>
    api.get('/operations', {
      params: {
        clientId,
        page: 0,
        size: 10000
      }
    }),

  getOperation: (id) =>
    api.get(`/operations/${id}`),

  createOperation: (operation) =>
    api.post('/operations', operation),

  updateOperation: (id, operation) =>
    api.put(`/operations/change_data/${id}`, operation)
};