# kn-tech-task

Note:
Please switch to dev branch to download the whole application -containing backend and frontend implementation-

This master branch contains only the backend implementation/rest api operations that support the functionality required for city list application.
 
These apis are completely decoupled from the consumers -as it should be-, therefore can be consumed by any client -frontend, mobile, etc- that 
is capable of consuming rest services.

## Pre-reqs to run the app:

Maven version: 3.6.3+  
Java version: 11+  

## Steps to run this app locally

1. Download source code by cloning this branch: git clone -b master https://github.com/johanaCV/kn-tech-task.git

2. Build/Run the application:  
    ###### Option 1:
     Import the source code to the preferred IDE, go to class com.kuehnenagel.city.CityListApplication and run its main method   
    ###### Option 2:  
    1. Open an OS terminal/command console, move to the directory where the project was cloned (folder kn-tech-task) and execute the command: mvn clean install  
    2. Open an OS terminal/command console -or use the terminal opened in the previous step-, move to the following directory in the cloned project (folder kn-tech-task\target) and execute the command: java -jar city-list-0.0.1.jar
            
3. Consume the city list rest apis by using your preferred rest client utility. e.g postman, soapUI, etc