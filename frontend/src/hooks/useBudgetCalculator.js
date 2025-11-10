import { useState, useEffect } from 'react';
import { useApi } from './useApi';

// Custom hook for budget calculation
export const useBudgetCalculator = (tripData) => {
  const [budgetEstimate, setBudgetEstimate] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [suggestions, setSuggestions] = useState([]);
  
  // Use the API hook for budget calculation
  const budgetApi = useApi('/api/trip/calculateBudget', 'POST');
  const suggestionsApi = useApi('/api/trip/suggestDestinations', 'GET');
  
  // Calculate budget whenever trip data changes
  useEffect(() => {
    const calculate = async () => {
      // Only calculate if we have the minimum required data
      if (!tripData.destination || !tripData.state || tripData.travelers < 1 || 
          !tripData.startDate || !tripData.endDate) {
        setBudgetEstimate(null);
        return;
      }

      setLoading(true);
      setError(null);
      
      try {
        // Calculate trip duration
        const startDate = new Date(tripData.startDate);
        const endDate = new Date(tripData.endDate);
        const durationDays = Math.ceil((endDate - startDate) / (1000 * 60 * 60 * 24)) || 1;
        
        const payload = {
          destination: tripData.destination,
          state: tripData.state,
          travelers: tripData.travelers,
          days: durationDays,
          mealsPerDay: tripData.mealsPerDay || 3,
          accommodationType: "Standard", // Default for now
          transportationMode: tripData.transportType || "Mixed"
        };

        // Use the API hook to calculate budget
        const result = await budgetApi.execute(payload);
        setBudgetEstimate(result);
        
        // If budget is insufficient, get suggestions
        // Only call suggestDestinations if budget and region are valid
        if (tripData.budget && result && result.estimatedTotalCost && 
            parseFloat(tripData.budget) < result.estimatedTotalCost) {
          // Check if budget and region are valid before making the API call
          if (tripData.budget && tripData.budget !== 'null' && !isNaN(parseFloat(tripData.budget))) {
            // Set the URL for suggestions API with parameters
            suggestionsApi.setUrl(`/api/trip/suggestDestinations?budget=${tripData.budget}&region=Asia`);
            const suggested = await suggestionsApi.execute();
            setSuggestions(suggested || []);
          } else {
            setSuggestions([]);
          }
        } else {
          setSuggestions([]);
        }
      } catch (err) {
        setError(err.message);
        setBudgetEstimate(null);
      } finally {
        setLoading(false);
      }
    };

    // Debounce the calculation to avoid too many API calls
    const timer = setTimeout(() => {
      calculate();
    }, 500);

    return () => clearTimeout(timer);
  }, [tripData, budgetApi, suggestionsApi]);

  return { budgetEstimate, loading, error, suggestions };
};