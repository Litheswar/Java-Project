// API service functions for Smart Travel Planner

// Base API URL - adjust this to match your backend server
const API_BASE_URL = 'http://localhost:8080/api';

// Helper function for API requests with error handling
const apiRequest = async (endpoint, options = {}) => {
  try {
    const response = await fetch(`${API_BASE_URL}${endpoint}`, {
      headers: {
        'Content-Type': 'application/json',
        ...options.headers,
      },
      ...options,
    });

    if (!response.ok) {
      throw new Error(`API request failed with status ${response.status}`);
    }

    return await response.json();
  } catch (error) {
    console.error(`API request failed: ${error.message}`);
    throw error;
  }
};

// Cache for storing data with TTL
class Cache {
  constructor(ttl = 5 * 60 * 1000) { // 5 minutes default
    this.data = new Map();
    this.ttl = ttl;
  }

  set(key, value) {
    const item = {
      value,
      timestamp: Date.now()
    };
    this.data.set(key, item);
  }

  get(key) {
    const item = this.data.get(key);
    if (!item) return null;

    // Check if item is expired
    if (Date.now() - item.timestamp > this.ttl) {
      this.data.delete(key);
      return null;
    }

    return item.value;
  }

  clear() {
    this.data.clear();
  }
}

// Create caches for frequently accessed data
const countriesCache = new Cache();
const plannerOptionsCache = new Cache();

// API service functions

// Get all countries
export const getCountries = async () => {
  // Check cache first
  const cached = countriesCache.get('countries');
  if (cached) {
    return cached;
  }

  const countries = await apiRequest('/countries');
  countriesCache.set('countries', countries);
  return countries;
};

// Get places (destinations) for a specific country
export const getPlaces = async (countryId) => {
  return await apiRequest(`/places?countryId=${countryId}`);
};

// Get states for a specific country
export const getStatesByCountry = async (countryCode) => {
  return await apiRequest(`/countries-states?countryCode=${countryCode}`);
};

// Get planner options
export const getPlannerOptions = async () => {
  // Check cache first
  const cached = plannerOptionsCache.get('options');
  if (cached) {
    return cached;
  }

  const options = await apiRequest('/planner/options');
  plannerOptionsCache.set('options', options);
  return options;
};

// Post planner estimate
export const postPlannerEstimate = async (payload) => {
  return await apiRequest('/planner/estimate', {
    method: 'POST',
    body: JSON.stringify(payload),
  });
};

// Save trip data
export const saveTripData = async (payload) => {
  return await apiRequest('/trips', {
    method: 'POST',
    body: JSON.stringify(payload),
  });
};

// Get all destinations
export const getDestinations = async () => {
  return await apiRequest('/destinations');
};

// Get destinations by country
export const getDestinationsByCountry = async (countryId) => {
  return await apiRequest(`/destinations?countryId=${countryId}`);
};

// Calculate budget
export const calculateBudget = async (payload) => {
  return await apiRequest('/trip/calculateBudget', {
    method: 'POST',
    body: JSON.stringify(payload),
  });
};

// Suggest destinations
export const suggestDestinations = async (budget, region) => {
  // Prevent API call when budget or region are null/undefined
  if (!budget || !region || budget === 'null' || region === 'null') {
    console.warn("Budget or region missing. Skipping suggestDestinations call.");
    return []; // Return empty array instead of making API call
  }
  return await apiRequest(`/trip/suggestDestinations?budget=${budget}&region=${region}`);
};

// Default export for backward compatibility
export default {
  getCountries,
  getPlaces,
  getStatesByCountry,
  getPlannerOptions,
  postPlannerEstimate,
  saveTripData,
  getDestinations,
  getDestinationsByCountry,
  calculateBudget,
  suggestDestinations,
};