package ui.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.Test;
import ui.pages.HomePage;
import ui.pages.ResultsPage;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SearchFieldTests {
    WebDriver browser;

    @Before
    public void setup() {
        System.setProperty("webdriver.gecko.driver", "./webdrivers/geckodriver");
        System.setProperty("webdriver.chrome.driver", "./webdrivers/chromedriver");
        browser = new ChromeDriver();
        browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //setting the generic timeout
    }

    @Test
    @DisplayName("TEST - main page is accessible and displaying the correct title")
    public void TEST1_LOAD_MAIN_PAGE() {
        HomePage homePage = new HomePage(browser);

        assertTrue(homePage.getPageTitle().startsWith("Lieferando.de |")); //assert expected page has opened
    }

    @Test
    @DisplayName("TEST - functionality does not break under SQL injections")
    public void TEST2_SQL_INJECTIONS() {
        HomePage homePage = new HomePage(browser);
        String titleText = "Lieferando.de |";
        List<String> sqlInjections = Arrays.asList("search=keyword'","'1'='1","'%keyword'","'1'='1%'");

        for (String inject: sqlInjections) {
            homePage.setSearch_field(inject);
            homePage.clickSubmitButton();
            assertTrue(homePage.getPageTitle().startsWith(titleText));
        }
    }

    @Test
    @DisplayName("TEST - correct error message returned after inputting a zipcode that does not exist")
    public void TEST3_NON_EXISTING_ZIPCODE() {
        String nonExistingZipcode = "66666";
        String expectedErrorMessageEn = "postcode does not exist";
        String expectedErrorMessageDe = "Postleitzahl besteht nicht";

        HomePage homePage = new HomePage(browser);
        homePage.clearSearchField();
        assertTrue(homePage.getErrorMessage(nonExistingZipcode).contains(expectedErrorMessageEn));
        homePage.changeLocale("German");
        assertTrue(homePage.getErrorMessage(nonExistingZipcode).contains(expectedErrorMessageDe));
    }

    @Test
    @DisplayName("TEST - correct error message returned after inputting a zipcode that is not of valid length")
    public void TEST4_INVALID_ZIPCODE() {
        String invalidZipcode = "123";
        String expectedErrorMessageEn = "postcode is invalid";
        String expectedErrorMessageDe = "Adresse ist leider inkorrekt";

        HomePage homePage = new HomePage(browser);
        homePage.clearSearchField();
        assertTrue(homePage.getErrorMessage(invalidZipcode).contains(expectedErrorMessageEn));
        homePage.changeLocale("German");
        assertTrue(homePage.getErrorMessage(invalidZipcode).contains(expectedErrorMessageDe));
    }

    @Test
    @DisplayName("TEST - a valid address was entered and should be displayed in the search results")
    public void TEST5_HAPPY_PATH() {
        System.out.println("");
        String testAddress = "10409";
        HomePage homePage = new HomePage(browser);

        homePage.setSearch_field(testAddress);
        homePage.clickSubmitButton();

        ResultsPage resultspage = new ResultsPage(browser);
        assertTrue(resultspage.getLocation().contains(testAddress));
    }

    @Test
    @DisplayName("TEST - checks that the google suggests food for mutti")
    public void TEST6_GOOGLE_SHOWS_SUGGESTIONS() {
        String testAddress = "Platz der Republik (Berlin), Berlin";
        HomePage homePage = new HomePage(browser);

        assertNotNull(homePage.getSearchResultsByAddress(testAddress));
        assertTrue(homePage.getSearchResultsByAddress(testAddress).size()>0);
    }

    @After
    public void close(){
        browser.close();
    }
}


