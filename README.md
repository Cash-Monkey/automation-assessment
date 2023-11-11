
## This is a test framework. 

Download the project from github in any way you fancy.
Once you have the project downloaded you should see 4 files in the root:
- src
- .gitignore
- pom.xml
- and this file, README.md

## For a first time run
The recommended way to execute the tests (and therefore the project) is through Maven.
Open a console of your choice.
Make sure you have Maven installed with the console command: `mvn -version`  
  
If you see an error similar to: *"mvn: command not found"* 
then you will have to [download](https://maven.apache.org/download.cgi) 
and [install](https://maven.apache.org/install.html) 
Maven first.

Once your Maven is configured, navigate to the root directory of this project (with the pom.xml).
Setup and execute the project with this console command:
`mvn clean install test`

That's it. The console should start moving and a Chrome window should open soon after.

## Project details
There are 3 tests: cartAddRemove, cartAddRemoveOne, and checkAllNames
I expect that 2/3 will pass with checkAllNames being the one to fail.
On average, the tests take around 10 minutes to execute (anticipating 1 test failure; if that test ran fully it would take longer).

The project is made up of:
- Java
- Maven
- Selenium WebDriver
- TestNG
- WebDriverManager