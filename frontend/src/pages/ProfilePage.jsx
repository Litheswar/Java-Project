import React, { useState, useEffect } from 'react';
import { motion } from 'framer-motion';
import { 
  UserIcon, 
  ChartBarIcon, 
  TrophyIcon, 
  SparklesIcon,
  FireIcon,
  CalendarIcon,
  MapPinIcon,
  GlobeAltIcon
} from '@heroicons/react/24/outline';
import Card from '../components/Card';
import Chart from '../components/Chart';
import Button from '../components/Button';
import TravelPersona from '../components/TravelPersona';
import BadgeProgress from '../components/BadgeProgress';
import WorldMap from '../components/WorldMap';
import { userService } from '../services/userService';
import { tripService } from '../services/tripService';
import { tripHistoryService } from '../services/tripHistoryService';
import { useAppContext } from '../context/AppContext';

const ProfilePage = () => {
  const { user: contextUser, updateUser } = useAppContext();
  const [activeTab, setActiveTab] = useState('overview');
  const [user, setUser] = useState(contextUser);
  const [trips, setTrips] = useState([]);
  const [tripHistory, setTripHistory] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  
  // Fetch user data, trips, and trip history when component mounts
  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);
        
        // Fetch user data
        const userData = await userService.getUserById(contextUser.id);
        setUser(userData);
        updateUser(userData);
        
        // Fetch trips for the user
        const tripsData = await tripService.getTripsByUserId(contextUser.id);
        setTrips(tripsData);
        
        // Fetch trip history for the user
        const tripHistoryData = await tripHistoryService.getTripHistoryByUserId(contextUser.id);
        setTripHistory(tripHistoryData);
        
        setError(null);
      } catch (err) {
        console.error('Error fetching profile data:', err);
        setError('Failed to load profile data. Please try again later.');
      } finally {
        setLoading(false);
      }
    };
    
    fetchData();
  }, [contextUser.id, updateUser]);
  
  const completedTrips = trips.filter(trip => trip.status === 'completed');
  const totalCO2Saved = completedTrips.reduce((sum, trip) => sum + (trip.co2Saved || 0), 0);
  
  // Mock destinations for the world map (in a real app, this would come from the API)
  const destinations = [
    {
      id: 1,
      name: "Paris, France",
      country: "France",
      description: "The City of Light offers world-class art, cuisine, and culture.",
      sustainabilityScore: 85,
      coordinates: [48.8566, 2.3522]
    },
    {
      id: 2,
      name: "Kyoto, Japan",
      country: "Japan",
      description: "Ancient temples, traditional gardens, and modern innovation.",
      sustainabilityScore: 92,
      coordinates: [35.0116, 135.7681]
    },
    {
      id: 3,
      name: "Costa Rica",
      country: "Costa Rica",
      description: "Biodiverse rainforests, volcanoes, and commitment to sustainability.",
      sustainabilityScore: 95,
      coordinates: [9.7489, -83.7534]
    },
    {
      id: 4,
      name: "Reykjavik, Iceland",
      country: "Iceland",
      description: "Geothermal energy, Northern Lights, and unique landscapes.",
      sustainabilityScore: 88,
      coordinates: [64.1466, -21.9426]
    }
  ];
  
  const tabs = [
    { id: 'overview', name: 'Overview' },
    { id: 'stats', name: 'Statistics' },
    { id: 'achievements', name: 'Achievements' }
  ];
  
  if (loading) {
    return (
      <div className="py-8 flex justify-center items-center h-64">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-primary"></div>
      </div>
    );
  }
  
  if (error) {
    return (
      <div className="py-8 flex justify-center items-center h-64">
        <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative" role="alert">
          <strong className="font-bold">Error! </strong>
          <span className="block sm:inline">{error}</span>
        </div>
      </div>
    );
  }
  
  const renderOverview = () => (
    <div className="space-y-8">
      {/* User Info and Travel Persona */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <div className="lg:col-span-1">
          <Card>
            <div className="flex flex-col items-center text-center">
              <div className="bg-gradient-to-r from-primary to-secondary w-20 h-20 rounded-full flex items-center justify-center text-white text-2xl font-bold">
                {user.avatar || user.name.charAt(0)}
              </div>
              <h2 className="mt-4 text-2xl font-bold text-gray-900">{user.name}</h2>
              <p className="text-gray-600">{user.email}</p>
              <div className="mt-4 flex flex-wrap justify-center gap-2">
                <span className="inline-flex items-center px-3 py-1 rounded-full text-sm font-medium bg-blue-100 text-blue-800">
                  <MapPinIcon className="h-4 w-4 mr-1" />
                  Frequent Traveler
                </span>
                <span className="inline-flex items-center px-3 py-1 rounded-full text-sm font-medium bg-green-100 text-green-800">
                  <SparklesIcon className="h-4 w-4 mr-1" />
                  Eco-Conscious
                </span>
              </div>
              <div className="mt-6 w-full">
                <Button variant="secondary" className="w-full">Edit Profile</Button>
              </div>
            </div>
          </Card>
          
          <div className="mt-6">
            <TravelPersona 
              persona={user.travelPersona || "Eco Explorer"}
              ecoScore={user.ecoScore || 0}
              travelPoints={user.travelPoints || 0}
              streak={user.streak || 0}
            />
          </div>
        </div>
        
        <div className="lg:col-span-2 space-y-6">
          {/* Stats Overview */}
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
            <Card>
              <div className="flex items-center">
                <div className="flex-shrink-0 p-3 rounded-lg bg-blue-100">
                  <MapPinIcon className="h-6 w-6 text-blue-600" />
                </div>
                <div className="ml-4">
                  <h3 className="text-sm font-medium text-gray-600">Total Trips</h3>
                  <p className="text-2xl font-bold text-gray-900">{trips.length}</p>
                </div>
              </div>
            </Card>
            
            <Card>
              <div className="flex items-center">
                <div className="flex-shrink-0 p-3 rounded-lg bg-green-100">
                  <SparklesIcon className="h-6 w-6 text-green-600" />
                </div>
                <div className="ml-4">
                  <h3 className="text-sm font-medium text-gray-600">CO₂ Saved</h3>
                  <p className="text-2xl font-bold text-gray-900">{totalCO2Saved} kg</p>
                </div>
              </div>
            </Card>
            
            <Card>
              <div className="flex items-center">
                <div className="flex-shrink-0 p-3 rounded-lg bg-yellow-100">
                  <TrophyIcon className="h-6 w-6 text-yellow-600" />
                </div>
                <div className="ml-4">
                  <h3 className="text-sm font-medium text-gray-600">Travel Points</h3>
                  <p className="text-2xl font-bold text-gray-900">{user.travelPoints || 0}</p>
                </div>
              </div>
            </Card>
            
            <Card>
              <div className="flex items-center">
                <div className="flex-shrink-0 p-3 rounded-lg bg-red-100">
                  <FireIcon className="h-6 w-6 text-red-600" />
                </div>
                <div className="ml-4">
                  <h3 className="text-sm font-medium text-gray-600">Current Streak</h3>
                  <p className="text-2xl font-bold text-gray-900">{user.streak || 0} days</p>
                </div>
              </div>
            </Card>
          </div>
          
          {/* Badge Progress */}
          <BadgeProgress 
            currentBadges={user.badges || []}
            progress={75}
          />
        </div>
      </div>
      
      {/* Destinations Map */}
      <Card>
        <div className="flex items-center justify-between mb-6">
          <h2 className="text-lg font-medium text-gray-900">Destinations Visited</h2>
          <Button variant="ghost" size="sm">View all</Button>
        </div>
        
        <WorldMap destinations={destinations} />
      </Card>
      
      {/* Recent Trips */}
      <Card>
        <div className="flex items-center justify-between mb-6">
          <h2 className="text-lg font-medium text-gray-900">Recent Trips</h2>
          <Button variant="ghost" size="sm">View all</Button>
        </div>
        
        <div className="space-y-4">
          {trips.slice(0, 3).map((trip) => (
            <div key={trip.id} className="flex items-center p-4 hover:bg-gray-50 rounded-lg">
              <div className="flex-shrink-0">
                <div className="bg-gray-200 border-2 border-dashed rounded-xl w-16 h-16" />
              </div>
              <div className="ml-4 flex-1">
                <h3 className="text-sm font-medium text-gray-900">{trip.name}</h3>
                <p className="text-sm text-gray-500">{trip.destination}</p>
                <div className="mt-1 flex items-center text-sm text-gray-500">
                  <CalendarIcon className="flex-shrink-0 mr-1.5 h-4 w-4 text-gray-400" />
                  <span>{new Date(trip.startDate).toLocaleDateString()} - {new Date(trip.endDate).toLocaleDateString()}</span>
                </div>
              </div>
              <div className="flex flex-col items-end">
                <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-green-100 text-green-800">
                  <SparklesIcon className="h-3 w-3 mr-1" />
                  {trip.co2Saved || 0} kg saved
                </span>
                <p className="mt-1 text-sm text-gray-500">
                  ${trip.spent || 0} of ${trip.budget || 0}
                </p>
              </div>
            </div>
          ))}
        </div>
      </Card>
    </div>
  );
  
  const renderStats = () => (
    <div className="space-y-8">
      {/* Eco Impact Chart - in a real app, this would come from the API */}
      <Card>
        <Chart 
          title="Eco Impact Over Time"
          data={[]} // This would be populated with real data
          type="line"
          dataKey="month"
          height={400}
        />
      </Card>
      
      {/* Spending Chart - in a real app, this would come from the API */}
      <Card>
        <Chart 
          title="Spending by Category"
          data={[]} // This would be populated with real data
          type="bar"
          dataKey="name"
          height={400}
        />
      </Card>
      
      {/* Travel History */}
      <Card>
        <h2 className="text-lg font-medium text-gray-900 mb-6">Travel History</h2>
        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-gray-200">
            <thead className="bg-gray-50">
              <tr>
                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Trip
                </th>
                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Destination
                </th>
                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Dates
                </th>
                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Budget
                </th>
                <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  CO₂ Saved
                </th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-gray-200">
              {trips.map((trip) => (
                <tr key={trip.id} className="hover:bg-gray-50">
                  <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                    {trip.name}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    {trip.destination}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    {new Date(trip.startDate).toLocaleDateString()} - {new Date(trip.endDate).toLocaleDateString()}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    ${trip.spent || 0} of ${trip.budget || 0}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-green-100 text-green-800">
                      {trip.co2Saved || 0} kg
                    </span>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </Card>
    </div>
  );
  
  const renderAchievements = () => (
    <div className="space-y-8">
      {/* Badges */}
      <Card>
        <h2 className="text-lg font-medium text-gray-900 mb-6">Your Badges</h2>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
          {(user.badges || []).map((badge) => (
            <div key={badge.id} className="flex items-center p-4 bg-gradient-to-r from-blue-50 to-green-50 rounded-lg">
              <div className="flex-shrink-0 text-2xl">
                {badge.icon}
              </div>
              <div className="ml-4">
                <h3 className="text-sm font-medium text-gray-900">{badge.name}</h3>
                <p className="text-sm text-gray-500">Achievement unlocked</p>
              </div>
            </div>
          ))}
        </div>
      </Card>
      
      {/* Eco Score Progress */}
      <Card>
        <h2 className="text-lg font-medium text-gray-900 mb-6">Eco Traveler Progress</h2>
        <div className="space-y-6">
          <div>
            <div className="flex justify-between text-sm font-medium text-gray-700">
              <span>Eco Score: {user.ecoScore || 0}/100</span>
              <span>Expert Level</span>
            </div>
            <div className="mt-2 w-full bg-gray-200 rounded-full h-4">
              <div 
                className="bg-gradient-to-r from-green-400 to-green-600 h-4 rounded-full" 
                style={{ width: `${user.ecoScore || 0}%` }}
              ></div>
            </div>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            <div className="text-center p-4 bg-green-50 rounded-lg">
              <SparklesIcon className="h-8 w-8 text-green-600 mx-auto" />
              <h3 className="mt-2 text-lg font-medium text-gray-900">{totalCO2Saved} kg</h3>
              <p className="text-sm text-gray-500">CO₂ Saved</p>
            </div>
            
            <div className="text-center p-4 bg-blue-50 rounded-lg">
              <MapPinIcon className="h-8 w-8 text-blue-600 mx-auto" />
              <h3 className="mt-2 text-lg font-medium text-gray-900">{trips.length}</h3>
              <p className="text-sm text-gray-500">Sustainable Trips</p>
            </div>
            
            <div className="text-center p-4 bg-yellow-50 rounded-lg">
              <FireIcon className="h-8 w-8 text-yellow-600 mx-auto" />
              <h3 className="mt-2 text-lg font-medium text-gray-900">{user.streak || 0}</h3>
              <p className="text-sm text-gray-500">Day Streak</p>
            </div>
          </div>
        </div>
      </Card>
      
      {/* Travel Tips */}
      <Card>
        <h2 className="text-lg font-medium text-gray-900 mb-4">Eco Travel Tips</h2>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div className="p-4 bg-blue-50 rounded-lg">
            <h3 className="font-medium text-gray-900">Choose Sustainable Accommodations</h3>
            <p className="mt-2 text-sm text-gray-600">
              Look for hotels with green certifications and eco-friendly practices.
            </p>
          </div>
          
          <div className="p-4 bg-green-50 rounded-lg">
            <h3 className="font-medium text-gray-900">Offset Your Carbon Footprint</h3>
            <p className="mt-2 text-sm text-gray-600">
              Contribute to reforestation projects or renewable energy initiatives.
            </p>
          </div>
          
          <div className="p-4 bg-yellow-50 rounded-lg">
            <h3 className="font-medium text-gray-900">Use Public Transportation</h3>
            <p className="mt-2 text-sm text-gray-600">
              Trains and buses produce significantly less CO₂ per passenger than flights.
            </p>
          </div>
          
          <div className="p-4 bg-purple-50 rounded-lg">
            <h3 className="font-medium text-gray-900">Support Local Communities</h3>
            <p className="mt-2 text-sm text-gray-600">
              Choose local guides, restaurants, and shops to boost the local economy.
            </p>
          </div>
        </div>
      </Card>
    </div>
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
              <h1 className="text-2xl font-bold text-gray-900">Profile</h1>
              <p className="mt-1 text-sm text-gray-600">
                View and manage your travel profile and achievements
              </p>
            </div>
          </div>
          
          {/* Tabs */}
          <div className="mt-8 border-b border-gray-200">
            <nav className="-mb-px flex space-x-8">
              {tabs.map((tab) => (
                <button
                  key={tab.id}
                  onClick={() => setActiveTab(tab.id)}
                  className={`whitespace-nowrap py-4 px-1 border-b-2 font-medium text-sm ${
                    activeTab === tab.id
                      ? 'border-primary text-primary'
                      : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
                  }`}
                >
                  {tab.name}
                </button>
              ))}
            </nav>
          </div>
          
          {/* Tab Content */}
          <div className="mt-8">
            {activeTab === 'overview' && renderOverview()}
            {activeTab === 'stats' && renderStats()}
            {activeTab === 'achievements' && renderAchievements()}
          </div>
        </motion.div>
      </div>
    </div>
  );
};

export default ProfilePage;