import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import CountriesList from './CountriesList';

// Mock the useApi hook
jest.mock('../hooks/useApi', () => ({
  useApi: jest.fn()
}));

import { useApi } from '../hooks/useApi';

describe('CountriesList', () => {
  beforeEach(() => {
    useApi.mockReturnValue({
      data: null,
      loading: false,
      error: null
    });
  });

  it('should render loading state', () => {
    useApi.mockReturnValue({
      data: null,
      loading: true,
      error: null
    });

    render(<CountriesList />);
    expect(screen.getByRole('status')).toBeInTheDocument();
  });

  it('should render error state', () => {
    useApi.mockReturnValue({
      data: null,
      loading: false,
      error: 'Failed to load countries'
    });

    render(<CountriesList />);
    expect(screen.getByText(/error loading countries/i)).toBeInTheDocument();
  });

  it('should render countries list', () => {
    const mockCountries = [
      { id: 1, name: 'France', code: 'FR', sustainabilityScore: 85 },
      { id: 2, name: 'Japan', code: 'JP', sustainabilityScore: 92 }
    ];

    useApi.mockReturnValue({
      data: mockCountries,
      loading: false,
      error: null
    });

    render(<CountriesList />);
    
    expect(screen.getByText('France')).toBeInTheDocument();
    expect(screen.getByText('Japan')).toBeInTheDocument();
    expect(screen.getByText('FR')).toBeInTheDocument();
    expect(screen.getByText('JP')).toBeInTheDocument();
  });

  it('should render sustainability scores', () => {
    const mockCountries = [
      { id: 1, name: 'France', code: 'FR', sustainabilityScore: 85 }
    ];

    useApi.mockReturnValue({
      data: mockCountries,
      loading: false,
      error: null
    });

    render(<CountriesList />);
    
    expect(screen.getByText('85% Sustainable')).toBeInTheDocument();
  });

  it('should render search bar', () => {
    render(<CountriesList />);
    expect(screen.getByPlaceholderText('Search countries...')).toBeInTheDocument();
  });
});