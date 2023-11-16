# 4650Project
installtion requirements:

Apache TomCat 9.0 with Jdk 21
Esclipe Must Also have JDk 21

Steps to Configure Eclipse with Tomcat:

Go to the HELP tab on top of esclipse
Click INSTALL NEW SOFTWARE
Scroll to Find download.org/relases/2023 
Then scoll to find WEB XML JAVA EE and OSGI click next and finish
Then restart and allow ascess
To The TOP right there is a OPEN PERSPECTIVE tab then onced clicked scroll to Java EE
A server tab on the console should open up thats says NO SERVERS ARE AVAIABLE click on it
Filter to APache Tomcat 9 and browse to the location of the files where you put Apache Tomcat
Then the server tab should stay STOPPED SCYRNO double clicked that and a server config should show up
Then change the TOMCAT Admin PORT TO 8081
CHANGE HTTP/1.1 to 8082
Server should stay started and synced
Then right click the project scroll to find run as look for the Apache Tomcat 9.0 server then it should start 
