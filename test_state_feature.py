#!/usr/bin/env python3
"""
Test script to verify the State/Province selection feature implementation
"""

import requests
import json

def test_states_endpoint():
    """Test the /api/countries-states endpoint"""
    print("Testing /api/countries-states endpoint...")
    
    # Test with France
    response = requests.get('http://localhost:8080/api/countries-states?countryCode=FR')
    assert response.status_code == 200, f"Expected 200, got {response.status_code}"
    
    states = response.json()
    assert len(states) > 0, "Expected at least one state for France"
    print(f"✓ France states: {len(states)} states returned")
    
    # Test with Japan
    response = requests.get('http://localhost:8080/api/countries-states?countryCode=JP')
    assert response.status_code == 200, f"Expected 200, got {response.status_code}"
    
    states = response.json()
    assert len(states) > 0, "Expected at least one state for Japan"
    print(f"✓ Japan states: {len(states)} states returned")
    
    print("✓ All endpoint tests passed")

def test_frontend_availability():
    """Test that frontend is running"""
    print("Testing frontend availability...")
    
    response = requests.get('http://localhost:3006')
    assert response.status_code == 200, f"Expected 200, got {response.status_code}"
    print("✓ Frontend is running")

def test_backend_availability():
    """Test that backend is running"""
    print("Testing backend availability...")
    
    response = requests.get('http://localhost:8080/api/simple-test')
    assert response.status_code == 200, f"Expected 200, got {response.status_code}"
    assert "Simple test endpoint working" in response.text
    print("✓ Backend is running")

def main():
    """Run all tests"""
    print("Running State/Province Feature Tests...\n")
    
    try:
        test_backend_availability()
        test_frontend_availability()
        test_states_endpoint()
        
        print("\n🎉 All tests passed! The State/Province selection feature is working correctly.")
        
    except Exception as e:
        print(f"\n❌ Test failed: {e}")
        return 1
    
    return 0

if __name__ == "__main__":
    exit(main())