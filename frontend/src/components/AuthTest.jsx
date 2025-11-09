import React from 'react';
import { useAppContext } from '../context/AppContext';
import Button from './Button';

const AuthTest = () => {
  const { isAuthenticated, user, login, logout } = useAppContext();

  const handleLogin = () => {
    login({
      id: 1,
      name: "Test User",
      email: "test@example.com",
      avatar: "TU"
    });
  };

  const handleLogout = () => {
    logout();
  };

  return (
    <div className="p-4 bg-white rounded-lg shadow-md">
      <h2 className="text-xl font-bold mb-4">Authentication Test</h2>
      <div className="mb-4">
        <p className="font-medium">Authentication Status:</p>
        <p className={isAuthenticated ? "text-green-600" : "text-red-600"}>
          {isAuthenticated ? "Authenticated" : "Not Authenticated"}
        </p>
      </div>
      
      {isAuthenticated && user && (
        <div className="mb-4">
          <p className="font-medium">User Info:</p>
          <p>Name: {user.name}</p>
          <p>Email: {user.email}</p>
        </div>
      )}
      
      <div className="flex space-x-2">
        <Button onClick={handleLogin} variant="primary" disabled={isAuthenticated}>
          {isAuthenticated ? "Already Logged In" : "Login"}
        </Button>
        <Button onClick={handleLogout} variant="secondary" disabled={!isAuthenticated}>
          {isAuthenticated ? "Logout" : "Already Logged Out"}
        </Button>
      </div>
    </div>
  );
};

export default AuthTest;