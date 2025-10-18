import api from './api';

// Trip History Service - handles all trip history-related API calls
export const tripHistoryService = {
  // Fetch all trip history records
  getAllTripHistory: async () => {
    try {
      const response = await api.get('/trip_history');
      return response.data;
    } catch (error) {
      console.error('Error fetching trip history:', error);
      throw error;
    }
  },

  // Fetch a single trip history record by ID
  getTripHistoryById: async (id) => {
    try {
      const response = await api.get(`/trip_history/${id}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching trip history with ID ${id}:`, error);
      throw error;
    }
  },

  // Fetch trip history by user ID
  getTripHistoryByUserId: async (userId) => {
    try {
      const response = await api.get(`/trip_history/user/${userId}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching trip history for user ID ${userId}:`, error);
      throw error;
    }
  },

  // Fetch trip history by trip ID
  getTripHistoryByTripId: async (tripId) => {
    try {
      const response = await api.get(`/trip_history/trip/${tripId}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching trip history for trip ID ${tripId}:`, error);
      throw error;
    }
  },

  // Create a new trip history record
  createTripHistory: async (tripHistoryData) => {
    try {
      const response = await api.post('/trip_history', tripHistoryData);
      return response.data;
    } catch (error) {
      console.error('Error creating trip history record:', error);
      throw error;
    }
  },

  // Update an existing trip history record
  updateTripHistory: async (tripHistoryData) => {
    try {
      const response = await api.put('/trip_history', tripHistoryData);
      return response.data;
    } catch (error) {
      console.error('Error updating trip history record:', error);
      throw error;
    }
  },

  // Delete a trip history record by ID
  deleteTripHistory: async (id) => {
    try {
      const response = await api.delete(`/trip_history/${id}`);
      return response.data;
    } catch (error) {
      console.error(`Error deleting trip history record with ID ${id}:`, error);
      throw error;
    }
  }
};

export default tripHistoryService;