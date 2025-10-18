import React, { useState, useEffect } from 'react';
import { motion } from 'framer-motion';
import { 
  CurrencyDollarIcon, 
  PlusIcon, 
  FunnelIcon,
  ArrowsUpDownIcon,
  MapPinIcon,
  CreditCardIcon,
  ArrowDownTrayIcon,
  DocumentTextIcon
} from '@heroicons/react/24/outline';
import Card from '../components/Card';
import Chart from '../components/Chart';
import Button from '../components/Button';
import Modal from '../components/Modal';
import Map from '../components/Map';
import CurrencyConverter from '../components/CurrencyConverter';
import ExportReport from '../components/ExportReport';
import ExpenseCategorization from '../components/ExpenseCategorization';
import BudgetDeviationChart from '../components/BudgetDeviationChart';
import { expenseService } from '../services/expenseService';
import { tripService } from '../services/tripService';
import { useAppContext } from '../context/AppContext';

const ExpensesPage = () => {
  const { user } = useAppContext();
  const [expenses, setExpenses] = useState([]);
  const [trips, setTrips] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedTrip, setSelectedTrip] = useState('');
  const [sortBy, setSortBy] = useState('date');
  const [filterCategory, setFilterCategory] = useState('all');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  
  // Budget categories for deviation chart (in a real app, this would come from the API)
  const budgetCategories = [
    { name: 'Accommodation', budgeted: 800, actual: 850 },
    { name: 'Transportation', budgeted: 500, actual: 450 },
    { name: 'Food', budgeted: 400, actual: 380 },
    { name: 'Activities', budgeted: 300, actual: 320 },
    { name: 'Shopping', budgeted: 200, actual: 150 }
  ];
  
  // Fetch trips and expenses when component mounts
  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);
        // Fetch trips for the user
        const tripsData = await tripService.getTripsByUserId(user.id);
        setTrips(tripsData);
        
        // If there are trips, fetch expenses for the first trip
        if (tripsData.length > 0) {
          const firstTrip = tripsData[0];
          setSelectedTrip(firstTrip.id);
          const expensesData = await expenseService.getExpensesByTripId(firstTrip.id);
          setExpenses(expensesData);
        }
        
        setError(null);
      } catch (err) {
        console.error('Error fetching expenses data:', err);
        setError('Failed to load expenses data. Please try again later.');
      } finally {
        setLoading(false);
      }
    };
    
    fetchData();
  }, [user.id]);
  
  // Fetch expenses when selected trip changes
  useEffect(() => {
    const fetchExpenses = async () => {
      if (selectedTrip) {
        try {
          setLoading(true);
          const expensesData = await expenseService.getExpensesByTripId(selectedTrip);
          setExpenses(expensesData);
          setError(null);
        } catch (err) {
          console.error('Error fetching expenses:', err);
          setError('Failed to load expenses. Please try again later.');
        } finally {
          setLoading(false);
        }
      }
    };
    
    fetchExpenses();
  }, [selectedTrip]);
  
  const categories = ['all', 'Accommodation', 'Transportation', 'Food', 'Activities', 'Shopping'];
  
  const totalSpent = expenses.reduce((sum, expense) => sum + expense.amount, 0);
  const budget = trips.find(t => t.id === selectedTrip)?.budget || 0;
  const remaining = budget - totalSpent;
  
  const handleAddExpense = async (newExpense) => {
    try {
      // Add tripId to the expense data
      const expenseToAdd = {
        ...newExpense,
        tripId: selectedTrip,
        amount: parseFloat(newExpense.amount)
      };
      
      // Create expense via API
      const createdExpense = await expenseService.createExpense(expenseToAdd);
      
      // Add to local state
      setExpenses(prev => [...prev, { ...expenseToAdd, id: createdExpense }]);
      setIsModalOpen(false);
    } catch (error) {
      console.error('Error adding expense:', error);
      alert('Failed to add expense. Please try again.');
    }
  };
  
  const handleDeleteExpense = async (expenseId) => {
    try {
      // Delete expense via API
      await expenseService.deleteExpense(expenseId);
      
      // Remove from local state
      setExpenses(prev => prev.filter(expense => expense.id !== expenseId));
    } catch (error) {
      console.error('Error deleting expense:', error);
      alert('Failed to delete expense. Please try again.');
    }
  };
  
  const sortedExpenses = [...expenses].sort((a, b) => {
    if (sortBy === 'date') {
      return new Date(b.date) - new Date(a.date);
    } else if (sortBy === 'amount') {
      return b.amount - a.amount;
    }
    return 0;
  });
  
  const filteredExpenses = filterCategory === 'all' 
    ? sortedExpenses 
    : sortedExpenses.filter(expense => expense.category === filterCategory);
  
  if (loading && expenses.length === 0) {
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
              <h1 className="text-2xl font-bold text-gray-900">Expenses & Routes</h1>
              <p className="mt-1 text-sm text-gray-600">
                Track your spending and visualize your travel routes
              </p>
            </div>
            <div className="mt-4 flex md:mt-0 md:ml-4">
              <Button 
                variant="primary" 
                icon={<PlusIcon className="h-5 w-5" />}
                onClick={() => setIsModalOpen(true)}
                disabled={!selectedTrip}
              >
                Add Expense
              </Button>
            </div>
          </div>
          
          {/* Trip Selector */}
          <div className="mt-6">
            <label htmlFor="tripSelector" className="block text-sm font-medium text-gray-700 mb-2">
              Select Trip
            </label>
            <select
              id="tripSelector"
              value={selectedTrip}
              onChange={(e) => setSelectedTrip(parseInt(e.target.value))}
              className="block w-full max-w-md pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-primary focus:border-primary sm:text-sm rounded-md"
            >
              <option value="">Select a trip</option>
              {trips.map(trip => (
                <option key={trip.id} value={trip.id}>
                  {trip.name} - {trip.destination}
                </option>
              ))}
            </select>
          </div>
          
          {selectedTrip ? (
            <>
              {/* Budget Overview */}
              <div className="mt-8 grid grid-cols-1 md:grid-cols-3 gap-6">
                <Card className="md:col-span-1">
                  <div className="flex items-center">
                    <div className="flex-shrink-0 p-3 rounded-lg bg-blue-100">
                      <CurrencyDollarIcon className="h-6 w-6 text-blue-600" />
                    </div>
                    <div className="ml-4">
                      <h3 className="text-sm font-medium text-gray-600">Total Spent</h3>
                      <p className="text-2xl font-bold text-gray-900">${totalSpent.toFixed(2)}</p>
                    </div>
                  </div>
                </Card>
                
                <Card className="md:col-span-1">
                  <div className="flex items-center">
                    <div className="flex-shrink-0 p-3 rounded-lg bg-green-100">
                      <CurrencyDollarIcon className="h-6 w-6 text-green-600" />
                    </div>
                    <div className="ml-4">
                      <h3 className="text-sm font-medium text-gray-600">Budget Remaining</h3>
                      <p className="text-2xl font-bold text-gray-900">${remaining.toFixed(2)}</p>
                    </div>
                  </div>
                </Card>
                
                <Card className="md:col-span-1">
                  <div className="flex items-center">
                    <div className="flex-shrink-0 p-3 rounded-lg bg-purple-100">
                      <CreditCardIcon className="h-6 w-6 text-purple-600" />
                    </div>
                    <div className="ml-4">
                      <h3 className="text-sm font-medium text-gray-600">Budget Used</h3>
                      <p className="text-2xl font-bold text-gray-900">
                        {budget > 0 ? Math.round((totalSpent / budget) * 100) : 0}%
                      </p>
                    </div>
                  </div>
                </Card>
              </div>
              
              {/* Charts and Map */}
              <div className="mt-8 grid grid-cols-1 lg:grid-cols-3 gap-8">
                {/* Spending Chart */}
                <div className="lg:col-span-2">
                  <Chart 
                    title="Spending by Category"
                    data={[]} // This would be populated with real data from expense breakdown
                    type="pie"
                    dataKey="name"
                    height={350}
                  />
                </div>
                
                {/* Route Map */}
                <div>
                  <h2 className="text-lg font-medium text-gray-900 mb-4">Travel Route</h2>
                  <Map 
                    center={[0, 0]}
                    markers={[]}
                    className="h-96"
                  />
                </div>
              </div>
              
              {/* Currency Converter and Export */}
              <div className="mt-8 grid grid-cols-1 lg:grid-cols-2 gap-8">
                <CurrencyConverter />
                <ExportReport tripName={trips.find(t => t.id === selectedTrip)?.name || "Selected Trip"} onExport={(format) => console.log(`Exporting as ${format}`)} />
              </div>
              
              {/* Expense Categorization and Budget Deviation */}
              <div className="mt-8 grid grid-cols-1 lg:grid-cols-2 gap-8">
                <ExpenseCategorization expenses={expenses} />
                <BudgetDeviationChart 
                  budget={budget}
                  spent={totalSpent}
                  categories={budgetCategories}
                />
              </div>
              
              {/* Filters and Expenses Table */}
              <div className="mt-8">
                <Card>
                  <div className="flex flex-col md:flex-row md:items-center md:justify-between mb-6">
                    <h2 className="text-lg font-medium text-gray-900">Expense Details</h2>
                    
                    <div className="mt-4 md:mt-0 flex space-x-4">
                      <div className="flex items-center">
                        <FunnelIcon className="h-5 w-5 text-gray-400 mr-2" />
                        <select
                          value={filterCategory}
                          onChange={(e) => setFilterCategory(e.target.value)}
                          className="block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-primary focus:border-primary sm:text-sm rounded-md"
                        >
                          {categories.map(category => (
                            <option key={category} value={category}>
                              {category === 'all' ? 'All Categories' : category}
                            </option>
                          ))}
                        </select>
                      </div>
                      
                      <div className="flex items-center">
                        <ArrowsUpDownIcon className="h-5 w-5 text-gray-400 mr-2" />
                        <select
                          value={sortBy}
                          onChange={(e) => setSortBy(e.target.value)}
                          className="block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-primary focus:border-primary sm:text-sm rounded-md"
                        >
                          <option value="date">Sort by Date</option>
                          <option value="amount">Sort by Amount</option>
                        </select>
                      </div>
                    </div>
                  </div>
                  
                  <div className="overflow-x-auto">
                    <table className="min-w-full divide-y divide-gray-200">
                      <thead className="bg-gray-50">
                        <tr>
                          <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                            Date
                          </th>
                          <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                            Description
                          </th>
                          <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                            Category
                          </th>
                          <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                            Amount
                          </th>
                          <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                            Actions
                          </th>
                        </tr>
                      </thead>
                      <tbody className="bg-white divide-y divide-gray-200">
                        {filteredExpenses.map((expense) => (
                          <motion.tr 
                            key={expense.id}
                            initial={{ opacity: 0 }}
                            animate={{ opacity: 1 }}
                            transition={{ duration: 0.2 }}
                            className="hover:bg-gray-50"
                          >
                            <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                              {new Date(expense.date).toLocaleDateString()}
                            </td>
                            <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                              {expense.description}
                            </td>
                            <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                              <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-blue-100 text-blue-800">
                                {expense.category}
                              </span>
                            </td>
                            <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                              ${expense.amount.toFixed(2)}
                            </td>
                            <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                              <button
                                onClick={() => handleDeleteExpense(expense.id)}
                                className="text-red-600 hover:text-red-900"
                              >
                                Delete
                              </button>
                            </td>
                          </motion.tr>
                        ))}
                      </tbody>
                    </table>
                  </div>
                  
                  {filteredExpenses.length === 0 && (
                    <div className="text-center py-12">
                      <CurrencyDollarIcon className="mx-auto h-12 w-12 text-gray-400" />
                      <h3 className="mt-2 text-sm font-medium text-gray-900">No expenses</h3>
                      <p className="mt-1 text-sm text-gray-500">
                        Get started by adding a new expense.
                      </p>
                      <div className="mt-6">
                        <Button 
                          variant="primary" 
                          icon={<PlusIcon className="h-5 w-5" />}
                          onClick={() => setIsModalOpen(true)}
                        >
                          Add Expense
                        </Button>
                      </div>
                    </div>
                  )}
                </Card>
              </div>
            </>
          ) : (
            <div className="mt-8 text-center py-12">
              <MapPinIcon className="mx-auto h-12 w-12 text-gray-400" />
              <h3 className="mt-2 text-sm font-medium text-gray-900">No trip selected</h3>
              <p className="mt-1 text-sm text-gray-500">
                Please select a trip to view and manage expenses.
              </p>
            </div>
          )}
        </motion.div>
      </div>
      
      {/* Add Expense Modal */}
      <AddExpenseModal 
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        onSubmit={handleAddExpense}
        trips={trips}
        selectedTrip={selectedTrip}
      />
    </div>
  );
};

