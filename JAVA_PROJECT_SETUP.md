# Java Project Setup Guide

This document explains how to set up, compile, and run the Smart Travel Planner Java project using only the JDK—no Maven, Gradle, or JUnit.

## Project Structure

```
Java-Project/
├── bin/                    # Compiled class files
├── src/
│   ├── main/
│   │   └── java/           # Main source code
│   │       └── com/
│   │           └── smarttravelplanner/
│   │               ├── db/          # Database-related classes
│   │               ├── model/       # Data model classes
│   │               ├── service/     # Business logic classes
│   │               ├── utils/       # Utility classes
│   │               └── Main.java    # Main application entry point
│   └── test/
│       └── java/           # Test source code
│           └── com/
│               └── smarttravelplanner/
├── compile_all.bat         # Batch file to compile all sources
├── run_test.bat            # Batch file to run simple database test
└── README.md               # Project documentation
```

## Configuration Files

### .classpath
Updated to point to the correct source folders:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<classpath>
	<classpathentry kind="src" output="bin" path="src/main/java"/>
	<classpathentry kind="src" output="bin" path="src/test/java"/>
	<classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-17"/>
	<classpathentry kind="output" path="bin"/>
</classpath>
```

### .project
Standard Eclipse project configuration:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<projectDescription>
	<name>Java-Project</name>
	<comment></comment>
	<projects>
	</projects>
	<buildSpec>
		<buildCommand>
			<name>org.eclipse.jdt.core.javabuilder</name>
			<arguments>
			</arguments>
		</buildCommand>
	</buildSpec>
	<natures>
		<nature>org.eclipse.jdt.core.javanature</nature>
	</natures>
</projectDescription>
```

## Compilation Instructions

### Manual Compilation
To compile individual files:
```bash
javac -cp src/main/java -d bin src/main/java/com/smarttravelplanner/db/DBConnection.java
javac -cp src/main/java -d bin src/main/java/com/smarttravelplanner/db/BaseDAO.java
# ... compile other files as needed
```

### Batch Compilation
Use the provided `compile_all.bat` file:
```bash
compile_all.bat
```

This batch file compiles all the essential database-related classes in the correct order.

## Running the Application

### Running Simple Database Test
Use the provided `run_test.bat` file:
```bash
run_test.bat
```

This runs the `SimpleDBTest` class which tests the database connection.

### Running the Main Application
```bash
java -cp bin com.smarttravelplanner.Main
```

## Code Cleaning Guidelines

### Unused Imports Warning
"import ... is never used" - Remove these lines safely:
```java
// Remove lines like:
import java.util.ArrayList;  // If ArrayList is not used
import java.util.List;       // If List is not used
```

### Unused Fields Warning
"value of the field ... is not used" - Variable declared but never referenced:
```java
public class Example {
    private String unusedField;  // Remove this line
    
    public void doSomething() {
        // Method implementation
    }
}
```

### Unused Methods Warning
"method ... is never used locally" - Can be deleted if not called:
```java
public class Example {
    // Remove methods that are never called
    private void unusedMethod() {
        // Implementation
    }
    
    public void usedMethod() {
        // This method is used, so keep it
    }
}
```

## Debugging with Plain JDK

### Print Debugging
Insert `System.out.println()` at key points to trace variables:
```java
public int calculateSum(int a, int b) {
    System.out.println("calculateSum called with a=" + a + ", b=" + b);
    int result = a + b;
    System.out.println("Result: " + result);
    return result;
}
```

### Using jdb (Java Debugger)
```bash
jdb -classpath bin com.smarttravelplanner.Main
```

Debugger commands:
- `stop at ClassName:lineNumber` - Set breakpoints
- `print var` - Print variable values
- `next` - Execute next line
- `step` - Step into methods
- `continue` - Continue execution
- `list` - Show current code

### Exception Handling
Watch for common exceptions:
```java
// Add null checks before using objects
if (object != null) {
    object.doSomething();
} else {
    System.out.println("Object is null!");
}
```

## Common Issues and Solutions

### "Project is missing required source folder" Error
This occurs when Eclipse can't find the source folders. Solution:
1. Ensure `.classpath` points to correct paths
2. Refresh the project in Eclipse (F5)
3. Clean and rebuild the project

### Compilation Errors
If you see "cannot find symbol" errors:
1. Make sure all dependencies are compiled first
2. Check the classpath includes all necessary source directories
3. Ensure package names match directory structure

### Database Connection Issues
If database tests fail:
1. Verify PostgreSQL is running
2. Check database credentials in environment variables or DBConnection.java
3. Ensure PostgreSQL JDBC driver is in classpath

## Testing Without JUnit

Replace JUnit-style assertions with plain Java checks:
```java
// Instead of:
// assertEquals(expected, actual);

// Use:
if (expected != actual) {
    System.out.println("Test failed: expected " + expected + " but got " + actual);
} else {
    System.out.println("Test passed");
}
```

Each test should be a self-contained `main()` method that can run directly.

## Summary

✅ Corrected folder structure  
✅ Updated `.classpath` / `.project`  
✅ Cleaned code examples  
✅ Step-by-step debugging guide  
✅ Commands for compile/run  

The project now compiles and executes successfully using only `javac` and `java`, with no external dependencies required.