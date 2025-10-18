import React from 'react';
import { motion } from 'framer-motion';
import { 
  CalendarIcon,
  CurrencyDollarIcon,
  SparklesIcon,
  FireIcon,
  TrophyIcon,
  ChartBarIcon
} from '@heroicons/react/24/outline';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';

const ProductivityDashboard = ({ monthlyData = [], streak = 0, ecoScore = 0, travelPoints = 0 }) => {
  const CustomTooltip = ({ active, payload, label }) => {
    if (active && payload && payload.length) {
      return (
        <div className="bg-white p-4 rounded-lg shadow-lg border border-gray-200">
          <p className="font-semibold text-gray-800">{label}</p>
          <p className="text-sm">Trips: {payload[0].value}</p>
          <p className="text-sm">CO₂ Saved: {payload[1].value}kg</p>
        </div>
      );
    }
    return null;
  };

  // Default monthly data if not provided
  const defaultMonthlyData = [
    { month: 'Jan', trips: 2, co2Saved: 30 },
    { month: 'Feb', trips: 3, co2Saved: 45 },
    { month: 'Mar', trips: 1, co2Saved: 20 },
    { month: 'Apr', trips: 4, co2Saved: 60 },
    { month: 'May', trips: 2, co2Saved: 35 },
    { month: 'Jun', trips: 3, co2Saved: 50 }
  ];

  const data = monthlyData && monthlyData.length > 0 ? monthlyData : defaultMonthlyData;

  // Stats cards
  const stats = [
    {
      name: 'Monthly Trips',
      value: data.reduce((sum, month) => sum + (month.trips || 0), 0),
      icon: <CalendarIcon className="h-6 w-6 text-blue-600" />,
      change: '+12% from last month',
      color: 'bg-blue-100'
    },
    {
      name: 'Eco Score',
      value: ecoScore,
      icon: <SparklesIcon className="h-6 w-6 text-green-600" />,
      change: '+5 points',
      color: 'bg-green-100'
    },
    {
      name: 'Travel Points',
      value: travelPoints,
      icon: <TrophyIcon className="h-6 w-6 text-yellow-600" />,
      change: '+150 this month',
      color: 'bg-yellow-100'
    },
    {
      name: 'Current Streak',
      value: streak,
      icon: <FireIcon className="h-6 w-6 text-red-600" />,
      change: 'Keep it up!',
      color: 'bg-red-100'
    }
  ];

  return (
    <div className="space-y-6">
      {/* Stats Overview - Responsive grid */}
      <div className="grid grid-cols-2 sm:grid-cols-4 gap-4">
        {stats.map((stat, index) => (
          <motion.div
            key={index}
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.3, delay: index * 0.1 }}
            className="bg-gradient-to-br from-blue-50 to-green-50 rounded-xl p-4 text-center shadow-sm hover:shadow-lg transition-all"
            whileHover={{ scale: 1.05 }}
          >
            <div className="flex justify-center">
              <div className={`p-3 rounded-lg ${stat.color}`}>
                {stat.icon}
              </div>
            </div>
            <p className="text-xs text-gray-500 mt-2">{stat.name}</p>
            <h3 className="text-xl font-bold text-gray-800">{stat.value}</h3>
            <p className="text-xs text-gray-500 mt-1">{stat.change}</p>
          </motion.div>
        ))}
      </div>
      
      {/* Monthly Productivity Chart - Larger and more readable */}
      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.3, delay: 0.4 }}
        className="bg-white rounded-xl p-6 w-full h-[340px]"
      >
        <div className="flex items-center justify-between mb-4">
          <h3 className="text-lg font-semibold text-gray-900">Monthly Travel Productivity</h3>
          <ChartBarIcon className="h-5 w-5 text-gray-500" />
        </div>
        
        <div className="h-[260px]">
          <ResponsiveContainer width="100%" height="100%">
            <LineChart
              data={data}
              margin={{ top: 5, right: 30, left: 20, bottom: 5 }}
            >
              <CartesianGrid strokeDasharray="3 3" strokeOpacity={0.3} />
              <XAxis dataKey="month" />
              <YAxis yAxisId="left" />
              <YAxis yAxisId="right" orientation="right" />
              <Tooltip content={<CustomTooltip />} />
              <Line 
                yAxisId="left"
                type="monotone" 
                dataKey="trips" 
                stroke="#3b82f6" 
                activeDot={{ r: 8 }} 
                strokeWidth={3}
                name="Trips"
                dot={{ strokeWidth: 2, r: 4 }}
              />
              <Line 
                yAxisId="right"
                type="monotone" 
                dataKey="co2Saved" 
                stroke="#10b981" 
                strokeWidth={3}
                name="CO₂ Saved (kg)"
                dot={{ strokeWidth: 2, r: 4 }}
              />
            </LineChart>
          </ResponsiveContainer>
        </div>
        
        <div className="mt-4 flex justify-center space-x-6">
          <div className="flex items-center">
            <div className="w-3 h-3 bg-blue-500 rounded-full mr-2"></div>
            <span className="text-xs text-gray-600">Trips per Month</span>
          </div>
          <div className="flex items-center">
            <div className="w-3 h-3 bg-green-500 rounded-full mr-2"></div>
            <span className="text-xs text-gray-600">CO₂ Saved (kg)</span>
          </div>
        </div>
      </motion.div>
    </div>
  );
};

export default ProductivityDashboard;