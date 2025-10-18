import api from './api';

// Expense Service - handles all expense-related API calls
export const expenseService = {
  // Fetch all expenses
  getAllExpenses: async () => {
    try {
      const response = await api.get('/expenses');
      return response.data;
    } catch (error) {
      console.error('Error fetching expenses:', error);
      throw error;
    }
  },

  // Fetch a single expense by ID
  getExpenseById: async (id) => {
    try {
      const response = await api.get(`/expenses/${id}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching expense with ID ${id}:`, error);
      throw error;
    }
  },

  // Fetch expenses by trip ID
  getExpensesByTripId: async (tripId) => {
    try {
      const response = await api.get(`/expenses/trip/${tripId}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching expenses for trip ID ${tripId}:`, error);
      throw error;
    }
  },

  // Create a new expense
  createExpense: async (expenseData) => {
    try {
      const response = await api.post('/expenses', expenseData);
      return response.data;
    } catch (error) {
      console.error('Error creating expense:', error);
      throw error;
    }
  },

  // Update an existing expense
  updateExpense: async (expenseData) => {
    try {
      const response = await api.put('/expenses', expenseData);
      return response.data;
    } catch (error) {
      console.error('Error updating expense:', error);
      throw error;
    }
  },

  // Delete an expense by ID
  deleteExpense: async (id) => {
    try {
      const response = await api.delete(`/expenses/${id}`);
      return response.data;
    } catch (error) {
      console.error(`Error deleting expense with ID ${id}:`, error);
      throw error;
    }
  },

  // Fetch expense breakdown by trip ID
  getExpenseBreakdownByTripId: async (tripId) => {
    try {
      const response = await api.get(`/expenses/breakdown/${tripId}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching expense breakdown for trip ID ${tripId}:`, error);
      throw error;
    }
  }
};

export default expenseService;