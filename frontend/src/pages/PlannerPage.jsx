import React, { useState, useEffect, useMemo, useCallback } from 'react';
import { motion } from 'framer-motion';
import { 
  MapPinIcon, 
  CalendarIcon, 
  CurrencyDollarIcon, 
  UserIcon,
  SparklesIcon,
  ChevronLeftIcon,
  ChevronRightIcon,
  LightBulbIcon,
  CloudIcon,
  TrophyIcon,
  GlobeAltIcon,
  TruckIcon,
  CakeIcon,
  HomeIcon
} from '@heroicons/react/24/outline';
import Card from '../components/Card';
import Button from '../components/Button';
import Stepper from '../components/Stepper';
import CountriesList from '../components/CountriesList';
import StateSelect from '../components/StateSelect';
import CountryMap from '../components/CountryMap';
import TravelAdvisor from '../components/TravelAdvisor';
import { useApi } from '../hooks/useApi';
import * as apiService from '../services/api';

const PlannerPage = () => {
  const [currentStep, setCurrentStep] = useState(0);
  const [tripData, setTripData] = useState({
    destination: '',
    state: '', // Add state field
    startDate: '',
    endDate: '',
    travelers: 1,
    budget: '',
    mealsPerDay: 3,
    transportType: 'mixed',
    foodType: 'mixed'
  });
  
  // Error states for validation
  const [countryError, setCountryError] = useState(false);
  const [stateError, setStateError] = useState(false);
  
  const { data: plannerOptions, loading: optionsLoading } = useApi('/api/planner/options');
  const { data: countries } = useApi('/api/countries');
  
  // State for selected country data
  const [selectedCountryData, setSelectedCountryData] = useState(null);
  
  // State for selected state data
  const [selectedStateData, setSelectedStateData] = useState(null);
  
  // Mock eco-friendly suggestions
  const [isEcoTipsOpen, setIsEcoTipsOpen] = useState(false);
  
  const ecoSuggestions = [
    "Choose trains over flights for shorter distances to reduce CO2 emissions by up to 90%",
    "Stay at eco-certified accommodations to support sustainable tourism",
    "Pack reusable water bottles and shopping bags to minimize plastic waste",
    "Support local businesses by eating at local restaurants and buying from artisans"
  ];
  
  // Mock weather data
  const weatherData = {
    temperature: 22,
    condition: "Sunny",
    humidity: 65,
    wind: 12
  };
  
  const steps = [
    { title: 'Destination' },
    { title: 'Dates' },
    { title: 'Travelers' },
    { title: 'Budget' },
    { title: 'Preferences' },
    { title: 'Review' }
  ];
  
  // Update selected country data when destination changes
  useEffect(() => {
    if (tripData.destination && countries) {
      const country = countries.find(c => c.id === tripData.destination);
      setSelectedCountryData(country || null);
    } else {
      setSelectedCountryData(null);
    }
  }, [tripData.destination, countries]);
  
  // Memoize selectedCountryData to prevent unnecessary re-renders
  const memoizedSelectedCountryData = useMemo(() => selectedCountryData, [selectedCountryData?.id]);

  const handleNext = () => {
    // For step 0 (Destination), validate that both country and state are selected
    if (currentStep === 0) {
      if (!tripData.destination) {
        setCountryError(true);
        return;
      } else {
        setCountryError(false);
      }
      
      if (!tripData.state) {
        setStateError(true);
        return;
      } else {
        setStateError(false);
      }
    }
    
    if (currentStep < steps.length - 1) {
      setCurrentStep(currentStep + 1);
    }
  };
  
  const handleBack = () => {
    if (currentStep > 0) {
      setCurrentStep(currentStep - 1);
    }
  };
  
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setTripData(prev => ({
      ...prev,
      [name]: value
    }));
  };
  
  const handleCountrySelect = useCallback((country) => {
    setTripData(prev => ({
      ...prev,
      destination: country.id,
      state: '' // Reset state when country changes
    }));
    setSelectedStateData(null); // Reset selected state data
  }, []);
  
  const handleStateSelect = useCallback((state) => {
    console.log('PlannerPage: handleStateSelect called with state', state);
    setTripData(prev => ({
      ...prev,
      state: state ? state.name : ''
    }));
    setSelectedStateData(state);
  }, [setTripData, setSelectedStateData]);
  
  // Function to save trip data to backend
  const saveTripData = async () => {
    try {
      const result = await apiService.saveTripData(tripData);
      console.log('Trip data saved successfully:', result);
      alert('Trip plan created successfully!');
    } catch (error) {
      console.error('Error saving trip data:', error);
      alert('An error occurred while creating your trip plan.');
    }
  };
  
  const renderStepContent = () => {
    switch (currentStep) {
      case 0: // Destination
        return (
          <div className="flex flex-col lg:flex-row gap-6">
            <div className="lg:w-1/2">
              <h3 className="text-lg font-medium text-gray-900 mb-4">Choose your destination</h3>
              
              {/* Unified Country Selection Input */}
              <div className="mb-4">
                <label htmlFor="country-select" className="block text-sm font-medium text-gray-700 mb-1">
                  Choose your country
                </label>
                <div className="relative">
                  <input
                    type="text"
                    id="country-select"
                    placeholder="Type or select a country"
                    className={`w-full px-4 py-3 rounded-lg border focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition-all duration-200 ${
                      countryError ? 'border-red-500' : 'border-gray-300'
                    }`}
                    value={selectedCountryData ? selectedCountryData.name : ''}
                    onChange={(e) => {
                      // This is a read-only field, but we can use it to show the selected country
                    }}
                    readOnly
                  />
                  {selectedCountryData && (
                    <button
                      type="button"
                      className="absolute right-2 top-3 text-xs text-red-500 hover:text-red-700"
                      onClick={() => {
                        setTripData(prev => ({
                          ...prev,
                          destination: '',
                          state: ''
                        }));
                        setSelectedCountryData(null);
                        setSelectedStateData(null);
                      }}
                    >
                      Clear
                    </button>
                  )}
                </div>
                {countryError && (
                  <p className="mt-1 text-sm text-red-500">Please select your country.</p>
                )}
              </div>
              
              {/* Countries List - for selection */}
              <CountriesList 
                onCountrySelect={handleCountrySelect} 
                selectedCountry={selectedCountryData}
              />
              
              {/* State/Province Selection - only show when country is selected */}
              {tripData.destination && memoizedSelectedCountryData && (
                <div className="mt-6">
                  <StateSelect
                    selectedCountry={memoizedSelectedCountryData}
                    selectedState={selectedStateData}
                    onStateSelect={handleStateSelect}
                  />
                  {stateError && !tripData.state && (
                    <p className="mt-1 text-sm text-red-500">Please select your state or province.</p>
                  )}
                </div>
              )}
            </div>
            
            <div className="lg:w-1/2">
              <div className="flex flex-col h-full">
                {/* Selected Country Preview Card */}
                {tripData.destination && (
                  <div className="mb-6">
                    <h3 className="text-lg font-medium text-gray-900 mb-4">Selected Destination</h3>
                    <Card className="p-4">
                      <div className="flex items-center">
                        <div className="flex-shrink-0 w-16 h-16 bg-gradient-to-br from-primary to-secondary rounded-lg flex items-center justify-center">
                          <span className="text-xs font-bold text-white">
                            {selectedCountryData?.code || selectedCountryData?.name?.substring(0, 2).toUpperCase() || 'N/A'}
                          </span>
                        </div>
                        
                        <div className="ml-4 flex-1">
                          <h3 className="text-lg font-bold text-gray-900">{selectedCountryData?.name || 'Unknown Country'}</h3>
                          {selectedCountryData?.code && (
                            <p className="text-sm text-gray-500">{selectedCountryData.code}</p>
                          )}
                          
                          {/* Display selected state if available */}
                          {tripData.state && (
                            <p className="text-sm text-gray-600 mt-1">State: {tripData.state}</p>
                          )}
                          
                          {/* Sustainability Score */}
                          {selectedCountryData?.sustainabilityScore && (
                            <div className="mt-2 flex items-center">
                              <div className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-green-100 text-green-800">
                                <svg className="mr-1 h-4 w-4 text-green-500" fill="currentColor" viewBox="0 0 20 20">
                                  <path fillRule="evenodd" d="M5.293 9.707a1 1 0 010-1.414l4-4a1 1 0 011.414 0l4 4a1 1 0 01-1.414 1.414L11 7.414V15a1 1 0 11-2 0V7.414L6.707 9.707a1 1 0 01-1.414 0z" clipRule="evenodd" />
                                </svg>
                                {selectedCountryData.sustainabilityScore}% Sustainable
                              </div>
                            </div>
                          )}
                        </div>
                      </div>
                    </Card>
                  </div>
                )}
                
                <div className="flex-1">
                  <h3 className="text-lg font-medium text-gray-900 mb-4">Map Preview</h3>
                  <div className="h-96">
                    <CountryMap 
                      onCountrySelect={(countryCode) => {
                        // Find the country object by code
                        const country = countries.find(c => c.code === countryCode);
                        if (country) {
                          handleCountrySelect(country);
                        }
                      }} 
                      selectedCountry={selectedCountryData}
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>
        );
      
      case 1: // Dates
        return (
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <h3 className="text-lg font-medium text-gray-900 mb-4">Select your travel dates</h3>
              <div className="space-y-4">
                <div>
                  <label htmlFor="startDate" className="block text-sm font-medium text-gray-700">
                    Start Date
                  </label>
                  <div className="mt-1 relative rounded-md shadow-sm">
                    <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                      <CalendarIcon className="h-5 w-5 text-gray-400" />
                    </div>
                    <input
                      type="date"
                      name="startDate"
                      id="startDate"
                      value={tripData.startDate}
                      onChange={handleInputChange}
                      className="block w-full pl-10 pr-3 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-primary focus:border-primary"
                    />
                  </div>
                </div>
                
                <div>
                  <label htmlFor="endDate" className="block text-sm font-medium text-gray-700">
                    End Date
                  </label>
                  <div className="mt-1 relative rounded-md shadow-sm">
                    <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                      <CalendarIcon className="h-5 w-5 text-gray-400" />
                    </div>
                    <input
                      type="date"
                      name="endDate"
                      id="endDate"
                      value={tripData.endDate}
                      onChange={handleInputChange}
                      className="block w-full pl-10 pr-3 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-primary focus:border-primary"
                    />
                  </div>
                </div>
                
                <Card className="mt-6">
                  <div className="flex justify-between items-center">
                    <h4 className="text-sm font-medium text-gray-900">Travel Tips</h4>
                    <button 
                      onClick={() => setIsEcoTipsOpen(!isEcoTipsOpen)}
                      className="md:hidden text-primary hover:text-primary-dark"
                    >
                      {isEcoTipsOpen ? 'Hide' : 'Show'}
                    </button>
                  </div>
                  <div className={`${isEcoTipsOpen ? 'block' : 'hidden'} md:block mt-2`}>
                    <ul className="text-sm text-gray-600 list-disc pl-5 space-y-1">
                      <li>For the best experience, we recommend planning at least 2 weeks in advance. </li>
                      <li>Consider shoulder seasons for better prices and fewer crowds.</li>
                    </ul>
                  </div>
                </Card>
              </div>
            </div>
            
            <div>
              <h3 className="text-lg font-medium text-gray-900 mb-4">Duration Preview</h3>
              <Card className="h-96 flex items-center justify-center">
                <div className="text-center">
                  <CalendarIcon className="h-12 w-12 text-gray-400 mx-auto" />
                  <h4 className="mt-4 text-lg font-medium text-gray-900">
                    {tripData.startDate && tripData.endDate 
                      ? `${Math.ceil((new Date(tripData.endDate) - new Date(tripData.startDate)) / (1000 * 60 * 60 * 24))} days`
                      : 'Select dates to see duration'}
                  </h4>
                  <p className="mt-2 text-sm text-gray-500">
                    Your trip duration will appear here
                  </p>
                </div>
              </Card>
            </div>
          </div>
        );
      
      case 2: // Travelers
        return (
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <h3 className="text-lg font-medium text-gray-900 mb-4">Who's traveling?</h3>
              <div className="space-y-4">
                <div>
                  <label htmlFor="travelers" className="block text-sm font-medium text-gray-700">
                    Number of Travelers
                  </label>
                  <div className="mt-1 relative rounded-md shadow-sm">
                    <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                      <UserIcon className="h-5 w-5 text-gray-400" />
                    </div>
                    <input
                      type="number"
                      name="travelers"
                      id="travelers"
                      min="1"
                      max="20"
                      value={tripData.travelers}
                      onChange={handleInputChange}
                      className="block w-full pl-10 pr-3 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-primary focus:border-primary"
                    />
                  </div>
                </div>
                
                <div className="grid grid-cols-2 gap-4">
                  {[1, 2, 3, 4, 5, 6].map((count) => (
                    <Card 
                      key={count}
                      hoverEffect={true}
                      className={`cursor-pointer text-center py-4 ${
                        tripData.travelers === count ? 'ring-2 ring-primary' : ''
                      }`}
                      onClick={() => setTripData(prev => ({ ...prev, travelers: count }))}
                    >
                      <div className="flex justify-center">
                        {[...Array(count)].map((_, i) => (
                          <UserIcon key={i} className="h-6 w-6 text-gray-400 -ml-1 first:ml-0" />
                        ))}
                      </div>
                      <p className="mt-2 text-sm font-medium text-gray-900">{count} Traveler{count !== 1 ? 's' : ''}</p>
                    </Card>
                  ))}
                </div>
                
                <Card>
                  <h4 className="text-sm font-medium text-gray-900 mb-2">Group Travel Benefits</h4>
                  <ul className="text-sm text-gray-600 list-disc pl-5 space-y-1">
                    <li>Special group discounts on accommodations</li>
                    <li>Shared transportation options</li>
                    <li>Group activity packages</li>
                  </ul>
                </Card>
              </div>
            </div>
            
            <div>
              <h3 className="text-lg font-medium text-gray-900 mb-4">Traveler Preview</h3>
              <Card className="h-96">
                <div className="flex flex-col items-center justify-center h-full">
                  <div className="flex justify-center mb-4">
                    {[...Array(parseInt(tripData.travelers) || 1)].map((_, i) => (
                      <div key={i} className="bg-gradient-to-r from-primary to-secondary w-12 h-12 rounded-full flex items-center justify-center text-white font-bold -ml-2 first:ml-0">
                        {i + 1}
                      </div>
                    ))}
                  </div>
                  <h4 className="text-lg font-medium text-gray-900">
                    {tripData.travelers || 1} Traveler{tripData.travelers !== 1 ? 's' : ''}
                  </h4>
                  <p className="mt-2 text-sm text-gray-500">
                    Perfect for your planned adventure
                  </p>
                </div>
              </Card>
            </div>
          </div>
        );
      
      case 3: // Budget
        return (
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <h3 className="text-lg font-medium text-gray-900 mb-4">Set your budget</h3>
              <div className="space-y-4">
                <div>
                  <label htmlFor="budget" className="block text-sm font-medium text-gray-700">
                    Total Budget ($)
                  </label>
                  <div className="mt-1 relative rounded-md shadow-sm">
                    <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                      <CurrencyDollarIcon className="h-5 w-5 text-gray-400" />
                    </div>
                    <input
                      type="number"
                      name="budget"
                      id="budget"
                      min="0"
                      step="100"
                      value={tripData.budget}
                      onChange={handleInputChange}
                      className="block w-full pl-10 pr-3 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-primary focus:border-primary"
                      placeholder="e.g., 2000"
                    />
                  </div>
                </div>
                
                <Card>
                  <h4 className="text-sm font-medium text-gray-900 mb-3">Budget Tips</h4>
                  <ul className="text-sm text-gray-600 space-y-2">
                    <li className="flex items-start">
                      <div className="flex-shrink-0 h-5 w-5 text-green-500">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                          <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clipRule="evenodd" />
                        </svg>
                      </div>
                      <span className="ml-2">Allocate 30% for accommodation</span>
                    </li>
                    <li className="flex items-start">
                      <div className="flex-shrink-0 h-5 w-5 text-green-500">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                          <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clipRule="evenodd" />
                        </svg>
                      </div>
                      <span className="ml-2">Reserve 25% for transportation</span>
                    </li>
                    <li className="flex items-start">
                      <div className="flex-shrink-0 h-5 w-5 text-green-500">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                          <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clipRule="evenodd" />
                        </svg>
                      </div>
                      <span className="ml-2">Set aside 20% for food and dining</span>
                    </li>
                  </ul>
                </Card>
              </div>
            </div>
            
            <div>
              <h3 className="text-lg font-medium text-gray-900 mb-4">Budget Breakdown</h3>
              <Card className="h-96">
                {tripData.budget ? (
                  <div>
                    <div className="flex items-center justify-between mb-4">
                      <h4 className="text-lg font-medium text-gray-900">Estimated Allocation</h4>
                      <span className="text-lg font-bold text-primary">${tripData.budget}</span>
                    </div>
                    
                    <div className="space-y-4">
                      <div>
                        <div className="flex justify-between text-sm font-medium text-gray-700 mb-1">
                          <span>Accommodation (30%)</span>
                          <span>${Math.round(tripData.budget * 0.3)}</span>
                        </div>
                        <div className="w-full bg-gray-200 rounded-full h-2">
                          <div 
                            className="bg-blue-600 h-2 rounded-full" 
                            style={{ width: '30%' }}
                          ></div>
                        </div>
                      </div>
                      
                      <div>
                        <div className="flex justify-between text-sm font-medium text-gray-700 mb-1">
                          <span>Transportation (25%)</span>
                          <span>${Math.round(tripData.budget * 0.25)}</span>
                        </div>
                        <div className="w-full bg-gray-200 rounded-full h-2">
                          <div 
                            className="bg-green-600 h-2 rounded-full" 
                            style={{ width: '25%' }}
                          ></div>
                        </div>
                      </div>
                      
                      <div>
                        <div className="flex justify-between text-sm font-medium text-gray-700 mb-1">
                          <span>Food & Dining (20%)</span>
                          <span>${Math.round(tripData.budget * 0.2)}</span>
                        </div>
                        <div className="w-full bg-gray-200 rounded-full h-2">
                          <div 
                            className="bg-yellow-600 h-2 rounded-full" 
                            style={{ width: '20%' }}
                          ></div>
                        </div>
                      </div>
                      
                      <div>
                        <div className="flex justify-between text-sm font-medium text-gray-700 mb-1">
                          <span>Activities & Shopping (15%)</span>
                          <span>${Math.round(tripData.budget * 0.15)}</span>
                        </div>
                        <div className="w-full bg-gray-200 rounded-full h-2">
                          <div 
                            className="bg-purple-600 h-2 rounded-full" 
                            style={{ width: '15%' }}
                          ></div>
                        </div>
                      </div>
                      
                      <div>
                        <div className="flex justify-between text-sm font-medium text-gray-700 mb-1">
                          <span>Emergency Fund (10%)</span>
                          <span>${Math.round(tripData.budget * 0.1)}</span>
                        </div>
                        <div className="w-full bg-gray-200 rounded-full h-2">
                          <div 
                            className="bg-red-600 h-2 rounded-full" 
                            style={{ width: '10%' }}
                          ></div>
                        </div>
                      </div>
                    </div>
                  </div>
                ) : (
                  <div className="text-center py-8">
                    <CurrencyDollarIcon className="h-12 w-12 text-gray-400 mx-auto" />
                    <h4 className="mt-4 text-lg font-medium text-gray-900">
                      Enter a budget to see breakdown
                    </h4>
                    <p className="mt-2 text-sm text-gray-500">
                      Select or enter a budget amount
                    </p>
                  </div>
                )}
              </Card>
            </div>
          </div>
        );
      
      case 4: // Preferences
        return (
          <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
            <div className="lg:col-span-2">
              <h3 className="text-lg font-medium text-gray-900 mb-4">Travel Preferences</h3>
              <Card>
                <div className="space-y-6">
                  {/* Meals per day - Enhanced UI with numeric stepper */}
                  <div>
                    <h4 className="text-sm font-medium text-gray-900 mb-3">Meals per Day</h4>
                    <div className="flex items-center space-x-4">
                      <button
                        type="button"
                        className="inline-flex items-center px-3 py-2 border border-gray-300 shadow-sm text-sm leading-4 font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary"
                        onClick={() => setTripData(prev => ({ 
                          ...prev, 
                          mealsPerDay: Math.max(1, (prev.mealsPerDay || 3) - 1) 
                        }))}
                      >
                        <svg className="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M20 12H4" />
                        </svg>
                      </button>
                      <span className="text-lg font-medium text-gray-900 min-w-[2rem] text-center">
                        {tripData.mealsPerDay || 3}
                      </span>
                      <button
                        type="button"
                        className="inline-flex items-center px-3 py-2 border border-gray-300 shadow-sm text-sm leading-4 font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary"
                        onClick={() => setTripData(prev => ({ 
                          ...prev, 
                          mealsPerDay: Math.min(5, (prev.mealsPerDay || 3) + 1) 
                        }))}
                      >
                        <svg className="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 6v6m0 0v6m0-6h6m-6 0H6" />
                        </svg>
                      </button>
                      <span className="text-sm text-gray-500">meals per day</span>
                    </div>
                  </div>
                  
                  {/* Transport type selection */}
                  <div>
                    <h4 className="text-sm font-medium text-gray-900 mb-3">Preferred Transport</h4>
                    <div className="grid grid-cols-2 sm:grid-cols-4 gap-3">
                      {plannerOptions?.transportTypes?.map((type) => (
                        <button
                          key={type}
                          type="button"
                          className={`px-4 py-3 rounded-lg border text-sm font-medium transition-all duration-200 ${
                            tripData.transportType === type
                              ? 'border-primary bg-primary/10 text-primary'
                              : 'border-gray-300 text-gray-700 hover:bg-gray-50'
                          }`}
                          onClick={() => setTripData(prev => ({ ...prev, transportType: type }))}
                        >
                          <div className="flex flex-col items-center">
                            <div className="mb-1">
                              {type === 'rail' && <TrainIcon className="h-6 w-6" />}
                              {type === 'air' && <AirplaneIcon className="h-6 w-6" />}
                              {type === 'sea' && <ShipIcon className="h-6 w-6" />}
                              {type === 'mixed' && <ArrowsRightLeftIcon className="h-6 w-6" />}
                            </div>
                            <span className="capitalize">{type}</span>
                          </div>
                        </button>
                      ))}
                    </div>
                  </div>
                  
                  {/* Food type selection */}
                  <div>
                    <h4 className="text-sm font-medium text-gray-900 mb-3">Food Preferences</h4>
                    <div className="grid grid-cols-1 sm:grid-cols-3 gap-3">
                      {plannerOptions?.foodTypes?.map((type) => (
                        <button
                          key={type}
                          type="button"
                          className={`px-4 py-3 rounded-lg border text-sm font-medium transition-all duration-200 ${
                            tripData.foodType === type
                              ? 'border-primary bg-primary/10 text-primary'
                              : 'border-gray-300 text-gray-700 hover:bg-gray-50'
                          }`}
                          onClick={() => setTripData(prev => ({ ...prev, foodType: type }))}
                        >
                          <div className="flex items-center">
                            <div className="mr-2">
                              {type === 'veg' && <CakeIcon className="h-5 w-5" />}
                              {type === 'non-veg' && <TruckIcon className="h-5 w-5" />}
                              {type === 'mixed' && <HomeIcon className="h-5 w-5" />}
                            </div>
                            <span className="capitalize">
                              {type === 'veg' ? 'Vegetarian' : type === 'non-veg' ? 'Non-Vegetarian' : 'Mixed'}
                            </span>
                          </div>
                        </button>
                      ))}
                    </div>
                  </div>
                  
                  {/* Sustainability impact preview */}
                  <div className="pt-4 border-t border-gray-200">
                    <h4 className="text-sm font-medium text-gray-900 mb-3">Sustainability Impact</h4>
                    <div className="flex items-center">
                      <div className="flex-1">
                        <div className="flex items-center justify-between text-sm mb-1">
                          <span className="text-gray-600">CO2 Savings</span>
                          <span className="font-medium text-gray-900">150 kg</span>
                        </div>
                        <div className="w-full bg-gray-200 rounded-full h-2">
                          <div 
                            className="bg-green-600 h-2 rounded-full" 
                            style={{ width: '75%' }}
                          ></div>
                        </div>
                      </div>
                      <div className="ml-4 flex-shrink-0">
                        <div className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-green-100 text-green-800">
                          <SparklesIcon className="mr-1 h-4 w-4 text-green-500" />
                          Eco-Friendly
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </Card>
            </div>
            
            <div>
              <h3 className="text-lg font-medium text-gray-900 mb-4">Preferences Preview</h3>
              <Card className="h-96">
                <div className="flex flex-col h-full">
                  <div className="flex-1">
                    <h4 className="text-sm font-medium text-gray-900 mb-4">Your Selections</h4>
                    <div className="space-y-4">
                      <div className="flex items-center">
                        <CakeIcon className="h-5 w-5 text-gray-400" />
                        <div className="ml-3">
                          <p className="text-sm font-medium text-gray-900">Meals</p>
                          <p className="text-sm text-gray-500">{tripData.mealsPerDay || 3} per day</p>
                        </div>
                      </div>
                      
                      <div className="flex items-center">
                        <GlobeAltIcon className="h-5 w-5 text-gray-400" />
                        <div className="ml-3">
                          <p className="text-sm font-medium text-gray-900">Transport</p>
                          <p className="text-sm text-gray-500 capitalize">{tripData.transportType || 'mixed'}</p>
                        </div>
                      </div>
                      
                      <div className="flex items-center">
                        <HomeIcon className="h-5 w-5 text-gray-400" />
                        <div className="ml-3">
                          <p className="text-sm font-medium text-gray-900">Food</p>
                          <p className="text-sm text-gray-500">
                            {tripData.foodType === 'veg' ? 'Vegetarian' : 
                             tripData.foodType === 'non-veg' ? 'Non-Vegetarian' : 'Mixed'}
                          </p>
                        </div>
                      </div>
                    </div>
                  </div>
                  
                  <div className="pt-4 border-t border-gray-200">
                    <div className="flex items-center">
                      <div className="flex-shrink-0">
                        <div className="flex items-center justify-center h-8 w-8 rounded-full bg-green-100">
                          <SparklesIcon className="h-5 w-5 text-green-600" />
                        </div>
                      </div>
                      <div className="ml-3">
                        <p className="text-sm font-medium text-gray-900">Eco Score</p>
                        <p className="text-sm text-gray-500">85% sustainable choices</p>
                      </div>
                    </div>
                  </div>
                </div>
              </Card>
            </div>
          </div>
        );
      
      case 5: // Review
        return (
          <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
            <div className="lg:col-span-2">
              <h3 className="text-lg font-medium text-gray-900 mb-4">Review your trip plan</h3>
              <Card>
                <div className="space-y-6">
                  <div>
                    <h4 className="text-sm font-medium text-gray-900 mb-3">Destination</h4>
                    <div className="flex items-center p-3 bg-gray-50 rounded-lg">
                      <MapPinIcon className="h-5 w-5 text-gray-400" />
                      <div className="ml-3">
                        <p className="text-sm font-medium text-gray-900">
                          {selectedCountryData?.name || 'Not selected'}
                        </p>
                        {tripData.state && (
                          <p className="text-sm text-gray-500">State: {tripData.state}</p>
                        )}
                      </div>
                    </div>
                  </div>
                  
                  <div>
                    <h4 className="text-sm font-medium text-gray-900 mb-3">Travel Dates</h4>
                    <div className="flex items-center p-3 bg-gray-50 rounded-lg">
                      <CalendarIcon className="h-5 w-5 text-gray-400" />
                      <div className="ml-3">
                        <p className="text-sm font-medium text-gray-900">
                          {tripData.startDate || 'Not selected'} to {tripData.endDate || 'Not selected'}
                        </p>
                        <p className="text-sm text-gray-500">
                          {tripData.startDate && tripData.endDate 
                            ? `${Math.ceil((new Date(tripData.endDate) - new Date(tripData.startDate)) / (1000 * 60 * 60 * 24))} days`
                            : 'Duration not set'}
                        </p>
                      </div>
                    </div>
                  </div>
                  
                  <div>
                    <h4 className="text-sm font-medium text-gray-900 mb-3">Travelers & Budget</h4>
                    <div className="grid grid-cols-2 gap-3">
                      <div className="flex items-center p-3 bg-gray-50 rounded-lg">
                        <UserIcon className="h-5 w-5 text-gray-400" />
                        <div className="ml-3">
                          <p className="text-sm font-medium text-gray-900">
                            {tripData.travelers || 1} Traveler{tripData.travelers !== 1 ? 's' : ''}
                          </p>
                        </div>
                      </div>
                      <div className="flex items-center p-3 bg-gray-50 rounded-lg">
                        <CurrencyDollarIcon className="h-5 w-5 text-gray-400" />
                        <div className="ml-3">
                          <p className="text-sm font-medium text-gray-900">
                            ${tripData.budget || '0'} Budget
                          </p>
                        </div>
                      </div>
                    </div>
                  </div>
                  
                  <div>
                    <h4 className="text-sm font-medium text-gray-900 mb-3">Preferences</h4>
                    <div className="grid grid-cols-2 gap-3">
                      <div className="flex items-center p-3 bg-gray-50 rounded-lg">
                        <CakeIcon className="h-5 w-5 text-gray-400" />
                        <div className="ml-3">
                          <p className="text-sm font-medium text-gray-900">
                            {tripData.mealsPerDay || 3} Meals/Day
                          </p>
                        </div>
                      </div>
                      <div className="flex items-center p-3 bg-gray-50 rounded-lg">
                        <GlobeAltIcon className="h-5 w-5 text-gray-400" />
                        <div className="ml-3">
                          <p className="text-sm font-medium text-gray-900 capitalize">
                            {tripData.transportType || 'mixed'} Transport
                          </p>
                        </div>
                      </div>
                      <div className="flex items-center p-3 bg-gray-50 rounded-lg">
                        <HomeIcon className="h-5 w-5 text-gray-400" />
                        <div className="ml-3">
                          <p className="text-sm font-medium text-gray-900">
                            {tripData.foodType === 'veg' ? 'Vegetarian' : 
                             tripData.foodType === 'non-veg' ? 'Non-Vegetarian' : 'Mixed'} Food
                          </p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </Card>
            </div>
            
            <div>
              <h3 className="text-lg font-medium text-gray-900 mb-4">Finalize your trip</h3>
              <Card className="h-full">
                <div className="flex flex-col h-full">
                  <div className="flex-1">
                    <h4 className="text-sm font-medium text-gray-900">Next Steps</h4>
                    <ul className="mt-4 space-y-3">
                      <li className="flex items-start">
                        <div className="flex-shrink-0">
                          <div className="flex items-center justify-center h-5 w-5 rounded-full bg-green-100">
                            <svg className="h-3 w-3 text-green-600" fill="currentColor" viewBox="0 0 8 8">
                              <path d="M2.3 6.73L.6 4.53c-.4-1.04.46-1.4 1.1-.8l1.1 1.4 3.4-3.8c.6-.63 1.6-.27 1.2.7l-4 4.6c-.43.5-.8.4-1.1.1z" />
                            </svg>
                          </div>
                        </div>
                        <p className="ml-3 text-sm text-gray-600">
                          Create personalized itinerary
                        </p>
                      </li>
                      <li className="flex items-start">
                        <div className="flex-shrink-0">
                          <div className="flex items-center justify-center h-5 w-5 rounded-full bg-green-100">
                            <svg className="h-3 w-3 text-green-600" fill="currentColor" viewBox="0 0 8 8">
                              <path d="M2.3 6.73L.6 4.53c-.4-1.04.46-1.4 1.1-.8l1.1 1.4 3.4-3.8c.6-.63 1.6-.27 1.2.7l-4 4.6c-.43.5-.8.4-1.1.1z" />
                            </svg>
                          </div>
                        </div>
                        <p className="ml-3 text-sm text-gray-600">
                          Set up expense tracking
                        </p>
                      </li>
                      <li className="flex items-start">
                        <div className="flex-shrink-0">
                          <div className="flex items-center justify-center h-5 w-5 rounded-full bg-green-100">
                            <svg className="h-3 w-3 text-green-600" fill="currentColor" viewBox="0 0 8 8">
                              <path d="M2.3 6.73L.6 4.53c-.4-1.04.46-1.4 1.1-.8l1.1 1.4 3.4-3.8c.6-.63 1.6-.27 1.2.7l-4 4.6c-.43.5-.8.4-1.1.1z" />
                            </svg>
                          </div>
                        </div>
                        <p className="ml-3 text-sm text-gray-600">
                          Enable travel alerts
                        </p>
                      </li>
                    </ul>
                  </div>
                  
                  <div className="mt-6">
                    <Button 
                      variant="primary" 
                      className="w-full"
                      onClick={saveTripData}
                    >
                      Create Trip Plan
                    </Button>
                    <Button variant="ghost" className="w-full mt-3">
                      Save as Draft
                    </Button>
                  </div>
                </div>
              </Card>
            </div>
          </div>
        );
      
      default:
        return null;
    }
  };
  
  // Icon components for transport types
  const TrainIcon = () => (
    <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 19l9 2-9-18-9 18 9-2zm0 0v-8" />
    </svg>
  );
  
  const AirplaneIcon = () => (
    <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 19l9 2-9-18-9 18 9-2zm0 0v-8" />
    </svg>
  );
  
  const ShipIcon = () => (
    <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 15a4 4 0 004 4h9a5 5 0 10-.1-9.999 5.002 5.002 0 10-9.78 2.096A4 4 0 003 15z" />
    </svg>
  );
  
  const ArrowsRightLeftIcon = () => (
    <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M7 16V4m0 0L3 8m4-4l4 4m6 0v12m0 0l4-4m-4 4l-4-4" />
    </svg>
  );
  
  return (
    <div className="py-8">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.3 }}
        >
          <div className="flex flex-col md:flex-row md:items-center md:justify-between">
            <div>
              <h1 className="text-2xl font-bold text-gray-900">Trip Planner</h1>
              <p className="mt-1 text-sm text-gray-600">
                Plan your next sustainable adventure step by step
              </p>
            </div>
          </div>
          
          <Card className="mt-8">
            <div className="mb-8">
              <Stepper 
                steps={steps} 
                currentStep={currentStep} 
                onStepClick={setCurrentStep}
              />
            </div>
            
            <div className="mt-8">
              <div className="grid grid-cols-1 lg:grid-cols-4 gap-6">
                <div className="lg:col-span-3">
                  {renderStepContent()}
                </div>
                
                {/* Sidebar with eco-friendly suggestions and weather */}
                <div className="space-y-6">
                  {/* Eco-friendly suggestions */}
                  <Card>
                    <div className="flex items-center mb-4">
                      <LightBulbIcon className="h-5 w-5 text-yellow-500" />
                      <h3 className="ml-2 text-lg font-semibold text-gray-900">Eco Tips</h3>
                    </div>
                    <div className="space-y-3">
                      {ecoSuggestions.map((suggestion, index) => (
                        <div key={index} className="flex items-start">
                          <div className="flex-shrink-0 mt-1">
                            <div className="w-2 h-2 bg-green-500 rounded-full"></div>
                          </div>
                          <p className="ml-3 text-sm text-gray-600">{suggestion}</p>
                        </div>
                      ))}
                    </div>
                  </Card>
                  
                  {/* Weather widget */}
                  <Card>
                    <div className="flex items-center mb-4">
                      <CloudIcon className="h-5 w-5 text-blue-500" />
                      <h3 className="ml-2 text-lg font-semibold text-gray-900">Weather Forecast</h3>
                    </div>
                    <div className="text-center">
                      <div className="text-3xl font-bold text-gray-900">{weatherData.temperature}°C</div>
                      <div className="text-sm text-gray-600">{weatherData.condition}</div>
                      <div className="mt-2 text-xs text-gray-500">
                        Humidity: {weatherData.humidity}% | Wind: {weatherData.wind} km/h
                      </div>
                    </div>
                  </Card>
                  
                  {/* Gamification rewards */}
                  <Card>
                    <div className="flex items-center mb-4">
                      <TrophyIcon className="h-5 w-5 text-purple-500" />
                      <h3 className="ml-2 text-lg font-semibold text-gray-900">Planning Rewards</h3>
                    </div>
                    <div className="space-y-3">
                      <div className="flex justify-between items-center">
                        <span className="text-sm text-gray-600">Eco-friendly choices</span>
                        <span className="text-sm font-medium text-green-600">+50 pts</span>
                      </div>
                      <div className="flex justify-between items-center">
                        <span className="text-sm text-gray-600">Early planning</span>
                        <span className="text-sm font-medium text-blue-600">+30 pts</span>
                      </div>
                      <div className="flex justify-between items-center">
                        <span className="text-sm text-gray-600">Budget conscious</span>
                        <span className="text-sm font-medium text-yellow-600">+20 pts</span>
                      </div>
                    </div>
                    <div className="mt-4 pt-3 border-t border-gray-200">
                      <div className="flex justify-between">
                        <span className="text-sm font-medium text-gray-900">Total Points</span>
                        <span className="text-sm font-bold text-purple-600">100 pts</span>
                      </div>
                    </div>
                  </Card>
                </div>
              </div>
            </div>
            
            <div className="mt-8 flex justify-between">
              <Button
                variant="secondary"
                onClick={handleBack}
                disabled={currentStep === 0}
              >
                <ChevronLeftIcon className="h-5 w-5 mr-2" />
                Back
              </Button>
              
              <Button
                variant="primary"
                onClick={handleNext}
                disabled={currentStep === steps.length - 1}
              >
                Next
                <ChevronRightIcon className="h-5 w-5 ml-2" />
              </Button>
            </div>
          </Card>
        </motion.div>
      </div>
      <TravelAdvisor />
    </div>
  );
};

export default PlannerPage;