import React from 'react';
import { motion } from 'framer-motion';
import { 
  TagIcon,
  HomeModernIcon,
  TruckIcon,
  ShoppingBagIcon,
  TicketIcon,
  EllipsisHorizontalIcon
} from '@heroicons/react/24/outline';

const ExpenseCategorization = ({ expenses }) => {
  const categories = {
    'Accommodation': { icon: <HomeModernIcon className="h-5 w-5" />, color: 'bg-blue-100 text-blue-800' },
    'Transportation': { icon: <TruckIcon className="h-5 w-5" />, color: 'bg-green-100 text-green-800' },
    'Food': { icon: <ShoppingBagIcon className="h-5 w-5" />, color: 'bg-yellow-100 text-yellow-800' },
    'Activities': { icon: <TicketIcon className="h-5 w-5" />, color: 'bg-purple-100 text-purple-800' },
    'Shopping': { icon: <ShoppingBagIcon className="h-5 w-5" />, color: 'bg-pink-100 text-pink-800' },
    'Other': { icon: <EllipsisHorizontalIcon className="h-5 w-5" />, color: 'bg-gray-100 text-gray-800' }
  };

  // Calculate totals by category
  const categoryTotals = expenses.reduce((acc, expense) => {
    const category = expense.category || 'Other';
    acc[category] = (acc[category] || 0) + expense.amount;
    return acc;
  }, {});

  // Calculate total expenses
  const totalExpenses = expenses.reduce((sum, expense) => sum + expense.amount, 0);

  return (
    <motion.div
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      className="glass-card rounded-xl p-6"
    >
      <h3 className="text-lg font-semibold text-gray-900 mb-4">Expense Categorization</h3>
      
      <div className="space-y-4">
        {Object.entries(categoryTotals).map(([category, total]) => {
          const categoryData = categories[category] || categories['Other'];
          const percentage = totalExpenses > 0 ? (total / totalExpenses) * 100 : 0;
          
          return (
            <div key={category}>
              <div className="flex items-center justify-between mb-1">
                <div className="flex items-center">
                  <div className={`p-1 rounded ${categoryData.color}`}>
                    {categoryData.icon}
                  </div>
                  <span className="ml-2 text-sm font-medium text-gray-700">{category}</span>
                </div>
                <div className="text-sm font-medium text-gray-900">
                  ${total.toFixed(2)}
                </div>
              </div>
              
              <div className="w-full bg-gray-200 rounded-full h-2">
                <motion.div 
                  className={`h-2 rounded-full ${categoryData.color.replace('text', 'bg').replace('800', '500')}`}
                  initial={{ width: 0 }}
                  animate={{ width: `${percentage}%` }}
                  transition={{ duration: 1, delay: 0.2 }}
                ></motion.div>
              </div>
              
              <div className="text-xs text-gray-500 mt-1">
                {percentage.toFixed(1)}% of total expenses
              </div>
            </div>
          );
        })}
      </div>
      
      <div className="mt-6 pt-4 border-t border-gray-200">
        <div className="flex justify-between">
          <span className="text-sm font-medium text-gray-700">Total Expenses</span>
          <span className="text-sm font-bold text-gray-900">${totalExpenses.toFixed(2)}</span>
        </div>
      </div>
    </motion.div>
  );
};

export default ExpenseCategorization;