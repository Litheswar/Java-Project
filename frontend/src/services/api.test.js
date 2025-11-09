// Mock fetch for testing
global.fetch = jest.fn();

// Import the functions to test
import { getCountries, getPlannerOptions, postPlannerEstimate } from './api';

describe('API Service', () => {
  beforeEach(() => {
    fetch.mockClear();
  });

  describe('getCountries', () => {
    it('should fetch countries successfully', async () => {
      // Mock response
      const mockCountries = [
        { id: 1, name: 'France', code: 'FR', sustainabilityScore: 85 },
        { id: 2, name: 'Japan', code: 'JP', sustainabilityScore: 92 }
      ];

      fetch.mockResolvedValueOnce({
        ok: true,
        json: async () => mockCountries
      });

      const result = await getCountries();

      expect(fetch).toHaveBeenCalledWith('http://localhost:8080/api/countries', {
        headers: { 'Content-Type': 'application/json' }
      });
      expect(result).toEqual(mockCountries);
    });

    it('should handle fetch errors', async () => {
      fetch.mockRejectedValueOnce(new Error('Network error'));

      await expect(getCountries()).rejects.toThrow('Network error');
    });
  });

  describe('getPlannerOptions', () => {
    it('should fetch planner options successfully', async () => {
      const mockOptions = {
        mealsPerDay: [1, 2, 3, 4, 5],
        transportTypes: ['rail', 'air', 'sea', 'mixed'],
        foodTypes: ['veg', 'non-veg', 'mixed']
      };

      fetch.mockResolvedValueOnce({
        ok: true,
        json: async () => mockOptions
      });

      const result = await getPlannerOptions();

      expect(fetch).toHaveBeenCalledWith('http://localhost:8080/api/planner/options', {
        headers: { 'Content-Type': 'application/json' }
      });
      expect(result).toEqual(mockOptions);
    });
  });

  describe('postPlannerEstimate', () => {
    it('should post planner estimate successfully', async () => {
      const mockPayload = {
        destination: 1,
        travelers: 2,
        tripDays: 7
      };

      const mockResponse = {
        estimatedCost: 5000,
        sustainabilityScore: 85
      };

      fetch.mockResolvedValueOnce({
        ok: true,
        json: async () => mockResponse
      });

      const result = await postPlannerEstimate(mockPayload);

      expect(fetch).toHaveBeenCalledWith('http://localhost:8080/api/planner/estimate', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(mockPayload)
      });
      expect(result).toEqual(mockResponse);
    });
  });
});