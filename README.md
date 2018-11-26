# Getting started with Page Object Pattern for your Selenium tests
    @ 2018 Guy Klein
------------------------------------------------------------------
## Intro:
The assignment was to test the functionality of the search field on the main screen.  
Therefore, no other tests were written on any other areas that are not directly related to the search field!  
The assignment specifically named the tests to be written with Java over Selenium WebDriver, and so it was done.  
Multi-browser support included - Chrome and Firefox.  
Multi-language support included - German and French.  

## Tools:
IntelliJ - Gradle project with pure Java running on Selenium webdriver. Test runner is Junit.

## Structural notes, etc:
chromedriver and geckodriver (linux version!) added to the project for multi-browser support.  
Test package was separated from main package where usually application code is located.  
Tests were split into 2 categories - UI and API (respectively frontend and backend).  
utils package created to host utilities required for certain tests - e.g. DB connection, token fetching, etc.

## Assumption:
API tests are out of scope for this exercise.  
Logging should be done properly with a logger.  
Reporting should be extended, either to HTML or XML.  
Automating responsive UI is out of scope and also pointless.  
Automating a test to chech search field maximum size is a waste of effort.  
Load and performance tests are out of scope for this assignment and should anyways not run on live.  
Changing locale was done with URL and not with dropdown because testing the dropdown was not a requirement.  

## Issues:
Facebook like message is not responsive.  
Zipcode resilience is displayed in the result page.  
Search button disappears completely in a very small window.  
