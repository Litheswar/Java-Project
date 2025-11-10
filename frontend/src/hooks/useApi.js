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
      if (url.includes('/api/countries') && !url.includes('/countries-states')) {
        result = await apiService.getCountries();
      } else if (url.includes('/api/countries-states')) {
        const countryCode = new URLSearchParams(url.split('?')[1]).get('countryCode');
        result = await apiService.getStatesByCountry(countryCode);
      } else if (url.includes('/api/places')) {
        const countryId = new URLSearchParams(url.split('?')[1]).get('countryId');
        result = await apiService.getPlaces(countryId);
      } else if (url.includes('/api/planner/options')) {
        result = await apiService.getPlannerOptions();
      } else if (url.includes('/api/planner/estimate') && method === 'POST') {
        result = await apiService.postPlannerEstimate(requestData);
      } else if (url.includes('/api/trips') && method === 'POST') {
        result = await apiService.saveTripData(requestData);
      } else if (url.includes('/api/trip/calculateBudget') && method === 'POST') {
        result = await apiService.calculateBudget(requestData);
      } else if (url.includes('/api/trip/suggestDestinations') && method === 'GET') {
        const params = new URLSearchParams(url.split('?')[1]);
        const budget = params.get('budget');
        const region = params.get('region');
        // Prevent API call when budget or region are null/undefined
        if (!budget || !region || budget === 'null' || region === 'null') {
          console.warn("Budget or region missing. Skipping suggestDestinations call.");
          setData([]);
          setLoading(false);
          return [];
        }
        result = await apiService.suggestDestinations(budget, region);
      } else if (url.includes('/api/destinations')) {
        const params = new URLSearchParams(url.split('?')[1]);
        const countryId = params.get('countryId');
        if (countryId) {
          result = await apiService.getDestinationsByCountry(countryId);
        } else {
          result = await apiService.getDestinations();
        }
      } else if (url) {
        // Fallback to direct API call for other endpoints
        result = await apiService.apiRequest(url.replace('/api', ''), { method });
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
  }, [url, method]); // This will re-execute when the URL changes

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
    
    // Mock data for countries-states endpoint - fixed format to match API
    if (url.includes('/countries-states')) {
      const params = new URLSearchParams(url.split('?')[1]);
      const countryCode = params.get('countryCode');
      
      // Sample state data for different countries - matching API format
      const statesByCountry = {
        'US': [
          { name: 'California', lat: 36.7783, lng: -119.4179 },
          { name: 'Texas', lat: 31.9686, lng: -99.9018 },
          { name: 'Florida', lat: 27.6648, lng: -81.5158 },
          { name: 'New York', lat: 40.7128, lng: -74.0060 },
          { name: 'Illinois', lat: 40.6331, lng: -89.3985 }
        ],
        'CA': [
          { name: 'Ontario', lat: 51.2538, lng: -85.3232 },
          { name: 'Quebec', lat: 46.8123, lng: -71.2158 },
          { name: 'British Columbia', lat: 53.7267, lng: -127.6476 },
          { name: 'Alberta', lat: 53.9333, lng: -116.5765 },
          { name: 'Manitoba', lat: 53.7609, lng: -98.8139 }
        ],
        'AU': [
          { name: 'New South Wales', lat: -33.8688, lng: 151.2093 },
          { name: 'Victoria', lat: -37.8136, lng: 144.9631 },
          { name: 'Queensland', lat: -27.4698, lng: 153.0251 },
          { name: 'Western Australia', lat: -31.9505, lng: 115.8596 },
          { name: 'South Australia', lat: -34.9285, lng: 138.6007 }
        ],
        'IN': [
          { name: 'Maharashtra', lat: 19.7515, lng: 75.7139 },
          { name: 'Karnataka', lat: 15.3173, lng: 75.7139 },
          { name: 'Tamil Nadu', lat: 11.1271, lng: 78.6569 },
          { name: 'West Bengal', lat: 22.9868, lng: 87.8550 },
          { name: 'Gujarat', lat: 22.2587, lng: 71.1924 }
        ],
        'JP': [
          { name: 'Tokyo', lat: 35.6762, lng: 139.6503 },
          { name: 'Osaka', lat: 34.6937, lng: 135.5023 },
          { name: 'Kyoto', lat: 35.0116, lng: 135.7681 },
          { name: 'Hokkaido', lat: 43.0621, lng: 141.3544 },
          { name: 'Okinawa', lat: 26.2124, lng: 127.6811 }
        ]
      };
      
      return statesByCountry[countryCode] || [];
    }
    
    // Mock data for calculateBudget endpoint
    if (url.includes('/trip/calculateBudget') && method === 'POST') {
      // Return mock budget calculation result
      return {
        estimatedTotalCost: 4200,
        breakdown: {
          accommodation: 1800,
          meals: 900,
          transportation: 1000,
          activities: 500
        },
        costConfidence: "HIGH"
      };
    }
    
    // Mock data for suggestDestinations endpoint
    if (url.includes('/trip/suggestDestinations') && method === 'GET') {
      // Return mock destination suggestions
      return [
        { name: "Thailand", estimatedCost: 3500, savings: 700, bestMonth: "November" },
        { name: "Vietnam", estimatedCost: 2900, savings: 1300, bestMonth: "December" },
        { name: "India", estimatedCost: 2500, savings: 1700, bestMonth: "October" }
      ];
    }
    
    // Mock data for countries endpoint
    if (url.includes('/countries') && !url.includes('/countries-states')) {
      return [
        { id: 1, name: 'United States', code: 'US' },
        { id: 2, name: 'Canada', code: 'CA' },
        { id: 3, name: 'Australia', code: 'AU' },
        { id: 4, name: 'India', code: 'IN' },
        { id: 5, name: 'Japan', code: 'JP' },
        { id: 6, name: 'United Kingdom', code: 'GB' },
        { id: 7, name: 'Germany', code: 'DE' },
        { id: 8, name: 'France', code: 'FR' }
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