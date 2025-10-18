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
import { alertService } from '../services/alertService';
import { useAppContext } from '../context/AppContext';

const AlertsPage = () => {
  const { user } = useAppContext();
  const [alerts, setAlerts] = useState([]);
  const [filter, setFilter] = useState('all');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  
  // Fetch alerts when component mounts
  useEffect(() => {
    const fetchAlerts = async () => {
      try {
        setLoading(true);
        const alertsData = await alertService.getAlertsByUserId(user.id);
        setAlerts(alertsData);
        setError(null);
      } catch (err) {
        console.error('Error fetching alerts:', err);
        setError('Failed to load alerts. Please try again later.');
      } finally {
        setLoading(false);
      }
    };
    
    fetchAlerts();
  }, [user.id]);
  
  const markAsRead = async (id) => {
    try {
      // Update alert via API
      await alertService.updateAlert({ id, read: true });
      
      // Update local state
      setAlerts(prev => prev.map(alert => 
        alert.id === id ? { ...alert, read: true } : alert
      ));
    } catch (error) {
      console.error('Error marking alert as read:', error);
      alert('Failed to mark alert as read. Please try again.');
    }
  };
  
  const markAllAsRead = async () => {
    try {
      // Update all alerts via API
      const unreadAlerts = alerts.filter(alert => !alert.read);
      await Promise.all(unreadAlerts.map(alert => 
        alertService.updateAlert({ id: alert.id, read: true })
      ));
      
      // Update local state
      setAlerts(prev => prev.map(alert => ({ ...alert, read: true })));
    } catch (error) {
      console.error('Error marking all alerts as read:', error);
      alert('Failed to mark all alerts as read. Please try again.');
    }
  };
  
  const deleteAlert = async (id) => {
    try {
      // Delete alert via API
      await alertService.deleteAlert(id);
      
      // Update local state
      setAlerts(prev => prev.filter(alert => alert.id !== id));
    } catch (error) {
      console.error('Error deleting alert:', error);
      alert('Failed to delete alert. Please try again.');
    }
  };
  
  const filteredAlerts = filter === 'all' 
    ? alerts 
    : filter === 'unread' 
      ? alerts.filter(alert => !alert.read)
      : alerts.filter(alert => alert.read);
  
  const unreadCount = alerts.filter(alert => !alert.read).length;
  
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
                        {alert.type === 'warning' ? (
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