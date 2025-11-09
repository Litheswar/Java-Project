import React, { useState, useEffect } from 'react';
import { getCountries, getPlannerOptions, postPlannerEstimate } from '../services/api';

const ApiTest = () => {
  const [countries, setCountries] = useState([]);
  const [plannerOptions, setPlannerOptions] = useState(null);
  const [estimateResult, setEstimateResult] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);
        const countriesData = await getCountries();
        const optionsData = await getPlannerOptions();
        setCountries(countriesData);
        setPlannerOptions(optionsData);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  const testEstimate = async () => {
    try {
      setLoading(true);
      const testData = {
        destination: countries[0]?.id || 1,
        travelers: 2,
        tripDays: 7,
        mealsPerDay: 3,
        transportType: 'mixed',
        foodType: 'mixed',
        budget: 5000
      };
      const result = await postPlannerEstimate(testData);
      setEstimateResult(result);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div className="text-red-500">Error: {error}</div>;
  }

  return (
    <div className="p-6">
      <h2 className="text-2xl font-bold mb-4">API Test Component</h2>
      
      <div className="mb-6">
        <h3 className="text-xl font-semibold mb-2">Countries ({countries.length})</h3>
        <ul className="list-disc pl-5">
          {countries.slice(0, 5).map(country => (
            <li key={country.id}>{country.name}</li>
          ))}
          {countries.length > 5 && <li>... and {countries.length - 5} more</li>}
        </ul>
      </div>
      
      <div className="mb-6">
        <h3 className="text-xl font-semibold mb-2">Planner Options</h3>
        {plannerOptions && (
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            <div>
              <h4 className="font-medium">Meals per Day</h4>
              <p>{plannerOptions.mealsPerDay?.join(', ')}</p>
            </div>
            <div>
              <h4 className="font-medium">Transport Types</h4>
              <p>{plannerOptions.transportTypes?.join(', ')}</p>
            </div>
            <div>
              <h4 className="font-medium">Food Types</h4>
              <p>{plannerOptions.foodTypes?.join(', ')}</p>
            </div>
          </div>
        )}
      </div>
      
      <div className="mb-6">
        <button 
          onClick={testEstimate}
          className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
        >
          Test Planner Estimate
        </button>
        
        {estimateResult && (
          <div className="mt-4 p-4 bg-green-100 rounded">
            <h4 className="font-bold">Estimate Result:</h4>
            <pre>{JSON.stringify(estimateResult, null, 2)}</pre>
          </div>
        )}
      </div>
    </div>
  );
};

export default ApiTest;