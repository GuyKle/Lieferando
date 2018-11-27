# Getting started with Page Object Pattern for your Selenium tests
    @ 2018 Guy Klein
------------------------------------------------------------------
## Intro:
The assignment was to test the functionality of the search field on the main screen.  
Therefore, no other tests were written on any of the other areas that are not directly related to the search field!  
The assignment specifically mentions that the tests are to be written with Java over Selenium WebDriver, and so it was done, although it might not be the ideal choice of tooling for the technological stack and the experience of the surrounding teams.  
Multi-browser support included - Chrome and Firefox.  
Multi-language support included - German and French.  

## Tools:
IntelliJ - Gradle project with pure Java8 running on Selenium webdriver. Test runner is Junit, reports exported to HTML.  
Prerequisites: Linux env with gradle and git support

## Setting up:
### Clone the git from the repository
$ git clone <gitrepo>

### Browse to the folder where the repository was cloned and run
$ gradle clean test -D<browser name>
(default is chrome, parameter is case insensitive)

## Structural notes, etc:
- chromedriver and geckodriver (linux versions!) added to the project for multi-browser support.  
- Test package was separated from main package where usually application code is located.  
- Tests were split into 2 categories - UI and API (respectively for frontend and backend tests).  
- Tests (with abstract base class) and pageobjects\methods could and should be split into more classes, but this should be done with a bigger picture in mind, not for something as basic as "test ONLY the search field functionality". In such case, it is perfectly fine to have 6 short tests in the same class.  
- utils package created to host utilities required for certain tests - e.g. DB connection, multi-language support, token fetching, etc.
- Foundations for multi-language support can be found under utils in property files, but were not fully implemented due to the effort required in implementing a properties handler. Same goes for env properties, but in the case of the exercise there was only 1 env - www.lieferando.de

## Assumption:
- API tests are out of scope for this exercise.  
- Logging should be done properly with a logger. e.g. Log4j (crucial for identifying failure points).  
- Code formatter could also be used for more readable code, but is not a must have therefore I did not include it.  
- Automating responsive UI tests is out of scope and also pointless, as I tested manually (using chrome developer tools) and the search field has the same behavior in all sizes and devices.  
- Automating a test to check search field maximum size is a waste of effort.  
- Load and performance tests are out of scope for this assignment and should anyways not run on live products. Tests were restricted to single agents running simple test flows.  
- Changing locale was done with URL and not with dropdown because testing the dropdown was not a requirement, only testing the search field.  

## Issues I found while testing:
- Facebook "like text" and other elements in the mainpage are not fully responsive.  
- Zipcode resilience is displayed in the result page.
