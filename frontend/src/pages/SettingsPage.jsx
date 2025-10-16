import React, { useState } from 'react';
import { motion } from 'framer-motion';
import { 
  UserIcon, 
  BellIcon, 
  ShieldCheckIcon,
  GlobeAltIcon,
  CreditCardIcon,
  MoonIcon,
  SunIcon
} from '@heroicons/react/24/outline';
import Card from '../components/Card';
import Button from '../components/Button';

const SettingsPage = () => {
  const [activeSection, setActiveSection] = useState('account');
  
  const sections = [
    { id: 'account', name: 'Account', icon: UserIcon },
    { id: 'notifications', name: 'Notifications', icon: BellIcon },
    { id: 'privacy', name: 'Privacy', icon: ShieldCheckIcon },
    { id: 'language', name: 'Language', icon: GlobeAltIcon },
    { id: 'billing', name: 'Billing', icon: CreditCardIcon },
    { id: 'appearance', name: 'Appearance', icon: SunIcon }
  ];
  
  const renderAccountSettings = () => (
    <div className="space-y-6">
      <Card>
        <h3 className="text-lg font-medium text-gray-900 mb-4">Profile Information</h3>
        <div className="grid grid-cols-1 gap-6 sm:grid-cols-2">
          <div>
            <label htmlFor="firstName" className="block text-sm font-medium text-gray-700">
              First Name
            </label>
            <input
              type="text"
              name="firstName"
              id="firstName"
              className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-primary focus:border-primary sm:text-sm rounded-md"
              defaultValue="Alex"
            />
          </div>
          
          <div>
            <label htmlFor="lastName" className="block text-sm font-medium text-gray-700">
              Last Name
            </label>
            <input
              type="text"
              name="lastName"
              id="lastName"
              className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-primary focus:border-primary sm:text-sm rounded-md"
              defaultValue="Johnson"
            />
          </div>
          
          <div className="sm:col-span-2">
            <label htmlFor="email" className="block text-sm font-medium text-gray-700">
              Email Address
            </label>
            <input
              type="email"
              name="email"
              id="email"
              className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-primary focus:border-primary sm:text-sm rounded-md"
              defaultValue="alex.johnson@example.com"
            />
          </div>
          
          <div className="sm:col-span-2">
            <label htmlFor="bio" className="block text-sm font-medium text-gray-700">
              Bio
            </label>
            <textarea
              id="bio"
              name="bio"
              rows={3}
              className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-primary focus:border-primary sm:text-sm rounded-md"
              defaultValue="Frequent traveler passionate about sustainable tourism."
            />
          </div>
        </div>
        <div className="mt-6">
          <Button variant="primary">Save Changes</Button>
        </div>
      </Card>
      
      <Card>
        <h3 className="text-lg font-medium text-gray-900 mb-4">Password</h3>
        <div className="grid grid-cols-1 gap-6 sm:grid-cols-2">
          <div>
            <label htmlFor="currentPassword" className="block text-sm font-medium text-gray-700">
              Current Password
            </label>
            <input
              type="password"
              name="currentPassword"
              id="currentPassword"
              className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-primary focus:border-primary sm:text-sm rounded-md"
            />
          </div>
          
          <div className="sm:col-span-2"></div>
          
          <div>
            <label htmlFor="newPassword" className="block text-sm font-medium text-gray-700">
              New Password
            </label>
            <input
              type="password"
              name="newPassword"
              id="newPassword"
              className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-primary focus:border-primary sm:text-sm rounded-md"
            />
          </div>
          
          <div>
            <label htmlFor="confirmPassword" className="block text-sm font-medium text-gray-700">
              Confirm New Password
            </label>
            <input
              type="password"
              name="confirmPassword"
              id="confirmPassword"
              className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-primary focus:border-primary sm:text-sm rounded-md"
            />
          </div>
        </div>
        <div className="mt-6">
          <Button variant="primary">Update Password</Button>
        </div>
      </Card>
    </div>
  );
  
  const renderNotificationSettings = () => (
    <Card>
      <h3 className="text-lg font-medium text-gray-900 mb-6">Notification Preferences</h3>
      <div className="space-y-6">
        <div className="flex items-center justify-between">
          <div>
            <h4 className="text-base font-medium text-gray-900">Email Notifications</h4>
            <p className="text-sm text-gray-500">Receive email updates about your trips and account activity.</p>
          </div>
          <Button variant="secondary" size="sm">Enabled</Button>
        </div>
        
        <div className="flex items-center justify-between">
          <div>
            <h4 className="text-base font-medium text-gray-900">Push Notifications</h4>
            <p className="text-sm text-gray-500">Get instant alerts on your mobile device.</p>
          </div>
          <Button variant="secondary" size="sm">Enabled</Button>
        </div>
        
        <div className="flex items-center justify-between">
          <div>
            <h4 className="text-base font-medium text-gray-900">Budget Alerts</h4>
            <p className="text-sm text-gray-500">Notify when you're approaching your budget limits.</p>
          </div>
          <Button variant="secondary" size="sm">Enabled</Button>
        </div>
        
        <div className="flex items-center justify-between">
          <div>
            <h4 className="text-base font-medium text-gray-900">Eco Tips</h4>
            <p className="text-sm text-gray-500">Weekly suggestions for sustainable travel.</p>
          </div>
          <Button variant="secondary" size="sm">Enabled</Button>
        </div>
        
        <div className="flex items-center justify-between">
          <div>
            <h4 className="text-base font-medium text-gray-900">Travel Reminders</h4>
            <p className="text-sm text-gray-500">Important reminders before your trips.</p>
          </div>
          <Button variant="secondary" size="sm">Enabled</Button>
        </div>
      </div>
    </Card>
  );
  
  const renderPrivacySettings = () => (
    <Card>
      <h3 className="text-lg font-medium text-gray-900 mb-6">Privacy Settings</h3>
      <div className="space-y-6">
        <div className="flex items-center justify-between">
          <div>
            <h4 className="text-base font-medium text-gray-900">Profile Visibility</h4>
            <p className="text-sm text-gray-500">Make your profile visible to other travelers.</p>
          </div>
          <Button variant="secondary" size="sm">Public</Button>
        </div>
        
        <div className="flex items-center justify-between">
          <div>
            <h4 className="text-base font-medium text-gray-900">Trip Sharing</h4>
            <p className="text-sm text-gray-500">Allow friends to view your trip details.</p>
          </div>
          <Button variant="secondary" size="sm">Enabled</Button>
        </div>
        
        <div className="flex items-center justify-between">
          <div>
            <h4 className="text-base font-medium text-gray-900">Location Tracking</h4>
            <p className="text-sm text-gray-500">Share your location during trips for safety.</p>
          </div>
          <Button variant="secondary" size="sm">Enabled</Button>
        </div>
        
        <div className="flex items-center justify-between">
          <div>
            <h4 className="text-base font-medium text-gray-900">Data Collection</h4>
            <p className="text-sm text-gray-500">Help improve our service by sharing usage data.</p>
          </div>
          <Button variant="secondary" size="sm">Enabled</Button>
        </div>
        
        <div>
          <h4 className="text-base font-medium text-gray-900 mb-4">Download Your Data</h4>
          <p className="text-sm text-gray-500 mb-4">Get a copy of your personal data stored by Seamless-GO.</p>
          <Button variant="secondary">Download Data</Button>
        </div>
      </div>
    </Card>
  );
  
  const renderLanguageSettings = () => (
    <Card>
      <h3 className="text-lg font-medium text-gray-900 mb-6">Language & Region</h3>
      <div className="space-y-6">
        <div>
          <label htmlFor="language" className="block text-sm font-medium text-gray-700">
            Language
          </label>
          <select
            id="language"
            name="language"
            className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-primary focus:border-primary sm:text-sm rounded-md"
            defaultValue="en"
          >
            <option value="en">English</option>
            <option value="es">Spanish</option>
            <option value="fr">French</option>
            <option value="de">German</option>
            <option value="it">Italian</option>
            <option value="pt">Portuguese</option>
          </select>
        </div>
        
        <div>
          <label htmlFor="currency" className="block text-sm font-medium text-gray-700">
            Currency
          </label>
          <select
            id="currency"
            name="currency"
            className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-primary focus:border-primary sm:text-sm rounded-md"
            defaultValue="USD"
          >
            <option value="USD">US Dollar (USD)</option>
            <option value="EUR">Euro (EUR)</option>
            <option value="GBP">British Pound (GBP)</option>
            <option value="JPY">Japanese Yen (JPY)</option>
            <option value="CAD">Canadian Dollar (CAD)</option>
            <option value="AUD">Australian Dollar (AUD)</option>
          </select>
        </div>
        
        <div>
          <label htmlFor="timezone" className="block text-sm font-medium text-gray-700">
            Timezone
          </label>
          <select
            id="timezone"
            name="timezone"
            className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-primary focus:border-primary sm:text-sm rounded-md"
            defaultValue="America/New_York"
          >
            <option value="America/New_York">Eastern Time (ET)</option>
            <option value="America/Chicago">Central Time (CT)</option>
            <option value="America/Denver">Mountain Time (MT)</option>
            <option value="America/Los_Angeles">Pacific Time (PT)</option>
            <option value="Europe/London">London (GMT)</option>
            <option value="Europe/Paris">Paris (CET)</option>
            <option value="Asia/Tokyo">Tokyo (JST)</option>
          </select>
        </div>
        
        <div className="pt-4 border-t border-gray-200">
          <Button variant="primary">Save Preferences</Button>
        </div>
      </div>
    </Card>
  );
  
  const renderBillingSettings = () => (
    <div className="space-y-6">
      <Card>
        <h3 className="text-lg font-medium text-gray-900 mb-4">Subscription Plan</h3>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          <div className="border border-gray-200 rounded-lg p-6">
            <h4 className="text-lg font-medium text-gray-900">Free</h4>
            <p className="mt-2 text-3xl font-bold text-gray-900">$0<span className="text-lg font-medium text-gray-500">/month</span></p>
            <ul className="mt-4 space-y-3">
              <li className="flex items-center">
                <svg className="h-5 w-5 text-green-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
                </svg>
                <span className="ml-2 text-sm text-gray-600">Basic trip planning</span>
              </li>
              <li className="flex items-center">
                <svg className="h-5 w-5 text-green-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
                </svg>
                <span className="ml-2 text-sm text-gray-600">Up to 3 trips</span>
              </li>
              <li className="flex items-center">
                <svg className="h-5 w-5 text-green-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
                </svg>
                <span className="ml-2 text-sm text-gray-600">Basic expense tracking</span>
              </li>
            </ul>
            <div className="mt-6">
              <Button variant="secondary" className="w-full" disabled>Current Plan</Button>
            </div>
          </div>
          
          <div className="border-2 border-primary rounded-lg p-6 relative">
            <div className="absolute top-0 right-0 bg-primary text-white text-xs font-bold px-3 py-1 rounded-bl-lg rounded-tr-lg">
              Popular
            </div>
            <h4 className="text-lg font-medium text-gray-900">Pro</h4>
            <p className="mt-2 text-3xl font-bold text-gray-900">$9<span className="text-lg font-medium text-gray-500">/month</span></p>
            <ul className="mt-4 space-y-3">
              <li className="flex items-center">
                <svg className="h-5 w-5 text-green-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
                </svg>
                <span className="ml-2 text-sm text-gray-600">Unlimited trips</span>
              </li>
              <li className="flex items-center">
                <svg className="h-5 w-5 text-green-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
                </svg>
                <span className="ml-2 text-sm text-gray-600">Advanced expense tracking</span>
              </li>
              <li className="flex items-center">
                <svg className="h-5 w-5 text-green-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
                </svg>
                <span className="ml-2 text-sm text-gray-600">Eco-score analytics</span>
              </li>
              <li className="flex items-center">
                <svg className="h-5 w-5 text-green-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
                </svg>
                <span className="ml-2 text-sm text-gray-600">Route optimization</span>
              </li>
            </ul>
            <div className="mt-6">
              <Button variant="primary" className="w-full">Upgrade to Pro</Button>
            </div>
          </div>
          
          <div className="border border-gray-200 rounded-lg p-6">
            <h4 className="text-lg font-medium text-gray-900">Premium</h4>
            <p className="mt-2 text-3xl font-bold text-gray-900">$19<span className="text-lg font-medium text-gray-500">/month</span></p>
            <ul className="mt-4 space-y-3">
              <li className="flex items-center">
                <svg className="h-5 w-5 text-green-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
                </svg>
                <span className="ml-2 text-sm text-gray-600">All Pro features</span>
              </li>
              <li className="flex items-center">
                <svg className="h-5 w-5 text-green-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
                </svg>
                <span className="ml-2 text-sm text-gray-600">Personal travel assistant</span>
              </li>
              <li className="flex items-center">
                <svg className="h-5 w-5 text-green-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
                </svg>
                <span className="ml-2 text-sm text-gray-600">Priority customer support</span>
              </li>
              <li className="flex items-center">
                <svg className="h-5 w-5 text-green-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
                </svg>
                <span className="ml-2 text-sm text-gray-600">Exclusive travel deals</span>
              </li>
            </ul>
            <div className="mt-6">
              <Button variant="secondary" className="w-full">Upgrade to Premium</Button>
            </div>
          </div>
        </div>
      </Card>
      
      <Card>
        <h3 className="text-lg font-medium text-gray-900 mb-4">Payment Methods</h3>
        <div className="space-y-4">
          <div className="flex items-center justify-between p-4 border border-gray-200 rounded-lg">
            <div className="flex items-center">
              <div className="flex-shrink-0">
                <svg className="h-8 w-8 text-gray-400" fill="currentColor" viewBox="0 0 24 24">
                  <path d="M16 6H4c-1.11 0-2 .89-2 2v8c0 1.11.89 2 2 2h12c1.11 0 2-.89 2-2V8c0-1.11-.89-2-2-2zm0 10H4V8h12v8zm-9-5.5c0 .83-.67 1.5-1.5 1.5S4 12.33 4 11.5 4.67 10 5.5 10s1.5.67 1.5 1.5zM9 13H7v-2h2v2zm4 0h-2v-2h2v2zm4 0h-2v-2h2v2z"/>
                </svg>
              </div>
              <div className="ml-4">
                <h4 className="text-sm font-medium text-gray-900">Visa ending in 4242</h4>
                <p className="text-sm text-gray-500">Expires 12/2025</p>
              </div>
            </div>
            <div className="flex space-x-2">
              <Button variant="ghost" size="sm">Edit</Button>
              <Button variant="danger" size="sm">Remove</Button>
            </div>
          </div>
          
          <div className="flex items-center justify-between p-4 border border-gray-200 rounded-lg">
            <div className="flex items-center">
              <div className="flex-shrink-0">
                <svg className="h-8 w-8 text-gray-400" fill="currentColor" viewBox="0 0 24 24">
                  <path d="M20 4H4c-1.11 0-1.99.89-1.99 2L2 18c0 1.11.89 2 2 2h16c1.11 0 2-.89 2-2V6c0-1.11-.89-2-2-2zm0 14H4v-6h16v6zm0-10H4V6h16v2z"/>
                </svg>
              </div>
              <div className="ml-4">
                <h4 className="text-sm font-medium text-gray-900">Mastercard ending in 1234</h4>
                <p className="text-sm text-gray-500">Expires 08/2024</p>
              </div>
            </div>
            <div className="flex space-x-2">
              <Button variant="ghost" size="sm">Edit</Button>
              <Button variant="danger" size="sm">Remove</Button>
            </div>
          </div>
        </div>
        <div className="mt-6">
          <Button variant="secondary">Add Payment Method</Button>
        </div>
      </Card>
    </div>
  );
  
  const renderAppearanceSettings = () => (
    <Card>
      <h3 className="text-lg font-medium text-gray-900 mb-6">Appearance</h3>
      <div className="space-y-6">
        <div>
          <h4 className="text-base font-medium text-gray-900 mb-4">Theme</h4>
          <div className="grid grid-cols-1 sm:grid-cols-3 gap-4">
            <div 
              className="border-2 border-primary rounded-lg p-4 cursor-pointer"
              onClick={() => document.documentElement.classList.remove('dark')}
            >
              <div className="flex items-center">
                <div className="flex-shrink-0">
                  <SunIcon className="h-6 w-6 text-yellow-500" />
                </div>
                <div className="ml-3">
                  <h5 className="text-sm font-medium text-gray-900">Light</h5>
                </div>
              </div>
              <div className="mt-3 bg-gray-200 border-2 border-dashed rounded-xl w-16 h-16" />
            </div>
            
            <div 
              className="border border-gray-200 rounded-lg p-4 cursor-pointer"
              onClick={() => document.documentElement.classList.add('dark')}
            >
              <div className="flex items-center">
                <div className="flex-shrink-0">
                  <MoonIcon className="h-6 w-6 text-gray-700" />
                </div>
                <div className="ml-3">
                  <h5 className="text-sm font-medium text-gray-900">Dark</h5>
                </div>
              </div>
              <div className="mt-3 bg-gray-700 border-2 border-dashed rounded-xl w-16 h-16" />
            </div>
            
            <div className="border border-gray-200 rounded-lg p-4 opacity-50 cursor-not-allowed">
              <div className="flex items-center">
                <div className="flex-shrink-0">
                  <svg className="h-6 w-6 text-purple-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M7 21a4 4 0 01-4-4V5a2 2 0 012-2h4a2 2 0 012 2v12a4 4 0 01-4 4zm0 0h12a2 2 0 002-2v-4a2 2 0 00-2-2h-2.343M11 7.343l1.657-1.657a2 2 0 012.828 0l2.829 2.829a2 2 0 010 2.828l-8.486 8.485M7 17h.01" />
                  </svg>
                </div>
                <div className="ml-3">
                  <h5 className="text-sm font-medium text-gray-900">System</h5>
                </div>
              </div>
              <div className="mt-3 flex">
                <div className="bg-gray-200 border-2 border-dashed rounded-l-xl w-8 h-16" />
                <div className="bg-gray-700 border-2 border-dashed rounded-r-xl w-8 h-16" />
              </div>
            </div>
          </div>
        </div>
        
        <div>
          <h4 className="text-base font-medium text-gray-900 mb-4">Color Theme</h4>
          <div className="flex flex-wrap gap-3">
            <div className="w-8 h-8 rounded-full bg-blue-500 cursor-pointer"></div>
            <div className="w-8 h-8 rounded-full bg-green-500 cursor-pointer"></div>
            <div className="w-8 h-8 rounded-full bg-purple-500 cursor-pointer"></div>
            <div className="w-8 h-8 rounded-full bg-yellow-500 cursor-pointer"></div>
            <div className="w-8 h-8 rounded-full bg-red-500 cursor-pointer"></div>
            <div className="w-8 h-8 rounded-full bg-indigo-500 cursor-pointer"></div>
          </div>
        </div>
      </div>
    </Card>
  );
  
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
              <h1 className="text-2xl font-bold text-gray-900">Settings</h1>
              <p className="mt-1 text-sm text-gray-600">
                Manage your account settings and preferences
              </p>
            </div>
          </div>
          
          <div className="mt-8 flex flex-col md:flex-row gap-8">
            {/* Sidebar */}
            <div className="md:w-64">
              <nav className="space-y-1">
                {sections.map((section) => {
                  const Icon = section.icon;
                  return (
                    <button
                      key={section.id}
                      onClick={() => setActiveSection(section.id)}
                      className={`flex items-center w-full px-3 py-2 text-sm font-medium rounded-md ${
                        activeSection === section.id
                          ? 'bg-primary text-white'
                          : 'text-gray-700 hover:bg-gray-100'
                      }`}
                    >
                      <Icon className="flex-shrink-0 h-5 w-5 mr-3" />
                      {section.name}
                    </button>
                  );
                })}
              </nav>
            </div>
            
            {/* Main Content */}
            <div className="flex-1">
              {activeSection === 'account' && renderAccountSettings()}
              {activeSection === 'notifications' && renderNotificationSettings()}
              {activeSection === 'privacy' && renderPrivacySettings()}
              {activeSection === 'language' && renderLanguageSettings()}
              {activeSection === 'billing' && renderBillingSettings()}
              {activeSection === 'appearance' && renderAppearanceSettings()}
            </div>
          </div>
        </motion.div>
      </div>
    </div>
  );
};

export default SettingsPage;