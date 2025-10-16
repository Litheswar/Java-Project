import React from 'react';
import { motion } from 'framer-motion';
import { 
  DocumentArrowDownIcon,
  ArrowDownTrayIcon,
  DocumentTextIcon
} from '@heroicons/react/24/outline';

const ExportReport = ({ tripName, onExport }) => {
  const handleExport = (format) => {
    if (onExport) {
      onExport(format, tripName);
    }
    
    // Show success message
    alert(`Exporting ${tripName} as ${format.toUpperCase()}...`);
  };

  return (
    <motion.div
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      className="glass-card rounded-xl p-6"
    >
      <div className="flex items-center mb-4">
        <DocumentArrowDownIcon className="h-5 w-5 text-gray-500" />
        <h3 className="ml-2 text-lg font-semibold text-gray-900">Export Trip Report</h3>
      </div>
      
      <p className="text-sm text-gray-600 mb-6">
        Download a detailed report of your trip expenses, itinerary, and sustainability impact.
      </p>
      
      <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
        <motion.button
          whileHover={{ scale: 1.02 }}
          whileTap={{ scale: 0.98 }}
          onClick={() => handleExport('pdf')}
          className="flex items-center justify-center p-4 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors"
        >
          <div className="flex flex-col items-center">
            <div className="bg-red-100 p-3 rounded-lg">
              <DocumentTextIcon className="h-6 w-6 text-red-600" />
            </div>
            <span className="mt-2 font-medium text-gray-900">PDF Report</span>
            <span className="text-xs text-gray-500">Detailed document</span>
          </div>
        </motion.button>
        
        <motion.button
          whileHover={{ scale: 1.02 }}
          whileTap={{ scale: 0.98 }}
          onClick={() => handleExport('csv')}
          className="flex items-center justify-center p-4 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors"
        >
          <div className="flex flex-col items-center">
            <div className="bg-green-100 p-3 rounded-lg">
              <ArrowDownTrayIcon className="h-6 w-6 text-green-600" />
            </div>
            <span className="mt-2 font-medium text-gray-900">CSV Data</span>
            <span className="text-xs text-gray-500">Spreadsheet format</span>
          </div>
        </motion.button>
      </div>
    </motion.div>
  );
};

export default ExportReport;