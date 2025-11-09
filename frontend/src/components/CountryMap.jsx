import React, { useEffect, useRef, useState } from 'react';
import L from 'leaflet';
import 'leaflet.markercluster';
import 'leaflet.markercluster/dist/MarkerCluster.css';
import 'leaflet.markercluster/dist/MarkerCluster.Default.css';

// Fix for default marker icons in Leaflet
delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
  iconRetinaUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon-2x.png',
  iconUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon.png',
  shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-shadow.png',
});

// Custom marker icons
const countryIcon = new L.Icon({
  iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-blue.png',
  shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-shadow.png',
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
  shadowSize: [41, 41]
});

const stateIcon = new L.Icon({
  iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-green.png',
  shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-shadow.png',
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
  shadowSize: [41, 41]
});

const CountryMap = ({ selectedCountry, onCountrySelect }) => {
  const mapRef = useRef(null);
  const countryMarkersRef = useRef([]);
  const stateMarkersRef = useRef([]);
  const [isLoading, setIsLoading] = useState(false);
  const mapInstanceRef = useRef(null);
  const [isWorldView, setIsWorldView] = useState(true);
  const [breadcrumb, setBreadcrumb] = useState(['World']);

  // Initialize Leaflet map only once
  useEffect(() => {
    if (!mapInstanceRef.current) {
      // Use setTimeout to ensure the DOM element is available
      const timer = setTimeout(() => {
        const mapElement = document.getElementById('map');
        if (mapElement) {
          mapInstanceRef.current = L.map(mapElement, {
            center: [20, 0],
            zoom: 2
          });
          L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png")
            .addTo(mapInstanceRef.current);
          
          // Load all countries initially
          loadAllCountries();
        }
      }, 100);
      
      return () => clearTimeout(timer);
    }

    // Cleanup function
    return () => {
      if (mapInstanceRef.current) {
        mapInstanceRef.current.remove();
        mapInstanceRef.current = null;
      }
    };
  }, []);

  // Clear all country markers from the map
  const clearCountryMarkers = () => {
    countryMarkersRef.current.forEach(marker => {
      if (mapInstanceRef.current) {
        mapInstanceRef.current.removeLayer(marker);
      }
    });
    countryMarkersRef.current = [];
  };

  // Clear all state markers from the map
  const clearStateMarkers = () => {
    stateMarkersRef.current.forEach(marker => {
      if (mapInstanceRef.current) {
        mapInstanceRef.current.removeLayer(marker);
      }
    });
    stateMarkersRef.current = [];
  };

  // Load all countries as pins on initial render
  const loadAllCountries = async () => {
    if (!mapInstanceRef.current) return;
    
    try {
      const response = await fetch('http://localhost:8080/api/countries');
      if (response.ok) {
        const countries = await response.json();
        
        // Clear existing markers
        clearCountryMarkers();
        
        // Add country markers
        countryMarkersRef.current = countries.map(country => {
          if (country.coordinates && country.coordinates.lat && country.coordinates.lng) {
            return L.marker([country.coordinates.lat, country.coordinates.lng], { icon: countryIcon })
              .bindTooltip(country.name)
              .on("click", () => handleCountrySelect(country.code))
              .addTo(mapInstanceRef.current);
          }
          return null;
        }).filter(marker => marker !== null);
        
        // Fit map to show all country markers
        if (countryMarkersRef.current.length > 0) {
          const group = L.featureGroup(countryMarkersRef.current);
          mapInstanceRef.current.fitBounds(group.getBounds(), { padding: [40, 40] });
        }
      }
    } catch (error) {
      console.error('Error loading countries:', error);
    }
  };

  // Handle country selection (via card click OR map marker click)
  const handleCountrySelect = async (countryCode) => {
    if (!mapInstanceRef.current) return;
  
    setIsLoading(true);
  
    try {
      // Fetch state list from backend using query parameters (more reliable in Spark Java)
      const response = await fetch(`http://localhost:8080/api/countries-states?countryCode=${countryCode}`);
    
      if (response.ok) {
        const states = await response.json();
      
        // Remove all country markers
        clearCountryMarkers();
      
        // Remove previous state markers
        clearStateMarkers();
      
        // Add new state markers
        stateMarkersRef.current = states.map(state => {
          if (state.lat && state.lng) {
            return L.marker([state.lat, state.lng], { icon: stateIcon })
              .bindPopup(`<b>${state.name}</b>`)
              .addTo(mapInstanceRef.current);
          }
          return null;
        }).filter(marker => marker !== null);
      
        // Fit map to all state markers with smooth zoom animation
        if (stateMarkersRef.current.length > 0) {
          const group = L.featureGroup(stateMarkersRef.current);
          mapInstanceRef.current.flyToBounds(group.getBounds(), { padding: [40, 40], duration: 1.5 });
        }
      
        // Update state
        setIsWorldView(false);
        setBreadcrumb(['World', countryCode, 'States']);
      
        // Notify parent component
        if (onCountrySelect) {
          onCountrySelect(countryCode);
        }
      }
    } catch (error) {
      console.error('Error fetching states:', error);
    } finally {
      setIsLoading(false);
    }
  };

  // Return to world view
  const returnToWorldView = () => {
    if (!mapInstanceRef.current) return;
    
    // Clear state markers
    clearStateMarkers();
    
    // Load all countries again
    loadAllCountries();
    
    // Update state
    setIsWorldView(true);
    setBreadcrumb(['World']);
  };

  // Update map markers when selected country changes from parent
  useEffect(() => {
    if (!mapInstanceRef.current) return;
    
    if (selectedCountry && selectedCountry.code) {
      handleCountrySelect(selectedCountry.code);
    }
  }, [selectedCountry]);

  return (
    <div className="glass-card rounded-xl overflow-hidden h-full relative">
      {/* Loading indicator */}
      {isLoading && (
        <div className="absolute top-4 right-4 z-10 bg-white rounded-lg shadow-md p-3">
          <div className="flex items-center">
            <div className="animate-spin rounded-full h-5 w-5 border-b-2 border-primary"></div>
            <span className="ml-2 text-sm text-gray-600">Loading map data...</span>
          </div>
        </div>
      )}
      
      {/* Breadcrumb */}
      <div className="absolute top-4 left-4 z-10 bg-white rounded-lg shadow-md p-2">
        <div className="text-sm font-medium text-gray-700">
          {breadcrumb.join(' > ')}
        </div>
      </div>
      
      {/* Back to World View Button */}
      {!isWorldView && (
        <div className="absolute top-4 right-24 z-10">
          <button 
            onClick={returnToWorldView}
            className="bg-white hover:bg-gray-100 text-gray-800 font-semibold py-2 px-4 border border-gray-300 rounded-lg shadow-sm transition-all duration-200"
          >
            Back to World View
          </button>
        </div>
      )}
      
      <div id="map" style={{ height: '100%', width: '100%' }} className="rounded-xl"></div>
    </div>
  );
};

export default CountryMap;