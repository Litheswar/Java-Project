import React from 'react';
import { motion } from 'framer-motion';

const Card = ({ 
  children, 
  className = '', 
  hoverEffect = false, 
  onClick,
  ...props 
}) => {
  const baseClasses = "glass-card rounded-xl p-6 transition-all duration-300";
  
  const hoverClasses = hoverEffect 
    ? "hover:shadow-lg hover:-translate-y-1 cursor-pointer" 
    : "";
  
  const classes = `${baseClasses} ${hoverClasses} ${className}`;
  
  if (onClick) {
    return (
      <motion.div
        whileHover={hoverEffect ? { y: -5 } : {}}
        whileTap={{ scale: 0.98 }}
        className={classes}
        onClick={onClick}
        {...props}
      >
        {children}
      </motion.div>
    );
  }
  
  return (
    <div className={classes} {...props}>
      {children}
    </div>
  );
};

export default Card;