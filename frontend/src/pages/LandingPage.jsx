import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { motion } from 'framer-motion';
import { 
  ArrowRightIcon, 
  GlobeAltIcon, 
  ChartBarIcon, 
  LightBulbIcon,
  SparklesIcon
} from '@heroicons/react/24/outline';
import Button from '../components/Button';
import Card from '../components/Card';
import TravelQuote from '../components/TravelQuote';
import WorldMap from '../components/WorldMap';
import DiscoveryQuiz from '../components/DiscoveryQuiz';

const LandingPage = () => {
  const [isVisible, setIsVisible] = useState(false);
  
  useEffect(() => {
    setIsVisible(true);
  }, []);
  
  const features = [
    {
      icon: <GlobeAltIcon className="h-8 w-8 text-primary" />,
      title: "Smart Planning",
      description: "AI-powered travel planning that optimizes routes, costs, and time for your perfect trip."
    },
    {
      icon: <ChartBarIcon className="h-8 w-8 text-secondary" />,
      title: "Expense Tracking",
      description: "Real-time expense tracking with category breakdowns and budget management."
    },
    {
      icon: <SparklesIcon className="h-8 w-8 text-green-500" />,
      title: "Eco-Friendly",
      description: "Carbon footprint calculator and sustainable travel recommendations."
    },
    {
      icon: <LightBulbIcon className="h-8 w-8 text-accent" />,
      title: "Smart Suggestions",
      description: "Personalized recommendations based on your interests and travel history."
    }
  ];
  
  const testimonials = [
    {
      name: "Sarah Johnson",
      role: "Frequent Traveler",
      content: "Seamless-GO transformed how I plan my trips. The eco-score feature helps me make sustainable choices without compromising on experience.",
      avatar: "SJ"
    },
    {
      name: "Michael Chen",
      role: "Business Consultant",
      content: "The expense tracking and route optimization saved me 30% on my last business trip. Highly recommended!",
      avatar: "MC"
    },
    {
      name: "Emma Rodriguez",
      role: "Digital Nomad",
      content: "As a digital nomad, I need tools that work anywhere. Seamless-GO's offline capabilities and intuitive design are game-changers.",
      avatar: "ER"
    }
  ];
  
  // Mock travel quotes
  const travelQuotes = [
    "The world is a book and those who do not travel read only one page. - Saint Augustine",
    "Travel is fatal to prejudice, bigotry, and narrow-mindedness. - Mark Twain",
    "Adventure is worthwhile in itself. - Amelia Earhart",
    "To travel is to discover that everyone is wrong about other countries. - Aldous Huxley"
  ];
  
  // Mock destinations for the world map
  const destinations = [
    {
      id: 1,
      name: "Paris, France",
      country: "France",
      description: "The City of Light offers world-class art, cuisine, and culture.",
      sustainabilityScore: 85,
      coordinates: [48.8566, 2.3522]
    },
    {
      id: 2,
      name: "Kyoto, Japan",
      country: "Japan",
      description: "Ancient temples, traditional gardens, and modern innovation.",
      sustainabilityScore: 92,
      coordinates: [35.0116, 135.7681]
    },
    {
      id: 3,
      name: "Costa Rica",
      country: "Costa Rica",
      description: "Biodiverse rainforests, volcanoes, and commitment to sustainability.",
      sustainabilityScore: 95,
      coordinates: [9.7489, -83.7534]
    },
    {
      id: 4,
      name: "Reykjavik, Iceland",
      country: "Iceland",
      description: "Geothermal energy, Northern Lights, and unique landscapes.",
      sustainabilityScore: 88,
      coordinates: [64.1466, -21.9426]
    }
  ];
  
  const handleDestinationSelect = (destination) => {
    console.log('Selected destination:', destination);
  };
  
  const handleRecommendation = (destination) => {
    console.log('Recommended destination:', destination);
  };
  
  return (
    <div className="min-h-screen">
      {/* Hero Section */}
      <div className="relative overflow-hidden">
        <div className="max-w-7xl mx-auto">
          <div className="relative z-10 pb-8 bg-background sm:pb-16 md:pb-20 lg:max-w-2xl lg:w-full lg:pb-28 xl:pb-32">
            <motion.div 
              initial={{ opacity: 0, y: 20 }}
              animate={isVisible ? { opacity: 1, y: 0 } : {}}
              transition={{ duration: 0.5 }}
              className="pt-10 px-4 sm:px-6 lg:px-8"
            >
              <main className="mt-10 mx-auto max-w-7xl px-4 sm:mt-12 sm:px-6 md:mt-16 lg:mt-20 lg:px-8 xl:mt-28">
                <div className="sm:text-center lg:text-left">
                  <motion.h1 
                    initial={{ opacity: 0, y: 20 }}
                    animate={isVisible ? { opacity: 1, y: 0 } : {}}
                    transition={{ duration: 0.5, delay: 0.1 }}
                    className="text-4xl tracking-tight font-extrabold text-gray-900 sm:text-5xl md:text-6xl"
                  >
                    <span className="block">Plan Smarter,</span>
                    <span className="block gradient-text">Travel Greener</span>
                  </motion.h1>
                  <motion.p 
                    initial={{ opacity: 0, y: 20 }}
                    animate={isVisible ? { opacity: 1, y: 0 } : {}}
                    transition={{ duration: 0.5, delay: 0.2 }}
                    className="mt-3 text-base text-gray-600 sm:mt-5 sm:text-lg sm:max-w-xl sm:mx-auto md:mt-5 md:text-xl lg:mx-0"
                  >
                    Seamless-GO is your all-in-one travel companion that helps you plan sustainable trips, 
                    track expenses, and discover eco-friendly destinations around the world.
                  </motion.p>
                  <motion.div 
                    initial={{ opacity: 0, y: 20 }}
                    animate={isVisible ? { opacity: 1, y: 0 } : {}}
                    transition={{ duration: 0.5, delay: 0.3 }}
                    className="mt-5 sm:mt-8 sm:flex sm:justify-center lg:justify-start"
                  >
                    <div className="rounded-md shadow">
                      <Link to="/planner">
                        <Button 
                          variant="primary" 
                          size="lg" 
                          icon={<ArrowRightIcon className="h-5 w-5" />}
                          iconPosition="right"
                        >
                          Start Planning
                        </Button>
                      </Link>
                    </div>
                    <div className="mt-3 sm:mt-0 sm:ml-3">
                      <Link to="/login">
                        <Button variant="secondary" size="lg">
                          Sign In
                        </Button>
                      </Link>
                    </div>
                  </motion.div>
                </div>
              </main>
            </motion.div>
          </div>
        </div>
        <div className="lg:absolute lg:inset-y-0 lg:right-0 lg:w-1/2">
          <motion.div
            initial={{ opacity: 0, scale: 0.9 }}
            animate={isVisible ? { opacity: 1, scale: 1 } : {}}
            transition={{ duration: 0.5, delay: 0.4 }}
            className="h-56 w-full bg-gradient-to-r from-primary to-secondary sm:h-72 md:h-96 lg:w-full lg:h-full rounded-l-3xl"
          />
        </div>
      </div>
      
      {/* AI Travel Quote Section */}
      <div className="py-12 bg-background">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="lg:text-center mb-12">
            <motion.h2 
              initial={{ opacity: 0, y: 20 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true }}
              transition={{ duration: 0.5 }}
              className="text-base text-primary font-semibold tracking-wide uppercase"
            >
              Daily Inspiration
            </motion.h2>
            <motion.p 
              initial={{ opacity: 0, y: 20 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true }}
              transition={{ duration: 0.5, delay: 0.1 }}
              className="mt-2 text-3xl leading-8 font-extrabold tracking-tight text-gray-900 sm:text-4xl"
            >
              AI-Powered Travel Wisdom
            </motion.p>
          </div>
          
          <div className="max-w-3xl mx-auto">
            <TravelQuote quotes={travelQuotes} />
          </div>
        </div>
      </div>
      
      {/* Interactive World Map */}
      <div className="py-12 bg-white">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="lg:text-center mb-12">
            <motion.h2 
              initial={{ opacity: 0, y: 20 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true }}
              transition={{ duration: 0.5 }}
              className="text-base text-primary font-semibold tracking-wide uppercase"
            >
              Discover Destinations
            </motion.h2>
            <motion.p 
              initial={{ opacity: 0, y: 20 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true }}
              transition={{ duration: 0.5, delay: 0.1 }}
              className="mt-2 text-3xl leading-8 font-extrabold tracking-tight text-gray-900 sm:text-4xl"
            >
              Explore Sustainable Travel Options
            </motion.p>
            <motion.p 
              initial={{ opacity: 0, y: 20 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true }}
              transition={{ duration: 0.5, delay: 0.2 }}
              className="mt-4 max-w-2xl text-xl text-gray-500 lg:mx-auto"
            >
              Click on markers to discover eco-friendly destinations around the world.
            </motion.p>
          </div>
          
          <WorldMap destinations={destinations} onDestinationSelect={handleDestinationSelect} />
        </div>
      </div>
      
      {/* Discovery Quiz */}
      <div className="py-12 bg-background">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="lg:text-center mb-12">
            <motion.h2 
              initial={{ opacity: 0, y: 20 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true }}
              transition={{ duration: 0.5 }}
              className="text-base text-primary font-semibold tracking-wide uppercase"
            >
              Personalized Recommendations
            </motion.h2>
            <motion.p 
              initial={{ opacity: 0, y: 20 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true }}
              transition={{ duration: 0.5, delay: 0.1 }}
              className="mt-2 text-3xl leading-8 font-extrabold tracking-tight text-gray-900 sm:text-4xl"
            >
              Find Your Perfect Trip
            </motion.p>
            <motion.p 
              initial={{ opacity: 0, y: 20 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true }}
              transition={{ duration: 0.5, delay: 0.2 }}
              className="mt-4 max-w-2xl text-xl text-gray-500 lg:mx-auto"
            >
              Answer a few questions to get personalized destination recommendations.
            </motion.p>
          </div>
          
          <DiscoveryQuiz destinations={destinations} onRecommendation={handleRecommendation} />
        </div>
      </div>
      
      {/* Features Section */}
      <div className="py-12 bg-white">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="lg:text-center">
            <motion.h2 
              initial={{ opacity: 0, y: 20 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true }}
              transition={{ duration: 0.5 }}
              className="text-base text-primary font-semibold tracking-wide uppercase"
            >
              Features
            </motion.h2>
            <motion.p 
              initial={{ opacity: 0, y: 20 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true }}
              transition={{ duration: 0.5, delay: 0.1 }}
              className="mt-2 text-3xl leading-8 font-extrabold tracking-tight text-gray-900 sm:text-4xl"
            >
              Everything you need for perfect trips
            </motion.p>
            <motion.p 
              initial={{ opacity: 0, y: 20 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true }}
              transition={{ duration: 0.5, delay: 0.2 }}
              className="mt-4 max-w-2xl text-xl text-gray-500 lg:mx-auto"
            >
              Our platform combines smart technology with sustainable travel practices to give you the best travel experience.
            </motion.p>
          </div>
          
          <div className="mt-10">
            <div className="grid grid-cols-1 gap-8 sm:grid-cols-2 lg:grid-cols-4">
              {features.map((feature, index) => (
                <motion.div
                  key={index}
                  initial={{ opacity: 0, y: 20 }}
                  whileInView={{ opacity: 1, y: 0 }}
                  viewport={{ once: true }}
                  transition={{ duration: 0.5, delay: index * 0.1 }}
                >
                  <Card hoverEffect={true} className="h-full">
                    <div className="flex justify-center">
                      {feature.icon}
                    </div>
                    <h3 className="mt-4 text-lg font-medium text-gray-900 text-center">
                      {feature.title}
                    </h3>
                    <p className="mt-2 text-base text-gray-600 text-center">
                      {feature.description}
                    </p>
                  </Card>
                </motion.div>
              ))}
            </div>
          </div>
        </div>
      </div>
      
      {/* Testimonials Section */}
      <div className="py-12 bg-background">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="lg:text-center">
            <motion.h2 
              initial={{ opacity: 0, y: 20 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true }}
              transition={{ duration: 0.5 }}
              className="text-base text-primary font-semibold tracking-wide uppercase"
            >
              Testimonials
            </motion.h2>
            <motion.p 
              initial={{ opacity: 0, y: 20 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true }}
              transition={{ duration: 0.5, delay: 0.1 }}
              className="mt-2 text-3xl leading-8 font-extrabold tracking-tight text-gray-900 sm:text-4xl"
            >
              What travelers say
            </motion.p>
          </div>
          
          <div className="mt-10">
            <div className="grid grid-cols-1 gap-8 sm:grid-cols-2 lg:grid-cols-3">
              {testimonials.map((testimonial, index) => (
                <motion.div
                  key={index}
                  initial={{ opacity: 0, y: 20 }}
                  whileInView={{ opacity: 1, y: 0 }}
                  viewport={{ once: true }}
                  transition={{ duration: 0.5, delay: index * 0.1 }}
                >
                  <Card className="h-full">
                    <div className="flex items-center">
                      <div className="flex-shrink-0">
                        <div className="flex items-center justify-center h-12 w-12 rounded-full bg-primary text-white font-bold">
                          {testimonial.avatar}
                        </div>
                      </div>
                      <div className="ml-4">
                        <h4 className="text-lg font-bold text-gray-900">{testimonial.name}</h4>
                        <p className="text-sm text-gray-600">{testimonial.role}</p>
                      </div>
                    </div>
                    <p className="mt-4 text-gray-600 italic">
                      "{testimonial.content}"
                    </p>
                  </Card>
                </motion.div>
              ))}
            </div>
          </div>
        </div>
      </div>
      
      {/* CTA Section */}
      <div className="bg-gradient-to-r from-primary to-secondary">
        <div className="max-w-7xl mx-auto py-12 px-4 sm:px-6 lg:py-16 lg:px-8 lg:flex lg:items-center lg:justify-between">
          <motion.h2 
            initial={{ opacity: 0, x: -20 }}
            whileInView={{ opacity: 1, x: 0 }}
            viewport={{ once: true }}
            transition={{ duration: 0.5 }}
            className="text-3xl font-extrabold tracking-tight text-white sm:text-4xl"
          >
            <span className="block">Ready to start your journey?</span>
            <span className="block text-blue-100">Plan smarter with Seamless-GO today.</span>
          </motion.h2>
          <motion.div 
            initial={{ opacity: 0, x: 20 }}
            whileInView={{ opacity: 1, x: 0 }}
            viewport={{ once: true }}
            transition={{ duration: 0.5, delay: 0.1 }}
            className="mt-8 flex lg:mt-0 lg:flex-shrink-0"
          >
            <div className="inline-flex rounded-md shadow">
              <Link to="/planner">
                <Button 
                  variant="secondary" 
                  size="lg" 
                  icon={<ArrowRightIcon className="h-5 w-5" />}
                  iconPosition="right"
                  className="text-primary"
                >
                  Get started
                </Button>
              </Link>
            </div>
          </motion.div>
        </div>
      </div>
    </div>
  );
};

export default LandingPage;