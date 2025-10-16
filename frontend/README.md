# Seamless-GO Frontend

Modern, responsive web application for the Seamless-GO (Smart Travel Planner) project built with React.js, Tailwind CSS, and Framer Motion.

## Enhanced Features

This version includes advanced UI/UX enhancements and gamification features:

- AI-driven travel quotes and tips
- Interactive world map with sustainable destinations
- Personalized destination discovery quiz
- AI travel advisor chat bubble
- Real-time cost and CO₂ footprint tracking
- Currency converter for multi-country trips
- Expense auto-categorization
- Budget deviation visualization
- Travel persona generation
- Badge progress tracking
- Trip countdown alerts
- Eco-friendly suggestion alerts
- Monthly productivity dashboard
- PDF/CSV trip report export

## Features

- **Modern UI/UX**: Glassmorphism design with smooth animations
- **Responsive Design**: Mobile-first approach with Tailwind CSS
- **Interactive Components**: Charts, maps, modals, and steppers
- **Page Transitions**: Smooth animations with Framer Motion
- **Data Visualization**: Expense tracking with Recharts
- **Route Visualization**: Interactive maps with Leaflet
- **Gamification**: Badges, streaks, and eco-scores

## Tech Stack

- **React.js** (v18+) - Frontend library
- **Tailwind CSS** - Utility-first CSS framework
- **Framer Motion** - Animation library
- **React Router** (v6) - Declarative routing
- **Recharts** - Data visualization
- **Leaflet** - Interactive maps
- **Heroicons** - Icon library
- **Vite** - Build tool and development server

## Project Structure

```
frontend/
├── src/
│   ├── assets/          # Images, icons, and mock data
│   ├── components/      # Reusable UI components
│   ├── context/         # React context providers
│   ├── hooks/           # Custom hooks (API, form, localStorage)
│   ├── pages/           # Page components
│   ├── App.jsx          # Main application component
│   └── main.jsx         # Entry point
├── index.html           # HTML template
├── tailwind.config.js   # Tailwind CSS configuration
├── postcss.config.js    # PostCSS configuration
├── vite.config.js       # Vite configuration
└── package.json         # Project dependencies
```

## Pages

1. **Landing Page** (`/`) - Marketing and introduction
2. **Login Page** (`/login`) - Authentication
3. **Dashboard** (`/dashboard`) - Overview and stats
4. **Trip Planner** (`/planner`) - Step-by-step trip planning
5. **Expenses** (`/expenses`) - Expense tracking and route visualization
6. **Alerts** (`/alerts`) - Travel notifications
7. **Profile** (`/profile`) - User profile and achievements
8. **Settings** (`/settings`) - Account and preferences

## Components

- `Button` - Customizable button with variants
- `Card` - Glassmorphism card with hover effects
- `Chart` - Recharts integration with custom styling
- `Map` - Leaflet map component
- `Modal` - Animated modal dialog
- `Navbar` - Responsive navigation bar
- `Footer` - Website footer
- `Stepper` - Multi-step process indicator
- `TravelQuote` - AI-driven daily travel quotes
- `WorldMap` - Interactive sustainable destinations map
- `DiscoveryQuiz` - Personalized destination quiz
- `TravelAdvisor` - AI chat for eco-travel tips
- `CurrencyConverter` - Multi-currency converter
- `TravelPersona` - Personalized traveler identity
- `BadgeProgress` - Achievement tracking
- `ExportReport` - Trip report export
- `ExpenseCategorization` - Auto expense categorization
- `BudgetDeviationChart` - Budget vs reality visualization
- `ProductivityDashboard` - Monthly travel metrics

## Getting Started

1. Install dependencies:
   ```bash
   npm install
   ```

2. Start the development server:
   ```bash
   npm run dev
   ```

3. Build for production:
   ```bash
   npm run build
   ```

4. Preview production build:
   ```bash
   npm run preview
   ```

## Development

The application uses mock data for development purposes. In a real implementation, you would connect to the Java backend API endpoints:

- `GET /api/users/{id}` - User profile
- `GET /api/trips` - Trip list
- `GET /api/trips/{id}/expenses` - Trip expenses
- `GET /api/destinations` - Destination data
- `GET /api/alerts` - User alerts
- `GET /api/gamification/badges` - User badges

## Design Principles

- **Mobile-first responsive design**
- **Accessibility** (ARIA labels, keyboard navigation)
- **Performance** (Code splitting, lazy loading)
- **Consistency** (Design system with Tailwind)
- **User experience** (Smooth transitions, feedback)

## Customization

1. **Tailwind Configuration**: Modify `tailwind.config.js` to change colors, fonts, and breakpoints
2. **Theme Colors**: Update color palette in `src/index.css`
3. **Animations**: Adjust Framer Motion settings in components
4. **Branding**: Update logo and brand colors in `Navbar.jsx` and `Footer.jsx`

## Deployment

Build the application for production:

```bash
npm run build
```

The output will be in the `dist/` directory, ready for deployment to any static hosting service.