package ui.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Properties;

public class HomePage {
    private static String HOMEPAGE_URL="http://www.lieferando.de";
    private WebDriver browser;

    public HomePage(WebDriver browser){
        this.browser=browser;
        browser.get(HOMEPAGE_URL+"/en/");
        //Initialise Elements
        PageFactory.initElements(browser, this);
    }

    //page objects
    @FindBy(id="imysearchstring")
    WebElement PO_searchField;

    @FindBy(id="ideliveryareaerror")
    WebElement PO_errorMessage;

    @FindBy(id="iautoCompleteDropDownContent")
    List<WebElement> PO_googleAutoComplete;

    @FindBy(id="submit_deliveryarea")
    WebElement PO_submitButton;

    @FindBy(className="autoCompleteDropDown")
    WebElement PO_autoComplete;


    //getters and setters

    //captures error message
    public String getErrorMessage(String address){
        setSearch_field(address);
        clickSubmitButton();
        try {
            WebDriverWait wait = new WebDriverWait(browser, 10);
            wait.until(ExpectedConditions.visibilityOfAllElements(PO_errorMessage));
        }
        catch (NoSuchElementException e)
        {
            System.out.println("Expected error message did not appear within 10 seconds");
        }
        return PO_errorMessage.getText();
    }

    //returns a list of suggestions based on the search text
    public void setSearch_field(String address){
        PO_searchField.clear();
        PO_searchField.sendKeys(address);

        //wait a maximum of 10sec for the suggestions to appear
        try {
            WebDriverWait wait = new WebDriverWait(browser, 10);
            wait.until(ExpectedConditions.visibilityOfAllElements(PO_autoComplete));
        }
        catch (NoSuchElementException e)
        {
            System.out.println("List of suggestions did not appear within 10 seconds");
        }
        PO_searchField.sendKeys(Keys.ESCAPE);
    }

    //returns a list of suggestions based on the search text
    public List<WebElement> getSearchResultsByAddress(String address){
        PO_searchField.clear();
        PO_searchField.sendKeys(address);

        //wait a maximum of 10sec for the suggestions to appear
        try {
            WebDriverWait wait = new WebDriverWait(browser, 10);
            wait.until(ExpectedConditions.visibilityOfAllElements(PO_autoComplete));
        }
        catch (NoSuchElementException e)
        {
            System.out.println("List of suggestions did not appear within 10 seconds");
        }

        return PO_googleAutoComplete;
    }

    public void clearSearchField(){
        PO_searchField.clear();
    }

    public void clickSubmitButton() {
        PO_submitButton.click();
    }

    public void changeLocale(String language){
        switch (language){
            case "English":
                browser.navigate().to(HOMEPAGE_URL+"/en/");
                break;
            case "German":
                browser.navigate().to(HOMEPAGE_URL);
                break;
            case "French":
                browser.navigate().to(HOMEPAGE_URL+"/fr/");
                break;
            case "Dutch":
                browser.navigate().to(HOMEPAGE_URL+"/nl/");
                break;
            case "Polish":
                browser.navigate().to(HOMEPAGE_URL+"/pl/");
                break;
            case "Portuguese":
                browser.navigate().to(HOMEPAGE_URL+"/pt/");
                break;
            case "Romanian":
                browser.navigate().to(HOMEPAGE_URL+"/ro/");
                break;
            case "Bulgarian":
                browser.navigate().to(HOMEPAGE_URL+"/bg/");
                break;
            default:
                browser.navigate().to(HOMEPAGE_URL+"/en/");
        }
    }

    public String getPageTitle() {
        return browser.getTitle();
    }
}

