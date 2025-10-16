import React, { createContext, useContext, useReducer } from 'react';

// Initial state
const initialState = {
  user: {
    id: 1,
    name: "Alex Johnson",
    email: "alex.johnson@example.com",
    avatar: "AJ",
    ecoScore: 85,
    travelPoints: 1250,
    streak: 7,
    badges: [
      { id: 1, name: "Eco Warrior", icon: "🌱" },
      { id: 2, name: "Explorer", icon: "🗺️" },
      { id: 3, name: "Budget Master", icon: "💰" }
    ],
    travelPersona: "Eco Explorer",
    theme: "nature"
  },
  trips: [
    {
      id: 1,
      name: "European Adventure",
      destination: "Paris, France",
      startDate: "2023-06-15",
      endDate: "2023-06-28",
      budget: 2500,
      spent: 2100,
      co2Saved: 150,
      status: "completed"
    },
    {
      id: 2,
      name: "Mountain Retreat",
      destination: "Swiss Alps",
      startDate: "2023-08-10",
      endDate: "2023-08-20",
      budget: 3000,
      spent: 1800,
      co2Saved: 200,
      status: "upcoming"
    },
    {
      id: 3,
      name: "Beach Vacation",
      destination: "Bali, Indonesia",
      startDate: "2023-12-01",
      endDate: "2023-12-15",
      budget: 4000,
      spent: 0,
      co2Saved: 0,
      status: "planning"
    }
  ],
  alerts: [
    {
      id: 1,
      type: "info",
      title: "Travel Tip",
      message: "Consider taking the train from Paris to Lyon for a more eco-friendly option.",
      date: "2023-06-10",
      read: false
    },
    {
      id: 2,
      type: "warning",
      title: "Budget Alert",
      message: "You've spent 80% of your accommodation budget for the Swiss Alps trip.",
      date: "2023-07-05",
      read: false
    },
    {
      id: 3,
      type: "success",
      title: "Eco Achievement",
      message: "Congratulations! You've saved 200kg of CO2 on your European Adventure trip.",
      date: "2023-06-30",
      read: true
    }
  ],
  destinations: [
    {
      id: 1,
      name: "Paris, France",
      country: "France",
      description: "The City of Light offers world-class art, cuisine, and culture.",
      co2Rating: 4,
      sustainabilityScore: 85,
      image: "/src/assets/paris.jpg",
      coordinates: [48.8566, 2.3522]
    },
    {
      id: 2,
      name: "Kyoto, Japan",
      country: "Japan",
      description: "Ancient temples, traditional gardens, and modern innovation.",
      co2Rating: 5,
      sustainabilityScore: 92,
      image: "/src/assets/kyoto.jpg",
      coordinates: [35.0116, 135.7681]
    },
    {
      id: 3,
      name: "Costa Rica",
      country: "Costa Rica",
      description: "Biodiverse rainforests, volcanoes, and commitment to sustainability.",
      co2Rating: 5,
      sustainabilityScore: 95,
      image: "/src/assets/costarica.jpg",
      coordinates: [9.7489, -83.7534]
    },
    {
      id: 4,
      name: "Reykjavik, Iceland",
      country: "Iceland",
      description: "Geothermal energy, Northern Lights, and unique landscapes.",
      co2Rating: 4,
      sustainabilityScore: 88,
      image: "/src/assets/iceland.jpg",
      coordinates: [64.1466, -21.9426]
    }
  ],
  travelQuotes: [
    "The world is a book and those who do not travel read only one page. - Saint Augustine",
    "Travel is fatal to prejudice, bigotry, and narrow-mindedness. - Mark Twain",
    "Adventure is worthwhile in itself. - Amelia Earhart",
    "To travel is to discover that everyone is wrong about other countries. - Aldous Huxley"
  ]
};

// Action types
const actionTypes = {
  ADD_TRIP: 'ADD_TRIP',
  UPDATE_TRIP: 'UPDATE_TRIP',
  DELETE_TRIP: 'DELETE_TRIP',
  ADD_EXPENSE: 'ADD_EXPENSE',
  MARK_ALERT_READ: 'MARK_ALERT_READ',
  ADD_ALERT: 'ADD_ALERT',
  UPDATE_USER: 'UPDATE_USER',
  UPDATE_ECO_SCORE: 'UPDATE_ECO_SCORE'
};

// Reducer function
const appReducer = (state, action) => {
  switch (action.type) {
    case actionTypes.ADD_TRIP:
      return {
        ...state,
        trips: [...state.trips, action.payload]
      };
    case actionTypes.UPDATE_TRIP:
      return {
        ...state,
        trips: state.trips.map(trip => 
          trip.id === action.payload.id ? action.payload : trip
        )
      };
    case actionTypes.DELETE_TRIP:
      return {
        ...state,
        trips: state.trips.filter(trip => trip.id !== action.payload)
      };
    case actionTypes.ADD_EXPENSE:
      // In a real app, we would update the trip's expenses
      return state;
    case actionTypes.MARK_ALERT_READ:
      return {
        ...state,
        alerts: state.alerts.map(alert => 
          alert.id === action.payload ? { ...alert, read: true } : alert
        )
      };
    case actionTypes.ADD_ALERT:
      return {
        ...state,
        alerts: [...state.alerts, action.payload]
      };
    case actionTypes.UPDATE_USER:
      return {
        ...state,
        user: { ...state.user, ...action.payload }
      };
    case actionTypes.UPDATE_ECO_SCORE:
      return {
        ...state,
        user: { ...state.user, ecoScore: action.payload }
      };
    default:
      return state;
  }
};

// Create context
const AppContext = createContext();

// Provider component
export const AppProvider = ({ children }) => {
  const [state, dispatch] = useReducer(appReducer, initialState);

  // Action creators
  const addTrip = (trip) => {
    dispatch({ type: actionTypes.ADD_TRIP, payload: trip });
  };

  const updateTrip = (trip) => {
    dispatch({ type: actionTypes.UPDATE_TRIP, payload: trip });
  };

  const deleteTrip = (tripId) => {
    dispatch({ type: actionTypes.DELETE_TRIP, payload: tripId });
  };

  const addExpense = (expense) => {
    dispatch({ type: actionTypes.ADD_EXPENSE, payload: expense });
  };

  const markAlertRead = (alertId) => {
    dispatch({ type: actionTypes.MARK_ALERT_READ, payload: alertId });
  };

  const addAlert = (alert) => {
    dispatch({ type: actionTypes.ADD_ALERT, payload: alert });
  };

  const updateUser = (userData) => {
    dispatch({ type: actionTypes.UPDATE_USER, payload: userData });
  };

  const updateEcoScore = (score) => {
    dispatch({ type: actionTypes.UPDATE_ECO_SCORE, payload: score });
  };

  return (
    <AppContext.Provider value={{
      ...state,
      addTrip,
      updateTrip,
      deleteTrip,
      addExpense,
      markAlertRead,
      addAlert,
      updateUser,
      updateEcoScore
    }}>
      {children}
    </AppContext.Provider>
  );
};

// Custom hook to use the context
export const useAppContext = () => {
  const context = useContext(AppContext);
  if (!context) {
    throw new Error('useAppContext must be used within an AppProvider');
  }
  return context;
};