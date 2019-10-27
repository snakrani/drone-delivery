1 Project Description:
Java based project to implement drone-carried deliveries to customers in a small town. 

2 Tools Used 

Language : `Java 8`.

Unit Tesing and Code Coverage:  `Junit,Jacoco Plugin for Code Coverage`.

Code Quality: `Sonar`.

API-DOCS: `Maven Javadoc Plugin`.  

3 Build Solution:

`mvn clean install`(At command line) -> generates the fat jar(with dependencies) in home folder(copied from target folder).

#4 Execute Solution:

run below command in project directory
`java -jar target/drone-delivery-0.0.1-SNAPSHOT.jar {filePath}`
`java -jar target/drone-delivery-0.0.1-SNAPSHOT.jar c:\orders.txt`

#5 Executing unit tests only:

run below command to run unit tests.
`mvn test` > Runs the unit tests and displays test results.
Also generates the `Code Coverage Report`: Can be accessible from `<project directory>\target\site\jacoco\index.html`(open in browser)

#6 Accessing API Docs

Step #5 , generates the `JavaDocs` and can be accessed `<project directory>\target\apiDocs\index.html`(open in browser) 
 
#7 run sonar

Start sonar server and run mvn sonar:sonar

#8 My Understanding and assumptions:
1. Drones has no down time and once order reached, Drone starts immediately picking new order. 
2. Order placed in between 10 PM and 6 PM will have Detractors scores.
3. It will be faster if, Drone can take multiple items and deliver items of the same route together.
4. Only one drone is available. If we have multiple drones available we can do parallel order delivering.
5. Not processed orders for a day is stored in undelivered-orders file to keep track of unprocessed orders.
