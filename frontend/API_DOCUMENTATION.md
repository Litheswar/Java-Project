# Frontend API Documentation

This document describes the API endpoints used by the frontend to communicate with the Smart Travel Planner backend.

## Base URL
```
http://localhost:8080/api
```

## Endpoints

### 1. Get All Countries
```
GET /api/countries
```

**Response:**
```json
[
  {
    "id": 1,
    "name": "France",
    "code": "FR",
    "sustainabilityScore": 85,
    "coordinates": {
      "lat": 46.603354,
      "lng": 1.888334
    }
  },
  {
    "id": 2,
    "name": "Japan",
    "code": "JP",
    "sustainabilityScore": 92,
    "coordinates": {
      "lat": 36.204824,
      "lng": 138.252924
    }
  }
]
```

### 2. Get Places by Country
```
GET /api/places?countryId={id}
```

**Parameters:**
- `countryId` (integer): The ID of the country

**Response:**
```json
[
  {
    "id": 1,
    "name": "Paris",
    "countryId": 1,
    "coordinates": {
      "lat": 48.8566,
      "lng": 2.3522
    }
  }
]
```

### 3. Get Planner Options
```
GET /api/planner/options
```

**Response:**
```json
{
  "mealsPerDay": [1, 2, 3, 4, 5],
  "transportTypes": ["rail", "air", "sea", "mixed"],
  "foodTypes": ["veg", "non-veg", "mixed"]
}
```

### 4. Submit Planner Estimate
```
POST /api/planner/estimate
```

**Request Body:**
```json
{
  "destination": 1,
  "travelers": 2,
  "tripDays": 7,
  "mealsPerDay": 3,
  "transportType": "mixed",
  "foodType": "mixed",
  "budget": 5000
}
```

**Response:**
```json
{
  "estimatedCost": 4850,
  "sustainabilityScore": 78,
  "co2Savings": 150
}
```

## Error Handling

All API calls include proper error handling:
- Network errors are caught and displayed to the user
- HTTP error codes are handled appropriately
- User-friendly error messages are shown

## Caching

The frontend implements caching for performance:
- Countries list: Cached for 5 minutes
- Planner options: Cached for 5 minutes

Cache is automatically invalidated after the TTL expires.