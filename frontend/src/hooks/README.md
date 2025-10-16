# Custom React Hooks

This directory contains custom React hooks that provide reusable logic across the application.

## Available Hooks

### 1. useApi.js
A hook for handling API calls with loading states and error handling.

**Features:**
- Supports GET, POST, PUT, DELETE methods
- Loading state management
- Error handling
- Automatic execution for GET requests
- Manual execution for all methods

**Usage:**
```javascript
import { useApi } from './hooks/useApi';

// For GET requests
const { data, loading, error } = useApi('/api/users/1', 'GET');

// For POST requests
const { execute, loading, error } = useApi('/api/users', 'POST');
const handleSubmit = async () => {
  try {
    const result = await execute({ name: 'John', email: 'john@example.com' });
    console.log(result);
  } catch (err) {
    console.error(err);
  }
};
```

### 2. useForm.js
A hook for form handling with validation capabilities.

**Features:**
- Field value management
- Validation rules (required, minLength, maxLength, email, custom)
- Error tracking
- Touched field tracking
- Form reset functionality

**Usage:**
```javascript
import { useForm } from './hooks/useForm';

const validationRules = {
  email: {
    required: { message: 'Email is required' },
    email: { message: 'Please enter a valid email' }
  },
  password: {
    required: { message: 'Password is required' },
    minLength: { value: 6, message: 'Password must be at least 6 characters' }
  }
};

const { values, errors, handleChange, handleBlur, validateAll } = useForm(
  { email: '', password: '' },
  validationRules
);

const handleSubmit = (e) => {
  e.preventDefault();
  if (validateAll()) {
    // Submit form
  }
};
```

### 3. useLocalStorage.js
A hook for managing localStorage values with state synchronization.

**Features:**
- Automatic localStorage persistence
- State synchronization
- JSON serialization/deserialization
- Error handling

**Usage:**
```javascript
import { useLocalStorage } from './hooks/useLocalStorage';

// Basic usage
const [name, setName] = useLocalStorage('userName', 'John Doe');

// With objects
const [user, setUser] = useLocalStorage('user', { name: '', email: '' });
```

## Best Practices

1. **Consistent Naming**: All hooks should start with "use"
2. **Error Handling**: Hooks should handle errors gracefully
3. **TypeScript Support**: Hooks should be written with TypeScript in mind
4. **Documentation**: Each hook should have clear documentation
5. **Testing**: Hooks should be easily testable

## Adding New Hooks

When adding new hooks, follow this pattern:

1. Create a new file with the hook name (e.g., `useMyHook.js`)
2. Export the hook as a named export
3. Add JSDoc comments for documentation
4. Include usage examples in the README
5. Follow the existing code style and patterns