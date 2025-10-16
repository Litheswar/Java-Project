import React, { useState } from 'react';
import { motion } from 'framer-motion';
import { ArrowsRightLeftIcon } from '@heroicons/react/24/outline';

const CurrencyConverter = () => {
  const [amount, setAmount] = useState(100);
  const [fromCurrency, setFromCurrency] = useState('USD');
  const [toCurrency, setToCurrency] = useState('EUR');
  const [convertedAmount, setConvertedAmount] = useState(92.50);

  // Mock exchange rates (in a real app, this would come from an API)
  const exchangeRates = {
    USD: { EUR: 0.925, GBP: 0.79, JPY: 149.23, CAD: 1.36 },
    EUR: { USD: 1.081, GBP: 0.856, JPY: 161.45, CAD: 1.472 },
    GBP: { USD: 1.262, EUR: 1.168, JPY: 188.45, CAD: 1.718 },
    JPY: { USD: 0.0067, EUR: 0.0062, GBP: 0.0053, CAD: 0.0091 },
    CAD: { USD: 0.735, EUR: 0.680, GBP: 0.582, JPY: 109.87 }
  };

  const currencies = [
    { code: 'USD', name: 'US Dollar', symbol: '$' },
    { code: 'EUR', name: 'Euro', symbol: '€' },
    { code: 'GBP', name: 'British Pound', symbol: '£' },
    { code: 'JPY', name: 'Japanese Yen', symbol: '¥' },
    { code: 'CAD', name: 'Canadian Dollar', symbol: 'C$' }
  ];

  const handleConvert = () => {
    if (exchangeRates[fromCurrency] && exchangeRates[fromCurrency][toCurrency]) {
      const rate = exchangeRates[fromCurrency][toCurrency];
      setConvertedAmount((amount * rate).toFixed(2));
    }
  };

  const swapCurrencies = () => {
    setFromCurrency(toCurrency);
    setToCurrency(fromCurrency);
    // Recalculate with swapped currencies
    if (exchangeRates[toCurrency] && exchangeRates[toCurrency][fromCurrency]) {
      const rate = exchangeRates[toCurrency][fromCurrency];
      setConvertedAmount((amount * rate).toFixed(2));
    }
  };

  return (
    <motion.div
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      className="glass-card rounded-xl p-6"
    >
      <h3 className="text-lg font-semibold text-gray-900 mb-4">Currency Converter</h3>
      
      <div className="space-y-4">
        <div>
          <label htmlFor="amount" className="block text-sm font-medium text-gray-700">
            Amount
          </label>
          <div className="mt-1 relative rounded-md shadow-sm">
            <input
              type="number"
              id="amount"
              value={amount}
              onChange={(e) => {
                setAmount(e.target.value);
                handleConvert();
              }}
              className="block w-full pl-3 pr-12 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-primary focus:border-primary"
            />
            <div className="absolute inset-y-0 right-0 flex items-center">
              <select
                value={fromCurrency}
                onChange={(e) => {
                  setFromCurrency(e.target.value);
                  handleConvert();
                }}
                className="h-full py-0 pl-2 pr-7 border-transparent bg-transparent text-gray-500 focus:outline-none focus:ring-primary focus:border-primary rounded-md"
              >
                {currencies.map((currency) => (
                  <option key={currency.code} value={currency.code}>
                    {currency.code}
                  </option>
                ))}
              </select>
            </div>
          </div>
        </div>
        
        <div className="flex justify-center">
          <button
            onClick={swapCurrencies}
            className="p-2 rounded-full bg-gray-100 hover:bg-gray-200 text-gray-600"
          >
            <ArrowsRightLeftIcon className="h-5 w-5" />
          </button>
        </div>
        
        <div>
          <label htmlFor="converted" className="block text-sm font-medium text-gray-700">
            Converted Amount
          </label>
          <div className="mt-1 relative rounded-md shadow-sm">
            <input
              type="text"
              id="converted"
              value={convertedAmount}
              readOnly
              className="block w-full pl-3 pr-12 py-3 border border-gray-300 rounded-lg bg-gray-50"
            />
            <div className="absolute inset-y-0 right-0 flex items-center">
              <select
                value={toCurrency}
                onChange={(e) => {
                  setToCurrency(e.target.value);
                  handleConvert();
                }}
                className="h-full py-0 pl-2 pr-7 border-transparent bg-transparent text-gray-500 focus:outline-none focus:ring-primary focus:border-primary rounded-md"
              >
                {currencies.map((currency) => (
                  <option key={currency.code} value={currency.code}>
                    {currency.code}
                  </option>
                ))}
              </select>
            </div>
          </div>
        </div>
        
        <div className="text-sm text-gray-500">
          <p>Exchange rate: 1 {fromCurrency} = {(exchangeRates[fromCurrency]?.[toCurrency] || 0).toFixed(4)} {toCurrency}</p>
        </div>
      </div>
    </motion.div>
  );
};

export default CurrencyConverter;