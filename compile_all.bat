@echo off
echo Compiling all Java source files...

javac -cp src/main/java -d bin src/main/java/com/smarttravelplanner/db/DBConnection.java
javac -cp src/main/java -d bin src/main/java/com/smarttravelplanner/db/BaseDAO.java
javac -cp src/main/java -d bin src/main/java/com/smarttravelplanner/db/UserDAO.java
javac -cp src/main/java -d bin src/main/java/com/smarttravelplanner/db/CountryDAO.java
javac -cp src/main/java -d bin src/main/java/com/smarttravelplanner/db/StateDAO.java
javac -cp src/main/java -d bin src/main/java/com/smarttravelplanner/db/DestinationDAO.java
javac -cp src/main/java -d bin src/main/java/com/smarttravelplanner/db/TripDAO.java
javac -cp src/main/java -d bin src/main/java/com/smarttravelplanner/db/TripHistoryDAO.java
javac -cp src/main/java -d bin src/main/java/com/smarttravelplanner/db/ExpenseBreakdownDAO.java
javac -cp src/main/java -d bin src/main/java/com/smarttravelplanner/db/ActivityDAO.java
javac -cp src/main/java -d bin src/main/java/com/smarttravelplanner/db/AlertDAO.java
javac -cp src/main/java -d bin src/main/java/com/smarttravelplanner/db/ExpenseDAO.java
javac -cp src/main/java -d bin src/main/java/com/smarttravelplanner/db/RouteDAO.java
javac -cp src/main/java -d bin src/main/java/com/smarttravelplanner/db/SimpleDBTest.java
javac -cp src/main/java -d bin src/main/java/com/smarttravelplanner/db/CountryDAOTest.java

echo Compilation completed!
pause