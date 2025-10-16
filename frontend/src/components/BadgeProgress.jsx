import React from 'react';
import { motion } from 'framer-motion';
import { TrophyIcon } from '@heroicons/react/24/outline';

const BadgeProgress = ({ currentBadges, nextBadge, progress }) => {
  const badges = [
    { id: 1, name: "First Trip", icon: "✈️", requirement: 1, description: "Complete your first trip" },
    { id: 2, name: "Eco Warrior", icon: "🌱", requirement: 50, description: "Save 50kg of CO2" },
    { id: 3, name: "Explorer", icon: "🗺️", requirement: 5, description: "Visit 5 different destinations" },
    { id: 4, name: "Budget Master", icon: "💰", requirement: 1000, description: "Save $1000 on trips" },
    { id: 5, name: "Streak Master", icon: "🔥", requirement: 30, description: "Maintain a 30-day travel streak" },
    { id: 6, name: "Carbon Neutral", icon: "🌍", requirement: 200, description: "Save 200kg of CO2" }
  ];

  const getNextBadge = () => {
    // Find the next badge the user hasn't achieved yet
    const next = badges.find(badge => 
      !currentBadges.some(b => b.id === badge.id)
    );
    return next || badges[badges.length - 1];
  };

  const nextBadgeData = nextBadge || getNextBadge();

  return (
    <motion.div
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      className="glass-card rounded-xl p-6"
    >
      <div className="flex items-center justify-between mb-4">
        <h3 className="text-lg font-semibold text-gray-900">Next Badge</h3>
        <TrophyIcon className="h-5 w-5 text-yellow-500" />
      </div>
      
      <div className="flex items-center">
        <div className="flex-shrink-0 w-16 h-16 rounded-full bg-yellow-100 flex items-center justify-center text-2xl">
          {nextBadgeData.icon}
        </div>
        
        <div className="ml-4 flex-1">
          <h4 className="text-lg font-medium text-gray-900">{nextBadgeData.name}</h4>
          <p className="text-sm text-gray-500">{nextBadgeData.description}</p>
          
          <div className="mt-3">
            <div className="flex justify-between text-sm font-medium text-gray-700">
              <span>Progress</span>
              <span>{progress}%</span>
            </div>
            <div className="mt-1 w-full bg-gray-200 rounded-full h-2.5">
              <motion.div 
                className="bg-gradient-to-r from-yellow-400 to-yellow-600 h-2.5 rounded-full" 
                initial={{ width: 0 }}
                animate={{ width: `${progress}%` }}
                transition={{ duration: 1, delay: 0.5 }}
              ></motion.div>
            </div>
          </div>
        </div>
      </div>
      
      <div className="mt-6">
        <h4 className="text-sm font-medium text-gray-900 mb-2">Your Badges</h4>
        <div className="flex flex-wrap gap-2">
          {currentBadges.map((badge) => (
            <motion.div
              key={badge.id}
              whileHover={{ scale: 1.1 }}
              className="flex items-center px-3 py-1 rounded-full text-sm font-medium bg-blue-100 text-blue-800"
            >
              <span className="mr-1">{badge.icon}</span>
              {badge.name}
            </motion.div>
          ))}
        </div>
      </div>
    </motion.div>
  );
};

export default BadgeProgress;