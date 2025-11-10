import React, { useState, useEffect, useMemo, useRef } from 'react';
import { useApi } from '../hooks/useApi';

const StateSelect = ({ 
  selectedCountry, 
  selectedState, 
  onStateSelect, 
  className = '',
  disabled = false 
}) => {
  const [searchTerm, setSearchTerm] = useState('');
  const [isManualInput, setIsManualInput] = useState(false);
  const [manualState, setManualState] = useState('');
  const [isOpen, setIsOpen] = useState(false);
  const dropdownRef = useRef(null);
  
  // Stabilize the selectedCountry reference to prevent unnecessary re-renders
  const stableSelectedCountry = useMemo(() => selectedCountry, [selectedCountry?.id, selectedCountry?.code]);
  
  // Create the API URL dynamically
  const apiUrl = useMemo(() => {
    return stableSelectedCountry?.code ? `/api/countries-states?countryCode=${stableSelectedCountry.code}` : '';
  }, [stableSelectedCountry?.code]);
  
  // Fetch states when country changes
  const { data: states = [], loading, error, setUrl } = useApi('', 'GET');
  
  // Update URL when country changes
  useEffect(() => {
    if (stableSelectedCountry?.code) {
      setUrl(`/api/countries-states?countryCode=${stableSelectedCountry.code}`);
    } else {
      setUrl('');
    }
  }, [stableSelectedCountry?.code, setUrl]);
  
  // Filter states based on search term - add null check
  const filteredStates = useMemo(() => {
    if (!states || !Array.isArray(states)) return [];
    return states.filter(state => 
      state.name.toLowerCase().includes(searchTerm.toLowerCase())
    );
  }, [states, searchTerm]);
  
  // Reset search and manual input when country changes
  useEffect(() => {
    setSearchTerm('');
    setIsManualInput(false);
    setManualState('');
    setIsOpen(false);
    // Also reset selected state when country changes
    if (onStateSelect) {
      onStateSelect(null);
    }
  }, [stableSelectedCountry?.code, onStateSelect]); // Reset when country actually changes
  
  // Reset search term when selected state changes
  useEffect(() => {
    if (selectedState && selectedState.name) {
      setSearchTerm(selectedState.name);
    } else {
      setSearchTerm('');
    }
  }, [selectedState]);
  
  const handleStateSelect = (state) => {
    console.log('StateSelect: handleStateSelect called with state', state);
    if (onStateSelect) {
      onStateSelect(state);
      setSearchTerm(state ? state.name : '');
      setIsOpen(false);
    }
  };
  
  const handleManualInputChange = (e) => {
    const value = e.target.value;
    setManualState(value);
    // If user types something, treat it as a manual entry
    if (value && onStateSelect) {
      onStateSelect({ name: value });
    } else if (!value && onStateSelect) {
      onStateSelect(null);
    }
  };
  
  // If no states are available and we're not loading, show manual input
  const showManualInput = useMemo(() => {
    return (!states || states.length === 0) && !loading && stableSelectedCountry;
  }, [states, loading, stableSelectedCountry]);
  
  // Handle clicks outside the dropdown
  useEffect(() => {
    const handleClickOutside = (event) => {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
        setIsOpen(false);
      }
    };
    
    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);
  
  return (
    <div className={className}>
      <label htmlFor="state-select" className="block text-sm font-medium text-gray-700 mb-1">
        Select your state / province
      </label>
      
      {showManualInput || isManualInput ? (
        <div className="relative">
          <input
            type="text"
            id="state-select"
            placeholder="Type your state or province"
            className="w-full px-4 py-3 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition-all duration-200"
            value={manualState}
            onChange={handleManualInputChange}
            disabled={disabled}
          />
          {!isManualInput && (
            <button
              type="button"
              className="absolute right-2 top-3 text-xs text-primary hover:text-primary-dark"
              onClick={() => setIsManualInput(false)}
            >
              Cancel
            </button>
          )}
        </div>
      ) : (
        <div className="relative" ref={dropdownRef}>
          <input
            type="text"
            id="state-select"
            placeholder={stableSelectedCountry ? "Type or select your state / province" : "Select a country first"}
            className="w-full px-4 py-3 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition-all duration-200"
            value={selectedState && selectedState.name ? selectedState.name : searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            disabled={disabled || !stableSelectedCountry}
            onFocus={() => {
              if (stableSelectedCountry && !disabled) {
                setIsOpen(true);
              }
            }}
            onClick={() => {
              if (stableSelectedCountry && !disabled) {
                setIsOpen(true);
              }
            }}
            onBlur={(e) => {
              // Delay closing to allow for clicks on dropdown items
              setTimeout(() => {
                // Check if the new focus target is outside the dropdown
                if (!dropdownRef.current?.contains(document.activeElement)) {
                  setIsOpen(false);
                }
              }, 150);
            }}
          />
          
          {/* Dropdown arrow */}
          {stableSelectedCountry && !disabled && (
            <button
              type="button"
              className="absolute right-2 top-3 text-gray-400 hover:text-gray-600"
              onClick={() => setIsOpen(!isOpen)}
            >
              <svg className="h-5 w-5" fill="currentColor" viewBox="0 0 20 20">
                <path fillRule="evenodd" d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z" clipRule="evenodd" />
              </svg>
            </button>
          )}
          
          {/* Dropdown list */}
          {isOpen && stableSelectedCountry && !disabled && (
            <div className="absolute z-10 mt-1 w-full bg-white shadow-lg rounded-md max-h-60 overflow-auto">
              {loading ? (
                <div className="px-4 py-3 text-sm text-gray-500 flex items-center">
                  <svg className="animate-spin -ml-1 mr-3 h-5 w-5 text-primary" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                    <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                    <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                  </svg>
                  Loading states...
                </div>
              ) : error ? (
                <div className="px-4 py-3 text-sm text-red-500">Error loading states: {error}</div>
              ) : filteredStates && filteredStates.length > 0 ? (
                filteredStates.map((state) => (
                  <div
                    key={state.name}
                    className="px-4 py-3 text-sm hover:bg-gray-100 cursor-pointer flex items-center"
                    onClick={() => {
                      console.log('StateSelect: Dropdown item clicked with state', state);
                      handleStateSelect(state);
                    }}
                  >
                    <span>{state.name}</span>
                  </div>
                ))
              ) : (
                <div className="px-4 py-3 text-sm text-gray-500">
                  No states/provinces available for this country.
                  <button 
                    type="button" 
                    className="ml-1 text-primary hover:underline"
                    onClick={() => setIsManualInput(true)}
                  >
                    Enter manually
                  </button>
                </div>
              )}
            </div>
          )}
          
          {(selectedState && selectedState.name) || searchTerm ? (
            <div className="mt-2 px-4 py-2 bg-blue-50 rounded-lg flex items-center">
              <span className="text-sm text-gray-700">Selected: {selectedState?.name || searchTerm}</span>
              <button
                type="button"
                className="ml-2 text-xs text-red-500 hover:text-red-700"
                onClick={() => {
                  console.log('StateSelect: Clear button clicked');
                  handleStateSelect(null);
                  setSearchTerm('');
                }}
              >
                Clear
              </button>
            </div>
          ) : null}
        </div>
      )}
    </div>
  );
};

export default StateSelect;