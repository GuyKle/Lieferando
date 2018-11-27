package ui.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import sun.java2d.cmm.Profile;
import ui.pages.HomePage;
import ui.pages.ResultsPage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SearchFieldTests {
    WebDriver browser;

    @Before
    public void setup() {
        System.setProperty("webdriver.gecko.driver", "./webdrivers/geckodriver");
        System.setProperty("webdriver.chrome.driver", "./webdrivers/chromedriver");

        if (System.getProperty("browser").toLowerCase().equals("firefox")) {
            FirefoxOptions option = new FirefoxOptions();
            option.addPreference("dom.webnotifications.enabled", false);
            option.addPreference("app.update.enabled", false);
            option.addPreference("geo.enabled", false);
            browser = new FirefoxDriver(option);
        }
        else {
            ChromeOptions options = new ChromeOptions();
            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("profile.managed_default_content_settings.geolocation", 2);
            options.setExperimentalOption("prefs", prefs);
            browser = new ChromeDriver(options);
        }
        browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //setting the generic timeout
    }

    @Test
    @DisplayName("TEST - main page is accessible and displaying the correct title")
    public void testLoadMainPage() {
        HomePage homePage = new HomePage(browser);

        assertTrue(homePage.getPageTitle().startsWith("Lieferando.de |")); //assert expected page has opened
    }

    @Test
    @DisplayName("TEST - functionality does not break under SQL injections")
    public void testSqlInjections() {
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
    public void testNonExistingZipCode() {
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
    public void testInvalidZipCode() {
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
    public void testHappyPath() {
        String testAddress = "10409";
        HomePage homePage = new HomePage(browser);

        homePage.setSearch_field(testAddress);
        homePage.clickSubmitButton();

        ResultsPage resultspage = new ResultsPage(browser);
        assertTrue(resultspage.getLocation().contains(testAddress));
    }

    @Test
    @DisplayName("TEST - checks that the google suggests food for mutti")
    public void testGoogleSuggestions() {
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


