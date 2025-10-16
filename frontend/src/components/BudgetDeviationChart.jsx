import React from 'react';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, Cell } from 'recharts';
import { motion } from 'framer-motion';

const BudgetDeviationChart = ({ budget, spent, categories }) => {
  // Calculate budget vs actual for each category
  const data = categories.map(category => {
    const budgeted = category.budgeted || 0;
    const actual = category.actual || 0;
    const deviation = actual - budgeted;
    const deviationPercentage = budgeted > 0 ? (deviation / budgeted) * 100 : 0;
    
    return {
      name: category.name,
      budgeted,
      actual,
      deviation,
      deviationPercentage
    };
  });

  const CustomTooltip = ({ active, payload, label }) => {
    if (active && payload && payload.length) {
      const data = payload[0].payload;
      return (
        <div className="glass-card p-4 rounded-lg shadow-lg">
          <p className="font-semibold text-gray-800">{label}</p>
          <p className="text-sm">Budgeted: ${data.budgeted.toFixed(2)}</p>
          <p className="text-sm">Actual: ${data.actual.toFixed(2)}</p>
          <p className={`text-sm ${data.deviation >= 0 ? 'text-red-600' : 'text-green-600'}`}>
            Deviation: ${Math.abs(data.deviation).toFixed(2)} ({Math.abs(data.deviationPercentage).toFixed(1)}%)
          </p>
        </div>
      );
    }
    return null;
  };

  return (
    <motion.div
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      className="glass-card rounded-xl p-6 h-80"
    >
      <h3 className="text-lg font-semibold text-gray-900 mb-4">Budget vs Reality</h3>
      
      <div className="h-64">
        <ResponsiveContainer width="100%" height="100%">
          <BarChart
            data={data}
            margin={{ top: 20, right: 30, left: 20, bottom: 5 }}
          >
            <CartesianGrid strokeDasharray="3 3" strokeOpacity={0.3} />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip content={<CustomTooltip />} />
            <Bar dataKey="actual">
              {data.map((entry, index) => {
                // Color based on deviation
                let fill = '#3b82f6'; // Blue for on budget
                if (entry.deviation > 0) {
                  fill = '#ef4444'; // Red for over budget
                } else if (entry.deviation < 0) {
                  fill = '#10b981'; // Green for under budget
                }
                
                return <Cell key={`cell-${index}`} fill={fill} />;
              })}
            </Bar>
          </BarChart>
        </ResponsiveContainer>
      </div>
      
      <div className="mt-4 flex justify-center space-x-6">
        <div className="flex items-center">
          <div className="w-3 h-3 bg-blue-500 rounded-full mr-2"></div>
          <span className="text-xs text-gray-600">On Budget</span>
        </div>
        <div className="flex items-center">
          <div className="w-3 h-3 bg-red-500 rounded-full mr-2"></div>
          <span className="text-xs text-gray-600">Over Budget</span>
        </div>
        <div className="flex items-center">
          <div className="w-3 h-3 bg-green-500 rounded-full mr-2"></div>
          <span className="text-xs text-gray-600">Under Budget</span>
        </div>
      </div>
    </motion.div>
  );
};

export default BudgetDeviationChart;