const AddExpenseModal = ({ isOpen, onClose, onSubmit, trips, selectedTrip }) => {
  const [formData, setFormData] = useState({
    tripId: selectedTrip,
    category: 'Accommodation',
    amount: '',
    date: new Date().toISOString().split('T')[0],
    description: ''
  });
  
  const categories = ['Accommodation', 'Transportation', 'Food', 'Activities', 'Shopping'];
  
  // Update form data when selected trip changes
  useEffect(() => {
    setFormData(prev => ({
      ...prev,
      tripId: selectedTrip
    }));
  }, [selectedTrip]);
  
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };
  
  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(formData);
    setFormData({
      tripId: selectedTrip,
      category: 'Accommodation',
      amount: '',
      date: new Date().toISOString().split('T')[0],
      description: ''
    });
  };
  
  return (
    <Modal 
      isOpen={isOpen} 
      onClose={onClose} 
      title="Add New Expense"
      size="md"
    >
      <form onSubmit={handleSubmit} className="space-y-6">
        <div>
          <label htmlFor="tripId" className="block text-sm font-medium text-gray-700">
            Trip
          </label>
          <select
            id="tripId"
            name="tripId"
            value={formData.tripId}
            onChange={handleChange}
            required
            className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-primary focus:border-primary sm:text-sm rounded-md"
            disabled
          >
            <option value="">Select a trip</option>
            {trips.map(trip => (
              <option key={trip.id} value={trip.id}>
                {trip.name} - {trip.destination}
              </option>
            ))}
          </select>
        </div>
        
        <div>
          <label htmlFor="category" className="block text-sm font-medium text-gray-700">
            Category
          </label>
          <select
            id="category"
            name="category"
            value={formData.category}
            onChange={handleChange}
            className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-primary focus:border-primary sm:text-sm rounded-md"
          >
            {categories.map(category => (
              <option key={category} value={category}>
                {category}
              </option>
            ))}
          </select>
        </div>
        
        <div>
          <label htmlFor="amount" className="block text-sm font-medium text-gray-700">
            Amount ($)
          </label>
          <div className="mt-1 relative rounded-md shadow-sm">
            <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
              <CurrencyDollarIcon className="h-5 w-5 text-gray-400" />
            </div>
            <input
              type="number"
              name="amount"
              id="amount"
              value={formData.amount}
              onChange={handleChange}
              required
              min="0.01"
              step="0.01"
              className="block w-full pl-10 pr-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-primary focus:border-primary sm:text-sm"
            />
          </div>
        </div>
        
        <div>
          <label htmlFor="date" className="block text-sm font-medium text-gray-700">
            Date
          </label>
          <input
            type="date"
            name="date"
            id="date"
            value={formData.date}
            onChange={handleChange}
            required
            className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-primary focus:border-primary sm:text-sm rounded-md"
          />
        </div>
        
        <div>
          <label htmlFor="description" className="block text-sm font-medium text-gray-700">
            Description
          </label>
          <textarea
            id="description"
            name="description"
            rows={3}
            value={formData.description}
            onChange={handleChange}
            required
            className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-primary focus:border-primary sm:text-sm rounded-md"
          />
        </div>
        
        <div className="flex justify-end space-x-3">
          <Button variant="secondary" onClick={onClose}>
            Cancel
          </Button>
          <Button variant="primary" type="submit">
            Add Expense
          </Button>
        </div>
      </form>
    </Modal>
  );
};

export default ExpensesPage;