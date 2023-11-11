This is a test framework. The root directory for the project is named: webstaurantAssessment.

EXPLAIN HOW TO USE GITHUB

The recommended way to execute the tests (and therefore the project) is through Maven.
Use this command in your console of choice:
`mvn -test`

There are 3 tests: cartAddRemove, cartAddRemoveOne, and checkAllNames
I expect that 2/3 will pass with checkAllNames being the one to fail.
On average, the tests take around 10 minutes to execute (anticipating 1 test failure; if that test ran fully it would take longer).

The project is made up of:
- Java
- Maven
- Selenium WebDriver
- TestNG
- WebDriverManager