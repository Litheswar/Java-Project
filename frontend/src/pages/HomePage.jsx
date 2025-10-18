import React from 'react';
import { useAppContext } from '../context/AppContext';
import { useAuth } from '../context/AuthContext';
import { motion } from 'framer-motion';
import { 
  CalendarIcon, 
  CurrencyDollarIcon, 
  ChartBarIcon, 
  BellIcon,
  MapPinIcon,
  ArrowRightIcon
} from '@heroicons/react/24/outline';
import Card from '../components/Card';
import Button from '../components/Button';
import TravelPersona from '../components/TravelPersona';
import BadgeProgress from '../components/BadgeProgress';
import ProductivityDashboard from '../components/ProductivityDashboard';

const HomePage = () => {
  const { user: appUser, trips, alerts } = useAppContext();
  const { user: authUser } = useAuth();
  
  // Use authUser for authentication status and appUser for app data
  const user = authUser || appUser;
  
  // Get upcoming trips
  const upcomingTrips = trips ? trips.filter(trip => trip.status === 'upcoming' || trip.status === 'planning')
    .sort((a, b) => new Date(a.startDate) - new Date(b.startDate))
    .slice(0, 3) : [];
  
  // Get recent alerts
  const recentAlerts = alerts ? alerts.filter(alert => !alert.read)
    .sort((a, b) => new Date(b.date) - new Date(a.date))
    .slice(0, 3) : [];
  
  // Calculate stats
  const totalTrips = trips ? trips.length : 0;
  const completedTrips = trips ? trips.filter(trip => trip.status === 'completed').length : 0;
  const totalBudget = trips ? trips.reduce((sum, trip) => sum + (trip.budget || 0), 0) : 0;
  const totalSpent = trips ? trips.reduce((sum, trip) => sum + (trip.spent || 0), 0) : 0;
  
  return (
    <div className="min-h-screen py-8">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        {/* Welcome Section */}
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.3 }}
          className="mb-8"
        >
          <h1 className="text-3xl font-bold text-gray-900">
            Welcome back, <span className="gradient-text">{user?.name || 'Traveler'}</span>
          </h1>
          <p className="mt-2 text-gray-600">
            Ready to plan your next eco-friendly adventure?
          </p>
        </motion.div>
        
        {/* Stats Overview */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.3, delay: 0.1 }}
          >
            <Card className="h-full">
              <div className="flex items-center">
                <div className="p-3 rounded-lg bg-blue-100">
                  <MapPinIcon className="h-6 w-6 text-blue-600" />
                </div>
                <div className="ml-4">
                  <p className="text-sm font-medium text-gray-600">Total Trips</p>
                  <p className="text-2xl font-bold text-gray-900">{totalTrips}</p>
                </div>
              </div>
            </Card>
          </motion.div>
          
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.3, delay: 0.2 }}
          >
            <Card className="h-full">
              <div className="flex items-center">
                <div className="p-3 rounded-lg bg-green-100">
                  <ChartBarIcon className="h-6 w-6 text-green-600" />
                </div>
                <div className="ml-4">
                  <p className="text-sm font-medium text-gray-600">Completed</p>
                  <p className="text-2xl font-bold text-gray-900">{completedTrips}</p>
                </div>
              </div>
            </Card>
          </motion.div>
          
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.3, delay: 0.3 }}
          >
            <Card className="h-full">
              <div className="flex items-center">
                <div className="p-3 rounded-lg bg-yellow-100">
                  <CurrencyDollarIcon className="h-6 w-6 text-yellow-600" />
                </div>
                <div className="ml-4">
                  <p className="text-sm font-medium text-gray-600">Total Budget</p>
                  <p className="text-2xl font-bold text-gray-900">${totalBudget.toLocaleString()}</p>
                </div>
              </div>
            </Card>
          </motion.div>
          
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.3, delay: 0.4 }}
          >
            <Card className="h-full">
              <div className="flex items-center">
                <div className="p-3 rounded-lg bg-purple-100">
                  <BellIcon className="h-6 w-6 text-purple-600" />
                </div>
                <div className="ml-4">
                  <p className="text-sm font-medium text-gray-600">Alerts</p>
                  <p className="text-2xl font-bold text-gray-900">{recentAlerts.length}</p>
                </div>
              </div>
            </Card>
          </motion.div>
        </div>
        
        {/* Main Content Grid */}
        <div className="grid grid-cols-1 xl:grid-cols-3 gap-6">
          {/* Left Column - 2/3 width on large screens */}
          <div className="xl:col-span-2 flex flex-col gap-6">
            {/* Travel Persona */}
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.3, delay: 0.5 }}
              className="bg-white rounded-2xl shadow-md p-6"
            >
              <h2 className="text-xl font-bold text-gray-900 mb-4">Your Travel Persona</h2>
              <TravelPersona persona={user?.travelPersona} />
            </motion.div>
            
            {/* Upcoming Trips */}
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.3, delay: 0.6 }}
              className="bg-white rounded-2xl shadow-md p-6"
            >
              <div className="flex justify-between items-center mb-4">
                <h2 className="text-xl font-bold text-gray-900">Upcoming Trips</h2>
                <Button variant="secondary" size="sm">
                  View All
                </Button>
              </div>
              
              {upcomingTrips.length > 0 ? (
                <div className="space-y-4">
                  {upcomingTrips.map((trip) => (
                    <div key={trip.id} className="flex items-center p-4 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors">
                      <div className="flex-shrink-0">
                        <div className="bg-gradient-to-r from-primary to-secondary w-12 h-12 rounded-lg flex items-center justify-center">
                          <CalendarIcon className="h-6 w-6 text-white" />
                        </div>
                      </div>
                      <div className="ml-4 flex-1">
                        <h3 className="font-medium text-gray-900">{trip.name}</h3>
                        <p className="text-sm text-gray-600">{trip.destination}</p>
                        <p className="text-xs text-gray-500">
                          {new Date(trip.startDate).toLocaleDateString()} - {new Date(trip.endDate).toLocaleDateString()}
                        </p>
                      </div>
                      <div className="ml-4">
                        <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-blue-100 text-blue-800">
                          {trip.status.charAt(0).toUpperCase() + trip.status.slice(1)}
                        </span>
                      </div>
                    </div>
                  ))}
                </div>
              ) : (
                <div className="text-center py-8">
                  <MapPinIcon className="mx-auto h-12 w-12 text-gray-400" />
                  <h3 className="mt-2 text-sm font-medium text-gray-900">No upcoming trips</h3>
                  <p className="mt-1 text-sm text-gray-500">Get started by planning your next adventure.</p>
                  <div className="mt-6">
                    <Button variant="primary">
                      Plan a Trip
                    </Button>
                  </div>
                </div>
              )}
            </motion.div>
          </div>
          
          {/* Right Column - 1/3 width on large screens */}
          <div className="flex flex-col gap-6">
            {/* Eco Impact */}
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.3, delay: 0.7 }}
              className="bg-white rounded-2xl shadow-md p-6"
            >
              <h2 className="text-xl font-bold text-gray-900 mb-4">Eco Impact</h2>
              <ProductivityDashboard 
                ecoScore={user?.ecoScore || 0} 
                travelPoints={user?.travelPoints || 0} 
                streak={user?.streak || 0} 
              />
            </motion.div>
            
            {/* Badges */}
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.3, delay: 0.8 }}
              className="bg-white rounded-2xl shadow-md p-6"
            >
              <h2 className="text-xl font-bold text-gray-900 mb-4">Your Badges</h2>
              <BadgeProgress 
                currentBadges={user?.badges || []} 
                progress={Math.min(100, (user?.ecoScore || 0) * 2)} 
              />
            </motion.div>
            
            {/* Recent Alerts */}
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.3, delay: 0.9 }}
              className="bg-white rounded-2xl shadow-md p-6"
            >
              <div className="flex justify-between items-center mb-4">
                <h2 className="text-xl font-bold text-gray-900">Recent Alerts</h2>
                <Button variant="secondary" size="sm">
                  View All
                </Button>
              </div>
              
              {recentAlerts.length > 0 ? (
                <div className="space-y-3">
                  {recentAlerts.map((alert) => (
                    <div key={alert.id} className="flex items-start p-3 bg-gray-50 rounded-lg">
                      <div className={`flex-shrink-0 mt-1 w-3 h-3 rounded-full ${
                        alert.type === 'warning' ? 'bg-yellow-500' : 
                        alert.type === 'success' ? 'bg-green-500' : 'bg-blue-500'
                      }`}></div>
                      <div className="ml-3 flex-1">
                        <p className="text-sm font-medium text-gray-900">{alert.title}</p>
                        <p className="text-sm text-gray-600">{alert.message}</p>
                        <p className="text-xs text-gray-500 mt-1">
                          {new Date(alert.date).toLocaleDateString()}
                        </p>
                      </div>
                    </div>
                  ))}
                </div>
              ) : (
                <div className="text-center py-4">
                  <BellIcon className="mx-auto h-8 w-8 text-gray-400" />
                  <p className="mt-1 text-sm text-gray-500">No new alerts</p>
                </div>
              )}
            </motion.div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default HomePage;