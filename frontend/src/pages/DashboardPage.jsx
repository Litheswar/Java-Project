import React, { useState, useEffect } from 'react';
import { motion } from 'framer-motion';
import { 
  CalendarIcon, 
  CurrencyDollarIcon, 
  ChartBarIcon, 
  SparklesIcon,
  MapPinIcon,
  TrophyIcon,
  FireIcon
} from '@heroicons/react/24/outline';
import Card from '../components/Card';
import Chart from '../components/Chart';
import Button from '../components/Button';
import ProductivityDashboard from '../components/ProductivityDashboard';
import {
  mockUser,
  mockTrips,
  mockChartData,
  mockAlerts
} from '../assets/mockData';

const DashboardPage = () => {
  const [upcomingTrips, setUpcomingTrips] = useState([]);
  const [recentAlerts, setRecentAlerts] = useState([]);
  
  useEffect(() => {
    // Filter upcoming trips
    const upcoming = mockTrips.filter(trip => trip.status === 'upcoming' || trip.status === 'planning');
    setUpcomingTrips(upcoming);
    
    // Get recent alerts (unread)
    const unreadAlerts = mockAlerts.filter(alert => !alert.read);
    setRecentAlerts(unreadAlerts);
  }, []);
  
  // Mock monthly data for productivity dashboard
  const monthlyData = [
    { month: 'Jan', trips: 2, co2Saved: 120 },
    { month: 'Feb', trips: 1, co2Saved: 80 },
    { month: 'Mar', trips: 3, co2Saved: 150 },
    { month: 'Apr', trips: 2, co2Saved: 100 },
    { month: 'May', trips: 4, co2Saved: 180 },
    { month: 'Jun', trips: 3, co2Saved: 160 }
  ];
  
  const statCards = [
    {
      title: "Eco Score",
      value: mockUser.ecoScore,
      icon: <SparklesIcon className="h-6 w-6 text-green-500" />,
      change: "+5 from last month",
      color: "from-green-400 to-green-600"
    },
    {
      title: "Travel Points",
      value: mockUser.travelPoints,
      icon: <TrophyIcon className="h-6 w-6 text-yellow-500" />,
      change: "+150 this month",
      color: "from-yellow-400 to-yellow-600"
    },
    {
      title: "Current Streak",
      value: mockUser.streak,
      icon: <FireIcon className="h-6 w-6 text-red-500" />,
      change: "Keep it up!",
      color: "from-red-400 to-red-600"
    },
    {
      title: "Active Trips",
      value: upcomingTrips.length,
      icon: <MapPinIcon className="h-6 w-6 text-blue-500" />,
      change: "Planning 1 trip",
      color: "from-blue-400 to-blue-600"
    }
  ];
  
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
              <h1 className="text-2xl font-bold text-gray-900">Dashboard</h1>
              <p className="mt-1 text-sm text-gray-600">
                Welcome back, {mockUser.name}! Here's what's happening with your trips.
              </p>
            </div>
            <div className="mt-4 flex md:mt-0 md:ml-4">
              <Button variant="primary">Create New Trip</Button>
            </div>
          </div>
          
          {/* Stats Section */}
          <div className="mt-8 grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-4">
            {statCards.map((stat, index) => (
              <motion.div
                key={index}
                initial={{ opacity: 0, y: 20 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ duration: 0.3, delay: index * 0.1 }}
              >
                <Card hoverEffect={true}>
                  <div className="flex items-center">
                    <div className={`flex-shrink-0 p-3 rounded-lg bg-gradient-to-r ${stat.color}`}>
                      {stat.icon}
                    </div>
                    <div className="ml-4">
                      <h3 className="text-sm font-medium text-gray-600">{stat.title}</h3>
                      <p className="text-2xl font-bold text-gray-900">{stat.value}</p>
                      <p className="text-xs text-gray-500 mt-1">{stat.change}</p>
                    </div>
                  </div>
                </Card>
              </motion.div>
            ))}
          </div>
          
          {/* Charts and Upcoming Trips */}
          <div className="mt-8 grid grid-cols-1 lg:grid-cols-3 gap-8">
            {/* Spending Chart */}
            <div className="lg:col-span-2">
              <Chart 
                title="Spending by Category"
                data={mockChartData.expensesByCategory}
                type="pie"
                dataKey="name"
                height={350}
              />
            </div>
            
            {/* Upcoming Trips */}
            <div>
              <div className="flex items-center justify-between mb-4">
                <h2 className="text-lg font-medium text-gray-900">Upcoming Trips</h2>
                <Button variant="ghost" size="sm">View all</Button>
              </div>
              
              <div className="space-y-4">
                {upcomingTrips.slice(0, 3).map((trip) => (
                  <motion.div
                    key={trip.id}
                    initial={{ opacity: 0, x: 20 }}
                    animate={{ opacity: 1, x: 0 }}
                    transition={{ duration: 0.3 }}
                  >
                    <Card hoverEffect={true} className="p-4">
                      <div className="flex items-start">
                        <div className="flex-shrink-0">
                          <div className="bg-gradient-to-r from-primary to-secondary w-10 h-10 rounded-lg flex items-center justify-center">
                            <MapPinIcon className="h-5 w-5 text-white" />
                          </div>
                        </div>
                        <div className="ml-3 flex-1">
                          <h3 className="text-sm font-medium text-gray-900">{trip.name}</h3>
                          <p className="text-sm text-gray-500">{trip.destination}</p>
                          <div className="mt-2 flex items-center text-sm text-gray-500">
                            <CalendarIcon className="flex-shrink-0 mr-1.5 h-4 w-4 text-gray-400" />
                            <span>{new Date(trip.startDate).toLocaleDateString()} - {new Date(trip.endDate).toLocaleDateString()}</span>
                          </div>
                          <div className="mt-2 flex items-center">
                            <CurrencyDollarIcon className="flex-shrink-0 mr-1.5 h-4 w-4 text-gray-400" />
                            <span className="text-sm text-gray-500">
                              Spent: <span className="font-medium">${trip.spent}</span> of <span className="font-medium">${trip.budget}</span>
                            </span>
                          </div>
                        </div>
                      </div>
                    </Card>
                  </motion.div>
                ))}
              </div>
            </div>
          </div>
          
          {/* Productivity Dashboard */}
          <div className="mt-8">
            <ProductivityDashboard 
              monthlyData={monthlyData}
              streak={mockUser.streak}
              ecoScore={mockUser.ecoScore}
              travelPoints={mockUser.travelPoints}
            />
          </div>
          
          {/* Eco Impact and Alerts */}
          <div className="mt-8 grid grid-cols-1 lg:grid-cols-3 gap-8">
            {/* Eco Impact Chart */}
            <div className="lg:col-span-2">
              <Chart 
                title="Eco Impact Over Time"
                data={mockChartData.ecoImpact}
                type="bar"
                dataKey="month"
                height={300}
              />
            </div>
            
            {/* Recent Alerts */}
            <div>
              <div className="flex items-center justify-between mb-4">
                <h2 className="text-lg font-medium text-gray-900">Recent Alerts</h2>
                <Button variant="ghost" size="sm">View all</Button>
              </div>
              
              <div className="space-y-4">
                {recentAlerts.slice(0, 3).map((alert) => (
                  <motion.div
                    key={alert.id}
                    initial={{ opacity: 0, x: 20 }}
                    animate={{ opacity: 1, x: 0 }}
                    transition={{ duration: 0.3 }}
                  >
                    <Card className="p-4">
                      <div className="flex">
                        <div className={`flex-shrink-0 ${
                          alert.type === 'warning' ? 'text-yellow-500' : 
                          alert.type === 'success' ? 'text-green-500' : 'text-blue-500'
                        }`}>
                          {alert.type === 'warning' ? (
                            <svg className="h-5 w-5" fill="currentColor" viewBox="0 0 20 20">
                              <path fillRule="evenodd" d="M8.257 3.099c.765-1.36 2.722-1.36 3.486 0l5.58 9.92c.75 1.334-.213 2.98-1.742 2.98H4.42c-1.53 0-2.493-1.646-1.743-2.98l5.58-9.92zM11 13a1 1 0 11-2 0 1 1 0 012 0zm-1-8a1 1 0 00-1 1v3a1 1 0 002 0V6a1 1 0 00-1-1z" clipRule="evenodd" />
                            </svg>
                          ) : alert.type === 'success' ? (
                            <svg className="h-5 w-5" fill="currentColor" viewBox="0 0 20 20">
                              <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clipRule="evenodd" />
                            </svg>
                          ) : (
                            <svg className="h-5 w-5" fill="currentColor" viewBox="0 0 20 20">
                              <path fillRule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z" clipRule="evenodd" />
                            </svg>
                          )}
                        </div>
                        <div className="ml-3 flex-1">
                          <h3 className="text-sm font-medium text-gray-900">{alert.title}</h3>
                          <p className="mt-1 text-sm text-gray-500">{alert.message}</p>
                          <p className="mt-2 text-xs text-gray-400">
                            {new Date(alert.date).toLocaleDateString()}
                          </p>
                        </div>
                      </div>
                    </Card>
                  </motion.div>
                ))}
              </div>
            </div>
          </div>
        </motion.div>
      </div>
    </div>
  );
};

export default DashboardPage;