import React, { useState, useEffect, useMemo, useRef } from 'react';
import { useApi } from '../hooks/useApi';
import Card from './Card';
import Button from './Button';
import ReactCountryFlag from 'react-country-flag';

const CountriesList = ({ onCountrySelect, selectedCountry, className = '' }) => {
  const { data: countries, loading, error } = useApi('/api/countries');
  const [searchTerm, setSearchTerm] = useState('');
  const countriesListRef = useRef(null);
  const searchInputRef = useRef(null);

  // Filter countries based on search term
  const filteredCountries = useMemo(() => {
    if (!countries) return [];
    return countries.filter(country => 
      country.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
      (country.code && country.code.toLowerCase().includes(searchTerm.toLowerCase()))
    );
  }, [countries, searchTerm]);

  const handleCountryClick = (country) => {
    if (onCountrySelect) {
      onCountrySelect(country);
      // Clear search term after selection
      setSearchTerm('');
    }
  };

  // Handle manual country selection via search input
  const handleSearchSelect = (country) => {
    if (onCountrySelect) {
      onCountrySelect(country);
      setSearchTerm('');
      searchInputRef.current?.blur();
    }
  };

  // Scroll to selected country
  useEffect(() => {
    if (selectedCountry && countriesListRef.current) {
      const selectedElement = document.getElementById(`country-${selectedCountry.id}`);
      if (selectedElement) {
        selectedElement.scrollIntoView({ 
          behavior: 'smooth', 
          block: 'nearest', 
          inline: 'center' 
        });
      }
    }
  }, [selectedCountry]);

  if (loading) {
    return (
      <div className="flex justify-center items-center h-64">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-primary"></div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="bg-red-50 border border-red-200 rounded-lg p-4">
        <p className="text-red-800">Error loading countries: {error}</p>
      </div>
    );
  }

  return (
    <div className={className}>
      {/* Search Bar with Autocomplete */}
      <div className="relative mb-4">
        <input
          ref={searchInputRef}
          type="text"
          placeholder="Type or select a country"
          className="w-full px-4 py-3 pl-10 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent transition-all duration-200"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          onKeyDown={(e) => {
            // Handle Enter key to select first country in list
            if (e.key === 'Enter' && filteredCountries.length > 0) {
              handleSearchSelect(filteredCountries[0]);
            }
          }}
        />
        <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
          <svg className="h-5 w-5 text-gray-400" fill="currentColor" viewBox="0 0 20 20">
            <path fillRule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" clipRule="evenodd" />
          </svg>
        </div>
        
        {/* Autocomplete Dropdown */}
        {searchTerm && filteredCountries.length > 0 && (
          <div className="absolute z-10 mt-1 w-full bg-white shadow-lg rounded-md max-h-60 overflow-auto">
            {filteredCountries.slice(0, 9).map((country) => (
              <div
                key={country.id}
                className="px-4 py-3 text-sm hover:bg-gray-100 cursor-pointer flex items-center"
                onClick={() => handleSearchSelect(country)}
              >
                <div className="flex-shrink-0 w-6 h-6 mr-3">
                  {country.code ? (
                    <ReactCountryFlag
                      countryCode={country.code}
                      svg
                      style={{
                        width: '1.5rem',
                        height: '1.5rem',
                        borderRadius: '0.25rem'
                      }}
                    />
                  ) : (
                    <div className="bg-gradient-to-br from-primary to-secondary rounded w-6 h-6 flex items-center justify-center">
                      <span className="text-xs font-bold text-white">
                        {country.name.substring(0, 2).toUpperCase()}
                      </span>
                    </div>
                  )}
                </div>
                <span>{country.name}</span>
              </div>
            ))}
          </div>
        )}
      </div>

      {/* Vertical Scrollable Countries Grid */}
      <div 
        ref={countriesListRef}
        className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 max-h-[500px] overflow-y-auto pr-2"
      >
        {filteredCountries.length > 0 ? (
          filteredCountries.map((country) => (
            <div 
              key={country.id}
              className="transition-all duration-300 ease-in-out"
            >
              <Card 
                id={`country-${country.id}`}
                hoverEffect={true}
                className={`w-full cursor-pointer transition-all duration-200 ${
                  selectedCountry?.id === country.id 
                    ? 'ring-4 ring-primary ring-opacity-50 shadow-xl scale-[1.02] bg-blue-50' 
                    : 'hover:shadow-lg hover:scale-[1.02]'
                }`}
                onClick={() => handleCountryClick(country)}
              >
                <div className="flex flex-col items-center p-4">
                  {/* Country Flag */}
                  <div className="flex-shrink-0 w-16 h-16 mb-3 flex items-center justify-center">
                    {country.code ? (
                      <ReactCountryFlag
                        countryCode={country.code}
                        svg
                        style={{
                          width: '3rem',
                          height: '3rem',
                          borderRadius: '0.5rem',
                          boxShadow: selectedCountry?.id === country.id ? '0 0 0 2px #3b82f6' : 'none'
                        }}
                        title={country.code}
                      />
                    ) : (
                      <div className={`bg-gradient-to-br from-primary to-secondary rounded-lg w-12 h-12 flex items-center justify-center ${
                        selectedCountry?.id === country.id ? 'ring-2 ring-blue-500' : ''
                      }`}>
                        <span className="text-xs font-bold text-white">
                          {country.name.substring(0, 2).toUpperCase()}
                        </span>
                      </div>
                    )}
                  </div>
                  
                  <div className="flex flex-col items-center">
                    <h3 className="text-lg font-bold text-gray-900 text-center">{country.name}</h3>
                    
                    {/* Sustainability Score Badge */}
                    {country.sustainabilityScore && (
                      <div className="mt-2">
                        <div className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-green-100 text-green-800">
                          <svg className="mr-1 h-4 w-4 text-green-500" fill="currentColor" viewBox="0 0 20 20">
                            <path fillRule="evenodd" d="M5.293 9.707a1 1 0 010-1.414l4-4a1 1 0 011.414 0l4 4a1 1 0 01-1.414 1.414L11 7.414V15a1 1 0 11-2 0V7.414L6.707 9.707a1 1 0 01-1.414 0z" clipRule="evenodd" />
                          </svg>
                          {country.sustainabilityScore}% Sustainable
                        </div>
                      </div>
                    )}
                  </div>
                </div>
              </Card>
            </div>
          ))
        ) : (
          <div className="text-center py-12 col-span-full">
            <svg className="mx-auto h-12 w-12 text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3.055 11H5a2 2 0 012 2v1a2 2 0 002 2 2 2 0 012 2v2.945M8 3.935V5.5A2.5 2.5 0 0010.5 8h.5a2 2 0 012 2 2 2 0 104 0 2 2 0 012-2h1.064M15 20.488V18a2 2 0 012-2h3.064M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
            </svg>
            <h3 className="mt-2 text-sm font-medium text-gray-900">No countries found</h3>
            <p className="mt-1 text-sm text-gray-500">
              {searchTerm ? 'Try adjusting your search terms.' : 'There are no countries available.'}
            </p>
          </div>
        )}
      </div>
    </div>
  );
};

export default CountriesList;