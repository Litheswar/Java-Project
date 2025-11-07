from flask import Flask, jsonify
from flask_cors import CORS, cross_origin
import json

app = Flask(__name__)
CORS(app, resources={r"/api/*": {"origins": "*"}})  # Enable CORS for all API routes

# Mock data
mock_users = [
    {"id": 1, "name": "John Doe", "age": 30, "family_count": 4, "budget": 50000, "email": "john@example.com"},
    {"id": 2, "name": "Jane Smith", "age": 25, "family_count": 2, "budget": 30000, "email": "jane@example.com"}
]

mock_trips = [
    {"id": 1, "user_id": 1, "destination": "Paris", "days": 7, "budget": 5000},
    {"id": 2, "user_id": 1, "destination": "Tokyo", "days": 10, "budget": 8000},
    {"id": 3, "user_id": 2, "destination": "New York", "days": 5, "budget": 3000}
]

mock_destinations = [
    {"id": 1, "name": "Paris", "country": "France", "cost": 5000},
    {"id": 2, "name": "Tokyo", "country": "Japan", "cost": 8000},
    {"id": 3, "name": "New York", "country": "USA", "cost": 3000},
    {"id": 4, "name": "London", "country": "UK", "cost": 4500}
]

mock_countries = [
    {"id": 1, "name": "France"},
    {"id": 2, "name": "Japan"},
    {"id": 3, "name": "USA"},
    {"id": 4, "name": "UK"}
]

mock_alerts = [
    {"id": 1, "user_id": 1, "message": "Your trip to Paris is coming up soon!", "severity": "info"},
    {"id": 2, "user_id": 1, "message": "Budget alert: You've spent 80% of your allocated budget", "severity": "warning"}
]

# API Routes
@app.route('/api/users')
@cross_origin()
def get_users():
    return jsonify(mock_users)

@app.route('/api/users/<int:user_id>')
@cross_origin()
def get_user(user_id):
    user = next((u for u in mock_users if u["id"] == user_id), None)
    return jsonify(user) if user else ("User not found", 404)

@app.route('/api/trips/user/<int:user_id>')
@cross_origin()
def get_trips_by_user(user_id):
    trips = [t for t in mock_trips if t["user_id"] == user_id]
    return jsonify(trips)

@app.route('/api/destinations')
@cross_origin()
def get_destinations():
    return jsonify(mock_destinations)

@app.route('/api/countries')
@cross_origin()
def get_countries():
    return jsonify(mock_countries)

@app.route('/api/alerts/user/<int:user_id>')
@cross_origin()
def get_alerts_by_user(user_id):
    alerts = [a for a in mock_alerts if a["user_id"] == user_id]
    return jsonify(alerts)

@app.route('/api/dashboard/stats/<int:user_id>')
@cross_origin()
def get_dashboard_stats(user_id):
    user_trips = [t for t in mock_trips if t["user_id"] == user_id]
    total_spent = sum(t["budget"] for t in user_trips)
    return jsonify({
        "totalTrips": len(user_trips),
        "totalSpent": total_spent,
        "upcomingTrips": len([t for t in user_trips if t.get("status") != "completed"])
    })

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080, debug=True)