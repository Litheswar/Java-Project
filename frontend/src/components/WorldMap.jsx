import React, { useState } from 'react';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import L from 'leaflet';
import { motion } from 'framer-motion';
import 'leaflet/dist/leaflet.css';

// Fix for default marker icons in Leaflet
delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
  iconRetinaUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon-2x.png',
  iconUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon.png',
  shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-shadow.png',
});

const WorldMap = ({ destinations, onDestinationSelect }) => {
  const [selectedDestination, setSelectedDestination] = useState(null);

  // Convert sustainability score to marker color
  const getMarkerColor = (sustainabilityScore) => {
    if (sustainabilityScore >= 90) return 'green';
    if (sustainabilityScore >= 75) return 'yellow';
    return 'red';
  };

  // Custom marker icon based on sustainability
  const createCustomIcon = (sustainabilityScore) => {
    const color = getMarkerColor(sustainabilityScore);
    // Create inline styles instead of Tailwind classes
    const colorClasses = {
      green: '#10B981',
      yellow: '#FBBF24',
      red: '#EF4444'
    };
    
    const markerColor = colorClasses[color] || colorClasses.green;
    
    return L.divIcon({
      className: 'custom-icon',
      html: `
        <div style="position: relative; display: flex; flex-direction: column; align-items: center;">
          <div style="width: 24px; height: 24px; border-radius: 50%; background-color: ${markerColor}; 
                      border: 2px solid white; box-shadow: 0 2px 4px rgba(0,0,0,0.2); 
                      display: flex; align-items: center; justify-content: center;">
            <div style="width: 8px; height: 8px; border-radius: 50%; background-color: white;"></div>
          </div>
          <div style="width: 0; height: 0; border-left: 6px solid transparent; border-right: 6px solid transparent; 
                      border-top: 8px solid ${markerColor}; margin-top: -1px;"></div>
        </div>
      `,
      iconSize: [24, 32],
      iconAnchor: [12, 32],
    });
  };

  return (
    <motion.div
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      transition={{ duration: 0.5 }}
      className="glass-card rounded-xl overflow-hidden"
      style={{ height: '400px' }}
    >
      <MapContainer 
        center={[20, 0]} 
        zoom={2} 
        style={{ height: '100%', width: '100%' }}
        className="rounded-xl"
      >
        <TileLayer
          attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />
        {destinations.map((destination) => (
          <Marker
            key={destination.id}
            position={destination.coordinates}
            icon={createCustomIcon(destination.sustainabilityScore)}
            eventHandlers={{
              click: () => {
                setSelectedDestination(destination);
                if (onDestinationSelect) {
                  onDestinationSelect(destination);
                }
              },
            }}
          >
            <Popup>
              <div style={{ minWidth: '200px' }}>
                <div className="font-semibold">{destination.name}</div>
                <div className="text-sm text-gray-600">{destination.country}</div>
                <div className="mt-1 flex items-center">
                  <span className="inline-flex items-center px-2 py-1 rounded-full text-xs font-medium bg-green-100 text-green-800">
                    🌱 {destination.sustainabilityScore}% sustainable
                  </span>
                </div>
                <div className="mt-2 text-sm">{destination.description}</div>
              </div>
            </Popup>
          </Marker>
        ))}
      </MapContainer>
    </motion.div>
  );
};

export default WorldMap;