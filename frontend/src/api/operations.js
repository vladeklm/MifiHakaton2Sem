import api from './config';

export const operationsApi = {
  // Получение всех операций клиента одним запросом
  getOperations: (clientId) => 
    api.get('', { 
      params: { 
        clientId,
        page: 0,
        size: 10000
      }
    }),

  // Получение одной операции по ID
  getOperation: (id) => 
    api.get(`/${id}`),

  // Создание новой операции
  createOperation: (operation) => 
    api.post('', operation),

  // Обновление данных операции
  updateOperation: (id, operation) => 
    api.put(`/change_data/${id}`, operation)
}; 