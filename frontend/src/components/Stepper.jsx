import React from 'react';
import { motion } from 'framer-motion';
import { CheckCircleIcon } from '@heroicons/react/24/outline';

const Stepper = ({ steps, currentStep, onStepClick }) => {
  return (
    <div className="flex justify-between relative">
      {/* Progress line */}
      <div className="absolute top-4 left-0 right-0 h-0.5 bg-gray-200 -z-10">
        <motion.div 
          className="h-full bg-primary"
          initial={{ width: 0 }}
          animate={{ width: `${(currentStep / (steps.length - 1)) * 100}%` }}
          transition={{ duration: 0.5 }}
        />
      </div>
      
      {steps.map((step, index) => {
        const isCompleted = index < currentStep;
        const isCurrent = index === currentStep;
        const isUpcoming = index > currentStep;
        
        return (
          <div key={index} className="flex flex-col items-center">
            <motion.div
              whileHover={{ scale: 1.1 }}
              whileTap={{ scale: 0.95 }}
              onClick={() => onStepClick && onStepClick(index)}
              className={`
                flex items-center justify-center w-8 h-8 rounded-full border-2 transition-all duration-300
                ${isCompleted ? 'bg-primary border-primary' : ''}
                ${isCurrent ? 'border-primary bg-white' : ''}
                ${isUpcoming ? 'border-gray-300 bg-white' : ''}
                ${onStepClick ? 'cursor-pointer' : ''}
              `}
            >
              {isCompleted ? (
                <CheckCircleIcon className="h-5 w-5 text-white" />
              ) : (
                <span className={`
                  text-sm font-medium
                  ${isCurrent ? 'text-primary' : ''}
                  ${isUpcoming ? 'text-gray-400' : ''}
                `}>
                  {index + 1}
                </span>
              )}
            </motion.div>
            <div className="mt-2 text-center max-w-[100px]">
              <p className={`
                text-xs font-medium
                ${isCurrent ? 'text-primary' : 'text-gray-500'}
              `}>
                {step.title}
              </p>
            </div>
          </div>
        );
      })}
    </div>
  );
};

export default Stepper;