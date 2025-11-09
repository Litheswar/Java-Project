import { BrowserRouter as Router, Routes, Route, useLocation } from 'react-router-dom';
import { AnimatePresence } from 'framer-motion';
import { AppProvider, useAppContext } from './context/AppContext';
import LandingPage from './pages/LandingPage';
import LoginPage from './pages/LoginPage';
import DashboardPage from './pages/DashboardPage';
import PlannerPage from './pages/PlannerPage';
import ExpensesPage from './pages/ExpensesPage';
import AlertsPage from './pages/AlertsPage';
import ProfilePage from './pages/ProfilePage';
import SettingsPage from './pages/SettingsPage';
import Navbar from './components/Navbar';
import Footer from './components/Footer';
import ProtectedRoute from './components/ProtectedRoute';

// Component to conditionally render Navbar based on authentication and route
const ConditionalNavbar = () => {
  const { isAuthenticated } = useAppContext();
  const location = useLocation();
  
  // Show navbar only when authenticated and not on landing or login pages
  const showNavbar = isAuthenticated && !['/', '/login'].includes(location.pathname);
  
  return showNavbar ? <Navbar /> : null;
};

// Wrapper component to access context for conditional rendering
const AppContent = () => {
  // Define public routes (no authentication required)
  const publicRoutes = [
    { path: '/', element: <LandingPage /> },
    { path: '/login', element: <LoginPage /> }
  ];
  
  // Define protected routes (authentication required)
  const protectedRoutes = [
    { path: '/dashboard', element: <DashboardPage /> },
    { path: '/planner', element: <PlannerPage /> },
    { path: '/expenses', element: <ExpensesPage /> },
    { path: '/alerts', element: <AlertsPage /> },
    { path: '/profile', element: <ProfilePage /> },
    { path: '/settings', element: <SettingsPage /> }
  ];

  return (
    <Router>
      <div className="min-h-screen bg-gradient-to-br from-blue-50 to-green-50">
        <ConditionalNavbar />
        <AnimatePresence mode="wait">
          <Routes>
            {/* Public routes */}
            {publicRoutes.map((route) => (
              <Route key={route.path} path={route.path} element={route.element} />
            ))}
            
            {/* Protected routes */}
            {protectedRoutes.map((route) => (
              <Route
                key={route.path}
                path={route.path}
                element={
                  <ProtectedRoute>
                    {route.element}
                  </ProtectedRoute>
                }
              />
            ))}
          </Routes>
        </AnimatePresence>
        <Footer />
      </div>
    </Router>
  );
};

function App() {
  return (
    <AppProvider>
      <AppContent />
    </AppProvider>
  );
}

export default App;