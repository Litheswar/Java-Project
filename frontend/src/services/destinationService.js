import api from './api';

// Destination Service - handles all destination-related API calls
export const destinationService = {
  // Fetch all countries
  getAllCountries: async () => {
    try {
      const response = await api.get('/countries');
      return response.data;
    } catch (error) {
      console.error('Error fetching countries:', error);
      throw error;
    }
  },

  // Fetch a country by ID
  getCountryById: async (id) => {
    try {
      const response = await api.get(`/countries/${id}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching country with ID ${id}:`, error);
      throw error;
    }
  },

  // Fetch states by country ID
  getStatesByCountryId: async (countryId) => {
    try {
      const response = await api.get(`/states/country/${countryId}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching states for country ID ${countryId}:`, error);
      throw error;
    }
  },

  // Fetch a state by ID
  getStateById: async (id) => {
    try {
      const response = await api.get(`/states/${id}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching state with ID ${id}:`, error);
      throw error;
    }
  },

  // Fetch destinations by state ID
  getDestinationsByStateId: async (stateId) => {
    try {
      const response = await api.get(`/destinations/state/${stateId}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching destinations for state ID ${stateId}:`, error);
      throw error;
    }
  },

  // Fetch a destination by ID
  getDestinationById: async (id) => {
    try {
      const response = await api.get(`/destinations/${id}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching destination with ID ${id}:`, error);
      throw error;
    }
  }
};

export default destinationService;