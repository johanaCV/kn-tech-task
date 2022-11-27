# kn-tech-task

Note:
dev branch contains both the backend and frontend implementation that supports all the functionality required for city list application.

This is a rest-based application where the backend controller layer is coupled to the FE technology, in this case thymeleaf.

## Pre-reqs to run the app:

Maven version: 3.6.3+  
Java version: 11+  

## Steps to run this app locally

1. Download source code by cloning this branch: git clone -b dev https://github.com/johanaCV/kn-tech-task.git

2. Build/Run the application:  
    ###### Option 1:
     Import the source code to the preferred IDE, go to class com.kuehnenagel.city.CityListApplication and run its main method   
    ###### Option 2:  
    1. Open an OS terminal/command console, move to the directory where the project was cloned (folder kn-tech-task) and execute the command: mvn clean install  
    2. Open an OS terminal/command console -or use the terminal opened in the previous step-, move to the following directory in the cloned project (folder kn-tech-task\target) and execute the command: java -jar city-list-0.0.1.jar
            
3. Open the city list application by using your preferred browser -this app was tested using Google Chrome v 107.0.5304.122-, in the address bar enter: http://localhost:8080/v1.0/cities
  
Note:
  * user with role ROLE_ALLOW_EDIT to edit a city (name and/or photo uri): username: user_edit, password: pass123!
