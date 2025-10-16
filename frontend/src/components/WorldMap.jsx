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
    return L.divIcon({
      className: 'custom-icon',
      html: `
        <div class="relative">
          <div class="w-6 h-6 rounded-full bg-${color}-500 border-2 border-white shadow-lg flex items-center justify-center">
            <div class="w-2 h-2 rounded-full bg-white"></div>
          </div>
          <div class="absolute -bottom-1 left-1/2 transform -translate-x-1/2 w-0 h-0 border-l-4 border-r-4 border-t-4 border-l-transparent border-r-transparent border-t-${color}-500"></div>
        </div>
      `,
      iconSize: [24, 24],
      iconAnchor: [12, 24],
    });
  };

  return (
    <motion.div
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      transition={{ duration: 0.5 }}
      className="glass-card rounded-xl overflow-hidden h-96"
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
              <div className="font-semibold">{destination.name}</div>
              <div className="text-sm text-gray-600">{destination.country}</div>
              <div className="mt-1 flex items-center">
                <span className="inline-flex items-center px-2 py-1 rounded-full text-xs font-medium bg-green-100 text-green-800">
                  🌱 {destination.sustainabilityScore}% sustainable
                </span>
              </div>
              <div className="mt-2 text-sm">{destination.description}</div>
            </Popup>
          </Marker>
        ))}
      </MapContainer>
    </motion.div>
  );
};

export default WorldMap;