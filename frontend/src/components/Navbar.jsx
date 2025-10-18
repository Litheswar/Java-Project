import React, { useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { motion } from 'framer-motion';
import { useAuth } from '../context/AuthContext';
import { 
  HomeIcon, 
  MapIcon, 
  CreditCardIcon, 
  BellIcon, 
  UserIcon, 
  CogIcon,
  Bars3Icon,
  XMarkIcon
} from '@heroicons/react/24/outline';

const Navbar = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { user, signOut } = useAuth();
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  const handleSignOut = () => {
    signOut();
    navigate('/');
  };

  const publicNavItems = [
    { name: 'Home', path: '/', icon: HomeIcon }
  ];

  const privateNavItems = [
    { name: 'Home', path: '/home', icon: HomeIcon },
    { name: 'Planner', path: '/planner', icon: MapIcon },
    { name: 'Expenses', path: '/expenses', icon: CreditCardIcon },
    { name: 'Alerts', path: '/alerts', icon: BellIcon },
    { name: 'Profile', path: '/profile', icon: UserIcon },
    { name: 'Settings', path: '/settings', icon: CogIcon },
  ];

  const navItems = user ? privateNavItems : publicNavItems;

  const isActive = (path) => {
    if (path === '/' && location.pathname === '/') return true;
    if (path === '/') return false;
    return location.pathname.startsWith(path);
  };

  return (
    <nav className="glass-card sticky top-0 z-50 backdrop-blur-md border-b border-white/20">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between h-16">
          <div className="flex items-center">
            <Link to={user ? "/home" : "/"} className="flex-shrink-0 flex items-center">
              <div className="bg-gradient-to-r from-primary to-secondary w-8 h-8 rounded-lg"></div>
              <span className="ml-2 text-xl font-bold gradient-text">Seamless-GO</span>
            </Link>
          </div>
          
          {/* Desktop Navigation */}
          <div className="hidden md:flex md:items-center md:space-x-8">
            {navItems.map((item) => (
              <Link
                key={item.path}
                to={item.path}
                className={`flex items-center px-3 py-2 rounded-md text-sm font-medium transition-all duration-200 ${
                  isActive(item.path)
                    ? 'text-primary bg-blue-50/50'
                    : 'text-gray-600 hover:text-primary hover:bg-blue-50/30'
                }`}
              >
                <item.icon className="h-5 w-5 mr-1" />
                {item.name}
              </Link>
            ))}
            
            {user ? (
              <button
                onClick={handleSignOut}
                className="px-4 py-2 bg-gradient-to-r from-green-400 to-blue-500 text-white rounded-lg text-sm font-medium hover:opacity-90 transition-opacity"
              >
                Logout
              </button>
            ) : (
              <Link
                to="/login"
                className="px-4 py-2 bg-gradient-to-r from-green-400 to-blue-500 text-white rounded-lg text-sm font-medium hover:opacity-90 transition-opacity"
              >
                Sign In
              </Link>
            )}
          </div>
          
          {/* Mobile menu button */}
          <div className="flex md:hidden items-center">
            <button
              onClick={() => setIsMenuOpen(!isMenuOpen)}
              className="inline-flex items-center justify-center p-2 rounded-md text-gray-600 hover:text-primary hover:bg-blue-50/30 focus:outline-none"
            >
              {isMenuOpen ? (
                <XMarkIcon className="block h-6 w-6" />
              ) : (
                <Bars3Icon className="block h-6 w-6" />
              )}
            </button>
          </div>
        </div>
      </div>
      
      {/* Mobile Navigation */}
      {isMenuOpen && (
        <motion.div 
          initial={{ opacity: 0, height: 0 }}
          animate={{ opacity: 1, height: 'auto' }}
          exit={{ opacity: 0, height: 0 }}
          className="md:hidden"
        >
          <div className="px-2 pt-2 pb-3 space-y-1 sm:px-3">
            {navItems.map((item) => (
              <Link
                key={item.path}
                to={item.path}
                onClick={() => setIsMenuOpen(false)}
                className={`flex items-center px-3 py-2 rounded-md text-base font-medium transition-all duration-200 ${
                  isActive(item.path)
                    ? 'text-primary bg-blue-50/50'
                    : 'text-gray-600 hover:text-primary hover:bg-blue-50/30'
                }`}
              >
                <item.icon className="h-5 w-5 mr-2" />
                {item.name}
              </Link>
            ))}
            
            {user ? (
              <button
                onClick={() => {
                  handleSignOut();
                  setIsMenuOpen(false);
                }}
                className="w-full text-left px-3 py-2 rounded-md text-base font-medium text-gray-600 hover:text-primary hover:bg-blue-50/30"
              >
                Logout
              </button>
            ) : (
              <Link
                to="/login"
                onClick={() => setIsMenuOpen(false)}
                className="block px-3 py-2 rounded-md text-base font-medium text-gray-600 hover:text-primary hover:bg-blue-50/30"
              >
                Sign In
              </Link>
            )}
          </div>
        </motion.div>
      )}
    </nav>
  );
};

export default Navbar;