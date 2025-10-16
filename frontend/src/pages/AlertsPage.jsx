import React, { useState, useEffect } from 'react';
import { motion } from 'framer-motion';
import { 
  BellIcon, 
  CheckCircleIcon, 
  ExclamationTriangleIcon,
  InformationCircleIcon,
  XMarkIcon,
  ClockIcon,
  LightBulbIcon,
  SparklesIcon
} from '@heroicons/react/24/outline';
import Card from '../components/Card';
import Button from '../components/Button';
import {
  mockAlerts
} from '../assets/mockData';

const AlertsPage = () => {
  const [alerts, setAlerts] = useState([]);
  const [filter, setFilter] = useState('all');
  
  useEffect(() => {
    setAlerts(mockAlerts);
  }, []);
  
  // Mock countdown alerts
  const countdownAlerts = [
    {
      id: 4,
      type: "info",
      title: "Trip Countdown",
      message: "Your trip to Swiss Alps starts in 3 days. Don't forget to pack your hiking boots!",
      date: "2023-08-07",
      read: false,
      countdown: 3
    },
    {
      id: 5,
      type: "info",
      title: "Trip Countdown",
      message: "Your trip to Bali starts in 15 days. Time to research local customs and attractions.",
      date: "2023-07-17",
      read: false,
      countdown: 15
    }
  ];
  
  // Mock eco-friendly suggestions
  const ecoSuggestions = [
    {
      id: 6,
      type: "success",
      title: "Eco-Friendly Tip",
      message: "For your upcoming trip to Swiss Alps, consider taking the train instead of flying to reduce your carbon footprint by 90%.",
      date: "2023-07-20",
      read: false
    },
    {
      id: 7,
      type: "success",
      title: "Eco-Friendly Tip",
      message: "When in Bali, choose locally-owned accommodations and restaurants to support the local economy.",
      date: "2023-07-20",
      read: false
    }
  ];
  
  // Combine all alerts
  const allAlerts = [...countdownAlerts, ...ecoSuggestions, ...mockAlerts];
  
  const filteredAlerts = filter === 'all' 
    ? allAlerts 
    : filter === 'unread' 
      ? allAlerts.filter(alert => !alert.read)
      : allAlerts.filter(alert => alert.read);
  
  const markAsRead = (id) => {
    setAlerts(prev => prev.map(alert => 
      alert.id === id ? { ...alert, read: true } : alert
    ));
  };
  
  const markAllAsRead = () => {
    setAlerts(prev => prev.map(alert => ({ ...alert, read: true })));
  };
  
  const deleteAlert = (id) => {
    setAlerts(prev => prev.filter(alert => alert.id !== id));
  };
  
  const unreadCount = allAlerts.filter(alert => !alert.read).length;
  
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
              <h1 className="text-2xl font-bold text-gray-900">Travel Alerts</h1>
              <p className="mt-1 text-sm text-gray-600">
                Stay informed with important travel notifications
              </p>
            </div>
            <div className="mt-4 flex md:mt-0 md:ml-4">
              <Button 
                variant="secondary" 
                onClick={markAllAsRead}
                disabled={unreadCount === 0}
              >
                Mark All as Read
              </Button>
            </div>
          </div>
          
          {/* Stats */}
          <div className="mt-8 grid grid-cols-1 md:grid-cols-3 gap-6">
            <Card className="md:col-span-1">
              <div className="flex items-center">
                <div className="flex-shrink-0 p-3 rounded-lg bg-blue-100">
                  <BellIcon className="h-6 w-6 text-blue-600" />
                </div>
                <div className="ml-4">
                  <h3 className="text-sm font-medium text-gray-600">Total Alerts</h3>
                  <p className="text-2xl font-bold text-gray-900">{alerts.length}</p>
                </div>
              </div>
            </Card>
            
            <Card className="md:col-span-1">
              <div className="flex items-center">
                <div className="flex-shrink-0 p-3 rounded-lg bg-yellow-100">
                  <ExclamationTriangleIcon className="h-6 w-6 text-yellow-600" />
                </div>
                <div className="ml-4">
                  <h3 className="text-sm font-medium text-gray-600">Unread Alerts</h3>
                  <p className="text-2xl font-bold text-gray-900">{unreadCount}</p>
                </div>
              </div>
            </Card>
            
            <Card className="md:col-span-1">
              <div className="flex items-center">
                <div className="flex-shrink-0 p-3 rounded-lg bg-green-100">
                  <CheckCircleIcon className="h-6 w-6 text-green-600" />
                </div>
                <div className="ml-4">
                  <h3 className="text-sm font-medium text-gray-600">Read Alerts</h3>
                  <p className="text-2xl font-bold text-gray-900">{alerts.length - unreadCount}</p>
                </div>
              </div>
            </Card>
          </div>
          
          {/* Filters */}
          <div className="mt-8">
            <div className="flex space-x-4">
              <button
                onClick={() => setFilter('all')}
                className={`px-4 py-2 text-sm font-medium rounded-md ${
                  filter === 'all'
                    ? 'bg-primary text-white'
                    : 'text-gray-700 hover:bg-gray-100'
                }`}
              >
                All Alerts
              </button>
              <button
                onClick={() => setFilter('unread')}
                className={`px-4 py-2 text-sm font-medium rounded-md ${
                  filter === 'unread'
                    ? 'bg-primary text-white'
                    : 'text-gray-700 hover:bg-gray-100'
                }`}
              >
                Unread ({unreadCount})
              </button>
              <button
                onClick={() => setFilter('read')}
                className={`px-4 py-2 text-sm font-medium rounded-md ${
                  filter === 'read'
                    ? 'bg-primary text-white'
                    : 'text-gray-700 hover:bg-gray-100'
                }`}
              >
                Read
              </button>
            </div>
          </div>
          
          {/* Alerts List */}
          <div className="mt-8 space-y-4">
            {filteredAlerts.length > 0 ? (
              filteredAlerts.map((alert) => (
                <motion.div
                  key={alert.id}
                  initial={{ opacity: 0, y: 20 }}
                  animate={{ opacity: 1, y: 0 }}
                  transition={{ duration: 0.3 }}
                >
                  <Card className={`${alert.read ? 'bg-white' : 'bg-blue-50 border-l-4 border-l-primary'}`}>
                    <div className="flex items-start">
                      <div className={`flex-shrink-0 p-2 rounded-lg ${
                        alert.type === 'warning' ? 'bg-yellow-100 text-yellow-600' : 
                        alert.type === 'success' ? 'bg-green-100 text-green-600' : 
                        'bg-blue-100 text-blue-600'
                      }`}>
                        {alert.countdown ? (
                          <ClockIcon className="h-6 w-6" />
                        ) : alert.title.includes('Eco-Friendly') ? (
                          <SparklesIcon className="h-6 w-6" />
                        ) : alert.type === 'warning' ? (
                          <ExclamationTriangleIcon className="h-6 w-6" />
                        ) : alert.type === 'success' ? (
                          <CheckCircleIcon className="h-6 w-6" />
                        ) : (
                          <InformationCircleIcon className="h-6 w-6" />
                        )}
                      </div>
                      <div className="ml-4 flex-1">
                        <div className="flex items-center justify-between">
                          <h3 className={`text-lg font-medium ${
                            alert.read ? 'text-gray-900' : 'text-gray-900 font-bold'
                          }`}>
                            {alert.title}
                            {alert.countdown && (
                              <span className="ml-2 inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-red-100 text-red-800">
                                {alert.countdown} days
                              </span>
                            )}
                          </h3>
                          <button
                            onClick={() => deleteAlert(alert.id)}
                            className="text-gray-400 hover:text-gray-500"
                          >
                            <XMarkIcon className="h-5 w-5" />
                          </button>
                        </div>
                        <p className="mt-1 text-gray-600">{alert.message}</p>
                        <div className="mt-3 flex items-center justify-between">
                          <p className="text-sm text-gray-500">
                            {new Date(alert.date).toLocaleDateString()}
                          </p>
                          {!alert.read && (
                            <Button 
                              variant="ghost" 
                              size="sm" 
                              onClick={() => markAsRead(alert.id)}
                            >
                              Mark as Read
                            </Button>
                          )}
                        </div>
                      </div>
                    </div>
                  </Card>
                </motion.div>
              ))
            ) : (
              <Card className="text-center py-12">
                <BellIcon className="mx-auto h-12 w-12 text-gray-400" />
                <h3 className="mt-2 text-sm font-medium text-gray-900">No alerts</h3>
                <p className="mt-1 text-sm text-gray-500">
                  {filter === 'unread' 
                    ? "You're all caught up! No unread alerts." 
                    : "You don't have any alerts at the moment."}
                </p>
              </Card>
            )}
          </div>
        </motion.div>
      </div>
    </div>
  );
};

export default AlertsPage;