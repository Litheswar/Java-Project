import { useState, useEffect } from 'react';

// Mock API service
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

/**
 * Custom hook for API calls
 * @param {string} initialUrl - The initial API endpoint
 * @param {string} method - HTTP method (GET, POST, PUT, DELETE)
 */
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
      
      switch (method.toUpperCase()) {
        case 'GET':
          result = await mockApiService.get(url);
          break;
        case 'POST':
          result = await mockApiService.post(url, requestData);
          break;
        case 'PUT':
          result = await mockApiService.put(url, requestData);
          break;
        case 'DELETE':
          result = await mockApiService.delete(url);
          break;
        default:
          throw new Error(`Unsupported HTTP method: ${method}`);
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

export default useApi;