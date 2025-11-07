import React, { useState } from 'react';
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
  TrophyIcon
} from '@heroicons/react/24/outline';
import Card from '../components/Card';
import Button from '../components/Button';
import Stepper from '../components/Stepper';
import Map from '../components/Map';
import TravelAdvisor from '../components/TravelAdvisor';
import {
  mockDestinations,
  mockMapData
} from '../assets/mockData';

const PlannerPage = () => {
  const [currentStep, setCurrentStep] = useState(0);
  const [tripData, setTripData] = useState({
    destination: '',
    startDate: '',
    endDate: '',
    travelers: 1,
    budget: ''
  });
  
  // Mock eco-friendly suggestions
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
    { title: 'Review' }
  ];
  
  const handleNext = () => {
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
  
  const renderStepContent = () => {
    switch (currentStep) {
      case 0: // Destination
        return (
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <h3 className="text-lg font-medium text-gray-900 mb-4">Choose your destination</h3>
              <div className="space-y-4">
                {mockDestinations.map((destination) => (
                  <Card 
                    key={destination.id}
                    hoverEffect={true}
                    className={`cursor-pointer ${
                      tripData.destination === destination.id ? 'ring-2 ring-primary' : ''
                    }`}
                    onClick={() => setTripData(prev => ({ ...prev, destination: destination.id }))}
                  >
                    <div className="flex items-center">
                      <div className="flex-shrink-0">
                        <div className="bg-gray-200 border-2 border-dashed rounded-xl w-16 h-16" />
                      </div>
                      <div className="ml-4">
                        <h4 className="text-sm font-medium text-gray-900">{destination.name}</h4>
                        <p className="text-sm text-gray-500">{destination.country}</p>
                        <div className="mt-1 flex items-center">
                          <SparklesIcon className="h-4 w-4 text-green-500 mr-1" />
                          <span className="text-xs text-green-600 font-medium">
                            {destination.sustainabilityScore}% sustainable
                          </span>
                        </div>
                      </div>
                    </div>
                  </Card>
                ))}
              </div>
            </div>
            
            <div>
              <h3 className="text-lg font-medium text-gray-900 mb-4">Map Preview</h3>
              <Map 
                center={mockMapData.center}
                markers={mockMapData.markers}
                className="h-96"
              />
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
                  <h4 className="text-sm font-medium text-gray-900">Travel Tips</h4>
                  <p className="mt-2 text-sm text-gray-600">
                    For the best experience, we recommend planning at least 2 weeks in advance. 
                    Consider shoulder seasons for better prices and fewer crowds.
                  </p>
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
                      <p className="mt-2 text-sm font-medium text-gray-900">{count} {count === 1 ? 'Traveler' : 'Travelers'}</p>
                    </Card>
                  ))}
                </div>
                
                <Card className="mt-6">
                  <h4 className="text-sm font-medium text-gray-900">Group Travel Benefits</h4>
                  <ul className="mt-2 text-sm text-gray-600 list-disc pl-5 space-y-1">
                    <li>Group discounts on accommodations</li>
                    <li>Shared transportation options</li>
                    <li>Split costs for activities</li>
                    <li>Enhanced safety in numbers</li>
                  </ul>
                </Card>
              </div>
            </div>
            
            <div>
              <h3 className="text-lg font-medium text-gray-900 mb-4">Traveler Summary</h3>
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
                      value={tripData.budget}
                      onChange={handleInputChange}
                      className="block w-full pl-10 pr-3 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-primary focus:border-primary"
                      placeholder="e.g., 2000"
                    />
                  </div>
                </div>
                
                <div className="grid grid-cols-2 gap-4">
                  {[1000, 2000, 3000, 5000].map((amount) => (
                    <Card 
                      key={amount}
                      hoverEffect={true}
                      className={`cursor-pointer text-center py-4 ${
                        parseInt(tripData.budget) === amount ? 'ring-2 ring-primary' : ''
                      }`}
                      onClick={() => setTripData(prev => ({ ...prev, budget: amount }))}
                    >
                      <p className="text-lg font-medium text-gray-900">${amount}</p>
                      <p className="text-sm text-gray-500">Budget</p>
                    </Card>
                  ))}
                </div>
                
                <Card className="mt-6">
                  <h4 className="text-sm font-medium text-gray-900">Budget Tips</h4>
                  <ul className="mt-2 text-sm text-gray-600 list-disc pl-5 space-y-1">
                    <li>Allocate 30% for accommodation</li>
                    <li>25% for transportation</li>
                    <li>20% for food and dining</li>
                    <li>15% for activities and entertainment</li>
                    <li>10% for shopping and souvenirs</li>
                  </ul>
                </Card>
              </div>
            </div>
            
            <div>
              <h3 className="text-lg font-medium text-gray-900 mb-4">Budget Breakdown</h3>
              <Card className="h-96">
                <div className="h-full flex flex-col">
                  <h4 className="text-sm font-medium text-gray-900 mb-4">Suggested Allocation</h4>
                  <div className="flex-1 flex flex-col justify-center">
                    {tripData.budget ? (
                      <div className="space-y-4">
                        <div>
                          <div className="flex justify-between text-sm">
                            <span>Accommodation (30%)</span>
                            <span className="font-medium">${Math.round(tripData.budget * 0.3)}</span>
                          </div>
                          <div className="mt-1 w-full bg-gray-200 rounded-full h-2">
                            <div 
                              className="bg-blue-500 h-2 rounded-full" 
                              style={{ width: '30%' }}
                            ></div>
                          </div>
                        </div>
                        
                        <div>
                          <div className="flex justify-between text-sm">
                            <span>Transportation (25%)</span>
                            <span className="font-medium">${Math.round(tripData.budget * 0.25)}</span>
                          </div>
                          <div className="mt-1 w-full bg-gray-200 rounded-full h-2">
                            <div 
                              className="bg-green-500 h-2 rounded-full" 
                              style={{ width: '25%' }}
                            ></div>
                          </div>
                        </div>
                        
                        <div>
                          <div className="flex justify-between text-sm">
                            <span>Food & Dining (20%)</span>
                            <span className="font-medium">${Math.round(tripData.budget * 0.2)}</span>
                          </div>
                          <div className="mt-1 w-full bg-gray-200 rounded-full h-2">
                            <div 
                              className="bg-yellow-500 h-2 rounded-full" 
                              style={{ width: '20%' }}
                            ></div>
                          </div>
                        </div>
                        
                        <div>
                          <div className="flex justify-between text-sm">
                            <span>Activities (15%)</span>
                            <span className="font-medium">${Math.round(tripData.budget * 0.15)}</span>
                          </div>
                          <div className="mt-1 w-full bg-gray-200 rounded-full h-2">
                            <div 
                              className="bg-purple-500 h-2 rounded-full" 
                              style={{ width: '15%' }}
                            ></div>
                          </div>
                        </div>
                        
                        <div>
                          <div className="flex justify-between text-sm">
                            <span>Shopping (10%)</span>
                            <span className="font-medium">${Math.round(tripData.budget * 0.1)}</span>
                          </div>
                          <div className="mt-1 w-full bg-gray-200 rounded-full h-2">
                            <div 
                              className="bg-red-500 h-2 rounded-full" 
                              style={{ width: '10%' }}
                            ></div>
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
                  </div>
                </div>
              </Card>
            </div>
          </div>
        );
      
      case 4: // Review
        return (
          <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
            <div className="lg:col-span-2">
              <h3 className="text-lg font-medium text-gray-900 mb-4">Review your trip details</h3>
              <Card>
                <div className="space-y-6">
                  <div>
                    <h4 className="text-sm font-medium text-gray-500 uppercase tracking-wide">Destination</h4>
                    <p className="mt-1 text-lg font-medium text-gray-900">
                      {mockDestinations.find(d => d.id === tripData.destination)?.name || 'Not selected'}
                    </p>
                  </div>
                  
                  <div>
                    <h4 className="text-sm font-medium text-gray-500 uppercase tracking-wide">Travel Dates</h4>
                    <p className="mt-1 text-lg font-medium text-gray-900">
                      {tripData.startDate ? new Date(tripData.startDate).toLocaleDateString() : 'Not selected'} - 
                      {tripData.endDate ? new Date(tripData.endDate).toLocaleDateString() : 'Not selected'}
                    </p>
                  </div>
                  
                  <div>
                    <h4 className="text-sm font-medium text-gray-500 uppercase tracking-wide">Travelers</h4>
                    <p className="mt-1 text-lg font-medium text-gray-900">
                      {tripData.travelers || 1} traveler{tripData.travelers !== 1 ? 's' : ''}
                    </p>
                  </div>
                  
                  <div>
                    <h4 className="text-sm font-medium text-gray-500 uppercase tracking-wide">Budget</h4>
                    <p className="mt-1 text-lg font-medium text-gray-900">
                      ${tripData.budget || 'Not set'}
                    </p>
                  </div>
                  
                  <div className="pt-4 border-t border-gray-200">
                    <div className="flex items-center">
                      <SparklesIcon className="h-5 w-5 text-green-500 mr-2" />
                      <span className="text-sm font-medium text-green-600">
                        Estimated CO₂ savings: {Math.round((tripData.budget || 0) * 0.15)} kg
                      </span>
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
                    <Button variant="primary" className="w-full">
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