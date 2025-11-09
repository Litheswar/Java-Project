import { useState, useEffect } from 'react';
import * as apiService from '../services/api';

// Custom hook for API calls
export const useApi = (initialUrl = '', method = 'GET') => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [url, setUrl] = useState(initialUrl);

  const execute = async (requestData = null) => {
    setLoading(true);
    setError(null);
    
    try {
      let result;
      
      // Map URL patterns to API service functions
      if (url.includes('/api/countries')) {
        result = await apiService.getCountries();
      } else if (url.includes('/api/places')) {
        const countryId = new URLSearchParams(url.split('?')[1]).get('countryId');
        result = await apiService.getPlaces(countryId);
      } else if (url.includes('/api/planner/options')) {
        result = await apiService.getPlannerOptions();
      } else if (url.includes('/api/planner/estimate') && method === 'POST') {
        result = await apiService.postPlannerEstimate(requestData);
      } else if (url.includes('/api/destinations')) {
        const params = new URLSearchParams(url.split('?')[1]);
        const countryId = params.get('countryId');
        if (countryId) {
          result = await apiService.getDestinationsByCountry(countryId);
        } else {
          result = await apiService.getDestinations();
        }
      } else {
        // Fallback to mock service for other endpoints
        result = await mockApiService.get(url);
      }
      
      setData(result);
      return result;
    } catch (err) {
      setError(err.message || 'An error occurred');
      throw err;
    } finally {
      setLoading(false);
    }
  };

  // Execute GET request on mount if URL is provided
  useEffect(() => {
    if (method === 'GET' && url) {
      execute();
    }
  }, [url, method]);

  return { data, loading, error, execute, setUrl };
};

// Mock API service for backward compatibility
const mockApiService = {
  get: async (url) => {
    // Simulate API delay
    await new Promise(resolve => setTimeout(resolve, 500));
    
    // Return mock data based on endpoint
    if (url.includes('/users')) {
      return {
        id: 1,
        name: "Alex Johnson",
        email: "alex.johnson@example.com",
        ecoScore: 85,
        travelPoints: 1250,
        streak: 7
      };
    }
    
    if (url.includes('/trips')) {
      return [
        {
          id: 1,
          name: "European Adventure",
          destination: "Paris, France",
          startDate: "2023-06-15",
          endDate: "2023-06-28",
          budget: 2500,
          spent: 2100,
          co2Saved: 150,
          status: "completed"
        },
        {
          id: 2,
          name: "Mountain Retreat",
          destination: "Swiss Alps",
          startDate: "2023-08-10",
          endDate: "2023-08-20",
          budget: 3000,
          spent: 1800,
          co2Saved: 200,
          status: "upcoming"
        }
      ];
    }
    
    if (url.includes('/expenses')) {
      return [
        { id: 1, tripId: 1, category: "Accommodation", amount: 800, date: "2023-06-15", description: "Hotel de Paris" },
        { id: 2, tripId: 1, category: "Transportation", amount: 450, date: "2023-06-16", description: "Flight to Paris" },
        { id: 3, tripId: 1, category: "Food", amount: 320, date: "2023-06-17", description: "Dining in Paris" }
      ];
    }
    
    return {};
  },
  
  post: async (url, data) => {
    await new Promise(resolve => setTimeout(resolve, 500));
    return { ...data, id: Date.now() };
  },
  
  put: async (url, data) => {
    await new Promise(resolve => setTimeout(resolve, 500));
    return data;
  },
  
  delete: async (url) => {
    await new Promise(resolve => setTimeout(resolve, 500));
    return { success: true };
  }
};

export default useApi;