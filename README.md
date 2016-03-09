# Login-Webapp-MySQL
---
This webapp contains Login form and it validate a user dy username and password.
User data is stored in a MYSQL database.

#####Pre-requisites
* Java Development Kit (JDK) 1.8
* MYSQL Server 5.7
* Apache Maven 3.3
* Apache Tomcat 8.0

#####Config & Build
1.Goto the location of the program folder</br>
`$ cd <path>`</br>
2.Run following command</br>
`mvn clean install`

#####Deploy on TomCat
Copy the .war file (webapp-login.tar) to the 'webapps' folder of TomCat $CATALINA_HOME/webapps</br>
eg: `cp <path to .war file><file name>.war $CATALINA_HOME/webapps`

#####Execute Program
Type following URL in your browser</br>
`localhost:8080/webapp-login`
