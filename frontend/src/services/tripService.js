import api from './api';

// Trip Service - handles all trip-related API calls
export const tripService = {
  // Fetch all trips
  getAllTrips: async () => {
    try {
      const response = await api.get('/trips');
      return response.data;
    } catch (error) {
      console.error('Error fetching trips:', error);
      throw error;
    }
  },

  // Fetch a single trip by ID
  getTripById: async (id) => {
    try {
      const response = await api.get(`/trips/${id}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching trip with ID ${id}:`, error);
      throw error;
    }
  },

  // Fetch trips by user ID
  getTripsByUserId: async (userId) => {
    try {
      const response = await api.get(`/trips/user/${userId}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching trips for user ID ${userId}:`, error);
      throw error;
    }
  },

  // Create a new trip
  createTrip: async (tripData) => {
    try {
      const response = await api.post('/trips', tripData);
      return response.data;
    } catch (error) {
      console.error('Error creating trip:', error);
      throw error;
    }
  },

  // Update an existing trip
  updateTrip: async (tripData) => {
    try {
      const response = await api.put('/trips', tripData);
      return response.data;
    } catch (error) {
      console.error('Error updating trip:', error);
      throw error;
    }
  },

  // Delete a trip by ID
  deleteTrip: async (id) => {
    try {
      const response = await api.delete(`/trips/${id}`);
      return response.data;
    } catch (error) {
      console.error(`Error deleting trip with ID ${id}:`, error);
      throw error;
    }
  }
};

export default tripService;