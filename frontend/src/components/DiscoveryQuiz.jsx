import React, { useState } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import { ChevronRightIcon, SparklesIcon } from '@heroicons/react/24/outline';
import Button from './Button';
import Card from './Card';

const DiscoveryQuiz = ({ destinations, onRecommendation }) => {
  const [currentQuestion, setCurrentQuestion] = useState(0);
  const [answers, setAnswers] = useState([]);
  const [showResult, setShowResult] = useState(false);
  const [recommendedDestination, setRecommendedDestination] = useState(null);

  const questions = [
    {
      id: 1,
      text: "What type of experience are you looking for?",
      options: [
        { text: "Cultural immersion", value: "culture" },
        { text: "Natural wonders", value: "nature" },
        { text: "Urban adventures", value: "city" },
        { text: "Relaxation & wellness", value: "relax" }
      ]
    },
    {
      id: 2,
      text: "What's your preferred travel pace?",
      options: [
        { text: "Slow and steady", value: "slow" },
        { text: "Moderate exploration", value: "moderate" },
        { text: "Fast-paced adventure", value: "fast" },
        { text: "Mix of both", value: "mix" }
      ]
    },
    {
      id: 3,
      text: "What's most important to you?",
      options: [
        { text: "Eco-friendly options", value: "eco" },
        { text: "Local experiences", value: "local" },
        { text: "Luxury amenities", value: "luxury" },
        { text: "Budget-friendly", value: "budget" }
      ]
    }
  ];

  const handleAnswer = (answer) => {
    const newAnswers = [...answers, answer];
    setAnswers(newAnswers);

    if (currentQuestion < questions.length - 1) {
      setCurrentQuestion(currentQuestion + 1);
    } else {
      // Calculate recommendation based on answers
      calculateRecommendation(newAnswers);
      setShowResult(true);
    }
  };

  const calculateRecommendation = (answers) => {
    // Simple recommendation algorithm based on answers
    // In a real app, this would be more sophisticated
    const ecoScore = answers.filter(a => a === 'eco').length;
    const natureScore = answers.filter(a => a === 'nature').length;
    const cultureScore = answers.filter(a => a === 'culture').length;
    
    // Find destination with highest sustainability score if eco is important
    let recommended = destinations[0];
    if (ecoScore > 1) {
      recommended = destinations.reduce((prev, current) => 
        (prev.sustainabilityScore > current.sustainabilityScore) ? prev : current
      );
    } else if (natureScore > 1) {
      // Find nature-focused destination
      recommended = destinations.find(d => d.country === "Costa Rica") || destinations[0];
    } else if (cultureScore > 1) {
      // Find culture-focused destination
      recommended = destinations.find(d => d.country === "Japan") || destinations[0];
    }
    
    setRecommendedDestination(recommended);
    if (onRecommendation) {
      onRecommendation(recommended);
    }
  };

  const resetQuiz = () => {
    setCurrentQuestion(0);
    setAnswers([]);
    setShowResult(false);
    setRecommendedDestination(null);
  };

  return (
    <Card className="p-6">
      <div className="flex items-center mb-6">
        <SparklesIcon className="h-6 w-6 text-purple-500" />
        <h3 className="ml-2 text-xl font-bold text-gray-900">Discover Your Next Adventure</h3>
      </div>
      
      <AnimatePresence mode="wait">
        {showResult ? (
          <motion.div
            key="result"
            initial={{ opacity: 0, x: 20 }}
            animate={{ opacity: 1, x: 0 }}
            exit={{ opacity: 0, x: -20 }}
            transition={{ duration: 0.3 }}
            className="text-center"
          >
            <h4 className="text-lg font-semibold text-gray-900 mb-4">Based on your preferences</h4>
            
            {recommendedDestination && (
              <div className="glass-card rounded-xl p-6 mb-6">
                <div className="bg-gray-200 border-2 border-dashed rounded-xl w-16 h-16 mx-auto" />
                <h5 className="mt-4 text-xl font-bold text-gray-900">{recommendedDestination.name}</h5>
                <p className="mt-2 text-gray-600">{recommendedDestination.country}</p>
                <p className="mt-3 text-sm text-gray-500">{recommendedDestination.description}</p>
                <div className="mt-4 flex justify-center">
                  <span className="inline-flex items-center px-3 py-1 rounded-full text-sm font-medium bg-green-100 text-green-800">
                    🌱 {recommendedDestination.sustainabilityScore}% sustainable
                  </span>
                </div>
              </div>
            )}
            
            <div className="flex flex-col sm:flex-row gap-3 justify-center">
              <Button variant="primary" onClick={resetQuiz}>
                Retake Quiz
              </Button>
              <Button variant="secondary" onClick={() => onRecommendation && onRecommendation(recommendedDestination)}>
                Plan This Trip
                <ChevronRightIcon className="ml-2 h-5 w-5" />
              </Button>
            </div>
          </motion.div>
        ) : (
          <motion.div
            key="question"
            initial={{ opacity: 0, x: 20 }}
            animate={{ opacity: 1, x: 0 }}
            exit={{ opacity: 0, x: -20 }}
            transition={{ duration: 0.3 }}
          >
            <div className="mb-2">
              <h4 className="text-lg font-semibold text-gray-900">
                Question {currentQuestion + 1} of {questions.length}
              </h4>
              <div className="mt-1 w-full bg-gray-200 rounded-full h-2">
                <div 
                  className="bg-primary h-2 rounded-full" 
                  style={{ width: `${((currentQuestion + 1) / questions.length) * 100}%` }}
                ></div>
              </div>
            </div>
            
            <h5 className="text-xl font-medium text-gray-900 mt-6 mb-6">
              {questions[currentQuestion].text}
            </h5>
            
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
              {questions[currentQuestion].options.map((option, index) => (
                <motion.div
                  key={index}
                  whileHover={{ scale: 1.02 }}
                  whileTap={{ scale: 0.98 }}
                >
                  <Card 
                    hoverEffect={true}
                    className="cursor-pointer p-4 text-center"
                    onClick={() => handleAnswer(option.value)}
                  >
                    <p className="font-medium text-gray-900">{option.text}</p>
                  </Card>
                </motion.div>
              ))}
            </div>
          </motion.div>
        )}
      </AnimatePresence>
    </Card>
  );
};

export default DiscoveryQuiz;