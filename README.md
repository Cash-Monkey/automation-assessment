
## This is a test framework. 

Download the project from github in any way you fancy.
Once you have the project downloaded you should see 4 files in the root:
- src
- .gitignore
- pom.xml
- and this file, README.md

## For a first time run
The recommended way to execute the tests (and therefore the project) is through Maven.
Navigate to the root directory and open a console (not necessarily in that order).

Use this command from the console of choice to setup and execute the project:
`mvn clean install test`

That's it.

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