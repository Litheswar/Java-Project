import React from 'react';
import { Link } from 'react-router-dom';
import { 
  ArrowUpCircleIcon,
  GlobeAltIcon,
  ChatBubbleLeftRightIcon,
  QuestionMarkCircleIcon
} from '@heroicons/react/24/outline';

const Footer = () => {
  const scrollToTop = () => {
    window.scrollTo({
      top: 0,
      behavior: 'smooth'
    });
  };

  return (
    <footer className="bg-white/80 backdrop-blur-sm border-t border-gray-200/50">
      <div className="max-w-7xl mx-auto py-12 px-4 sm:px-6 lg:px-8">
        <div className="grid grid-cols-1 md:grid-cols-4 gap-8">
          <div className="col-span-1 md:col-span-2">
            <div className="flex items-center">
              <div className="bg-gradient-to-r from-primary to-secondary w-8 h-8 rounded-lg"></div>
              <span className="ml-2 text-xl font-bold gradient-text">Seamless-GO</span>
            </div>
            <p className="mt-4 text-gray-600 max-w-md">
              Plan your sustainable travels with Seamless-GO, the smart travel planner that helps you 
              optimize costs and reduce carbon footprint while exploring the world.
            </p>
          </div>
          
          <div>
            <h3 className="text-sm font-semibold text-gray-500 tracking-wider uppercase">
              Navigation
            </h3>
            <ul className="mt-4 space-y-4">
              <li><Link to="/" className="text-base text-gray-600 hover:text-primary">Home</Link></li>
              <li><Link to="/planner" className="text-base text-gray-600 hover:text-primary">Planner</Link></li>
              <li><Link to="/expenses" className="text-base text-gray-600 hover:text-primary">Expenses</Link></li>
              <li><Link to="/profile" className="text-base text-gray-600 hover:text-primary">Profile</Link></li>
            </ul>
          </div>
          
          <div>
            <h3 className="text-sm font-semibold text-gray-500 tracking-wider uppercase">
              Support
            </h3>
            <ul className="mt-4 space-y-4">
              <li className="flex items-center text-base text-gray-600 hover:text-primary cursor-pointer">
                <GlobeAltIcon className="h-5 w-5 mr-2" />
                Sustainability
              </li>
              <li className="flex items-center text-base text-gray-600 hover:text-primary cursor-pointer">
                <ChatBubbleLeftRightIcon className="h-5 w-5 mr-2" />
                Contact Us
              </li>
              <li className="flex items-center text-base text-gray-600 hover:text-primary cursor-pointer">
                <QuestionMarkCircleIcon className="h-5 w-5 mr-2" />
                Help Center
              </li>
            </ul>
          </div>
        </div>
        
        <div className="mt-12 border-t border-gray-200/50 pt-8 flex flex-col md:flex-row justify-between items-center">
          <p className="text-base text-gray-500">
            &copy; 2025 Seamless-GO. All rights reserved.
          </p>
          <div className="mt-4 md:mt-0">
            <button 
              onClick={scrollToTop}
              className="flex items-center text-gray-500 hover:text-primary transition-colors"
            >
              <ArrowUpCircleIcon className="h-5 w-5 mr-1" />
              Back to top
            </button>
          </div>
        </div>
      </div>
    </footer>
  );
};

export default Footer;