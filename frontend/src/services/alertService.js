import api from './api';

// Alert Service - handles all alert-related API calls
export const alertService = {
  // Fetch all alerts
  getAllAlerts: async () => {
    try {
      const response = await api.get('/alerts');
      return response.data;
    } catch (error) {
      console.error('Error fetching alerts:', error);
      throw error;
    }
  },

  // Fetch a single alert by ID
  getAlertById: async (id) => {
    try {
      const response = await api.get(`/alerts/${id}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching alert with ID ${id}:`, error);
      throw error;
    }
  },

  // Fetch alerts by user ID
  getAlertsByUserId: async (userId) => {
    try {
      const response = await api.get(`/alerts/user/${userId}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching alerts for user ID ${userId}:`, error);
      throw error;
    }
  },

  // Create a new alert
  createAlert: async (alertData) => {
    try {
      const response = await api.post('/alerts', alertData);
      return response.data;
    } catch (error) {
      console.error('Error creating alert:', error);
      throw error;
    }
  },

  // Update an existing alert
  updateAlert: async (alertData) => {
    try {
      const response = await api.put('/alerts', alertData);
      return response.data;
    } catch (error) {
      console.error('Error updating alert:', error);
      throw error;
    }
  },

  // Delete an alert by ID
  deleteAlert: async (id) => {
    try {
      const response = await api.delete(`/alerts/${id}`);
      return response.data;
    } catch (error) {
      console.error(`Error deleting alert with ID ${id}:`, error);
      throw error;
    }
  }
};

export default alertService;