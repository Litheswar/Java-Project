import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { AnimatePresence } from 'framer-motion';
import { AppProvider } from './context/AppContext';
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

function App() {
  return (
    <AppProvider>
      <Router>
        <div className="min-h-screen bg-gradient-to-br from-blue-50 to-green-50">
          <Navbar />
          <AnimatePresence mode="wait">
            <Routes>
              <Route path="/" element={<LandingPage />} />
              <Route path="/login" element={<LoginPage />} />
              <Route path="/dashboard" element={<DashboardPage />} />
              <Route path="/planner" element={<PlannerPage />} />
              <Route path="/expenses" element={<ExpensesPage />} />
              <Route path="/alerts" element={<AlertsPage />} />
              <Route path="/profile" element={<ProfilePage />} />
              <Route path="/settings" element={<SettingsPage />} />
            </Routes>
          </AnimatePresence>
          <Footer />
        </div>
      </Router>
    </AppProvider>
  );
}

export default App;