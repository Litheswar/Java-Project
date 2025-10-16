// Mock data for the Seamless-GO application

export const mockUser = {
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
  ]
};

export const mockTrips = [
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
];

export const mockExpenses = [
  {
    id: 1,
    tripId: 1,
    category: "Accommodation",
    amount: 800,
    date: "2023-06-15",
    description: "Hotel de Paris"
  },
  {
    id: 2,
    tripId: 1,
    category: "Transportation",
    amount: 450,
    date: "2023-06-16",
    description: "Flight to Paris"
  },
  {
    id: 3,
    tripId: 1,
    category: "Food",
    amount: 320,
    date: "2023-06-17",
    description: "Dining in Paris"
  },
  {
    id: 4,
    tripId: 1,
    category: "Activities",
    amount: 280,
    date: "2023-06-18",
    description: "Eiffel Tower tickets"
  },
  {
    id: 5,
    tripId: 1,
    category: "Shopping",
    amount: 650,
    date: "2023-06-20",
    description: "Souvenirs"
  }
];

export const mockAlerts = [
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
];

export const mockDestinations = [
  {
    id: 1,
    name: "Paris, France",
    country: "France",
    description: "The City of Light offers world-class art, cuisine, and culture.",
    co2Rating: 4,
    sustainabilityScore: 85,
    image: "/src/assets/paris.jpg"
  },
  {
    id: 2,
    name: "Kyoto, Japan",
    country: "Japan",
    description: "Ancient temples, traditional gardens, and modern innovation.",
    co2Rating: 5,
    sustainabilityScore: 92,
    image: "/src/assets/kyoto.jpg"
  },
  {
    id: 3,
    name: "Costa Rica",
    country: "Costa Rica",
    description: "Biodiverse rainforests, volcanoes, and commitment to sustainability.",
    co2Rating: 5,
    sustainabilityScore: 95,
    image: "/src/assets/costarica.jpg"
  },
  {
    id: 4,
    name: "Reykjavik, Iceland",
    country: "Iceland",
    description: "Geothermal energy, Northern Lights, and unique landscapes.",
    co2Rating: 4,
    sustainabilityScore: 88,
    image: "/src/assets/iceland.jpg"
  }
];

export const mockChartData = {
  expensesByCategory: [
    { name: 'Accommodation', value: 800 },
    { name: 'Transportation', value: 450 },
    { name: 'Food', value: 320 },
    { name: 'Activities', value: 280 },
    { name: 'Shopping', value: 650 }
  ],
  spendingOverTime: [
    { date: 'Jun 15', value: 800 },
    { date: 'Jun 16', value: 1250 },
    { date: 'Jun 17', value: 1570 },
    { date: 'Jun 18', value: 1850 },
    { date: 'Jun 19', value: 2100 }
  ],
  ecoImpact: [
    { month: 'Jan', co2Saved: 50 },
    { month: 'Feb', co2Saved: 75 },
    { month: 'Mar', co2Saved: 120 },
    { month: 'Apr', co2Saved: 90 },
    { month: 'May', co2Saved: 110 },
    { month: 'Jun', co2Saved: 150 }
  ]
};

export const mockMapData = {
  center: [48.8566, 2.3522], // Paris coordinates
  markers: [
    {
      position: [48.8566, 2.3522],
      title: "Eiffel Tower",
      description: "Iconic landmark of Paris"
    },
    {
      position: [48.8606, 2.3376],
      title: "Louvre Museum",
      description: "World's largest art museum"
    },
    {
      position: [48.8529, 2.3498],
      title: "Notre-Dame Cathedral",
      description: "Medieval Catholic cathedral"
    }
  ]
};