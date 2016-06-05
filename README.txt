Gradle is used as build system.
SPOCK testing framework for unit testing.
Groovy and Java used when writing test script.
Event name and participants name are case-sensitive



I could not write test classes for Driver classes as it takes input from system and for time constraints.

Q: How to compile?
A: 1. extract the zip file in any folder (e.g. ~/codechallenge/cpal/)
   2. run gradlew clean build
   
   Code will be build and built jar file will be placed in ~/codechallenge/cpal/build/libs.
   to check Test execution report open in ~/codechallenge/cpal/build/reports/tests/index.html
   
   
Q: How to run?
A: put the generated jar file (the one in ~/codechallenge/cpal/build/libs) in class path and run "java mithr.ems.Main"

if you want to see the code in eclipse run "gradlew cleanEclipse eclipse" and import the project into workspace