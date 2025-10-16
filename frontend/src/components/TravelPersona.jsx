import React from 'react';
import { motion } from 'framer-motion';
import { 
  UserCircleIcon,
  GlobeAltIcon,
  SparklesIcon,
  FireIcon,
  TrophyIcon
} from '@heroicons/react/24/outline';

const TravelPersona = ({ persona, ecoScore, travelPoints, streak }) => {
  const personas = {
    "Eco Explorer": {
      icon: <SparklesIcon className="h-8 w-8 text-green-500" />,
      color: "from-green-400 to-green-600",
      description: "You're passionate about sustainable travel and making a positive impact on the planet.",
      badge: "🌱 Eco Warrior"
    },
    "Culture Seeker": {
      icon: <GlobeAltIcon className="h-8 w-8 text-blue-500" />,
      color: "from-blue-400 to-blue-600",
      description: "You love immersing yourself in local cultures and authentic experiences.",
      badge: "🗺️ Cultural Explorer"
    },
    "Adventure Junkie": {
      icon: <FireIcon className="h-8 w-8 text-red-500" />,
      color: "from-red-400 to-red-600",
      description: "You seek thrilling adventures and adrenaline-pumping experiences.",
      badge: "🔥 Adventure Master"
    },
    "Luxury Traveler": {
      icon: <TrophyIcon className="h-8 w-8 text-purple-500" />,
      color: "from-purple-400 to-purple-600",
      description: "You appreciate the finer things in travel and seek premium experiences.",
      badge: "💎 Luxury Connoisseur"
    }
  };

  const currentPersona = personas[persona] || personas["Eco Explorer"];

  return (
    <motion.div
      initial={{ opacity: 0, scale: 0.9 }}
      animate={{ opacity: 1, scale: 1 }}
      transition={{ duration: 0.3 }}
      className="glass-card rounded-xl p-6"
    >
      <div className="flex flex-col items-center text-center">
        <div className={`flex items-center justify-center w-16 h-16 rounded-full bg-gradient-to-r ${currentPersona.color} text-white mb-4`}>
          {currentPersona.icon}
        </div>
        
        <h3 className="text-xl font-bold text-gray-900">{persona}</h3>
        <p className="mt-2 text-sm text-gray-600">{currentPersona.description}</p>
        
        <div className="mt-4 inline-flex items-center px-3 py-1 rounded-full text-sm font-medium bg-blue-100 text-blue-800">
          {currentPersona.badge}
        </div>
        
        <div className="mt-6 grid grid-cols-3 gap-4 w-full">
          <div className="text-center">
            <div className="text-2xl font-bold text-gray-900">{ecoScore}</div>
            <div className="text-xs text-gray-500">Eco Score</div>
          </div>
          <div className="text-center">
            <div className="text-2xl font-bold text-gray-900">{travelPoints}</div>
            <div className="text-xs text-gray-500">Points</div>
          </div>
          <div className="text-center">
            <div className="text-2xl font-bold text-gray-900">{streak}</div>
            <div className="text-xs text-gray-500">Day Streak</div>
          </div>
        </div>
      </div>
    </motion.div>
  );
};

export default TravelPersona;