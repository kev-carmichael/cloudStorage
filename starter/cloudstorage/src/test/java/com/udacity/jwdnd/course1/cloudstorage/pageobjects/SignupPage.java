package com.udacity.jwdnd.course1.cloudstorage.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignupPage {

    public WebDriver driver;

    private final By loginLink = By.id("loginLink");
    private final By inputFirstName = By.id("inputFirstName");
    private final By inputLastName = By.id("inputLastName");
    private final By inputUsername = By.id("inputUsername");
    private final By inputPassword = By.id("inputPassword");
    private final By submitButton = By.id("submit-button");
    private final By successMsg = By.id("success-msg");
    private final By errorMsg = By.id("error-msg");

    /*
    @FindBy(id = "loginLink")
    private WebElement loginLink;

    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "submit-button")  //this is Sign Up button
    private WebElement submitButton;

    @FindBy(id = "success-msg")
    private WebElement successMsg;

    @FindBy(id = "error-msg")
    private WebElement errorMsg; */

    public SignupPage(WebDriver driver){
        //PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    /*public void waitUntilElementVisible(WebElement element){
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOf(element));
    }
        public void waitUntilElementInvisible(WebElement element){
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.invisibilityOf(element));
    }
        public void waitUntilElementClickable(WebElement element){
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(element));
    }
        public void inputElementText(WebElement element, String text){
        waitUntilElementInvisible(element);
        waitUntilElementClickable(element);
        element.clear();
        element.sendKeys(text);
    }
        public void clickElement(WebElement element){
        waitUntilElementClickable(element);
        element.click();
    }
*/

    //****************used in other test classes - make into abstract methods?***************
    public void waitUntilElementVisible(By element){
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    public void waitUntilElementInvisible(By element){
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(element));
    }

    public void waitUntilElementClickable(By element){
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void inputElementText(By element, String text){
        waitUntilElementVisible(element);
        waitUntilElementClickable(element);
        driver.findElement(element).clear();
        driver.findElement(element).sendKeys(text);
    }

    public void clickElement(By element){
        waitUntilElementClickable(element);
        driver.findElement(element).click();
    }

    //InterruptedException must be caught or declared
    public void threadSleepSeconds(int seconds){
        try{
            Thread.sleep(seconds * 1000);
        } catch(InterruptedException ie){
        }
    }
    //**************************************************************************************

    public void waitUntilSignupPageVisible(){
        threadSleepSeconds(2);
        waitUntilElementVisible(inputFirstName);
        waitUntilElementVisible(inputLastName);
        waitUntilElementVisible(inputUsername);
        waitUntilElementVisible(inputPassword);
        waitUntilElementClickable(submitButton);
        threadSleepSeconds(2);
    }

    public void userEnterDetailsAndSignup(String firstName, String lastName,
                                          String username, String password){
        //waitUntilSignupPageVisible();
        inputElementText(inputFirstName, firstName);
        inputElementText(inputLastName, lastName);
        inputElementText(inputUsername, username);
        inputElementText(inputPassword, password);
        clickElement(submitButton);
    }

    /*public boolean successMsgVisible(){
        waitUntilElementVisible(successMsg);
        return successMsg.isDisplayed();
    }*/

    public boolean successMsgVisible(){
        waitUntilElementVisible(successMsg);
        return driver.findElement(successMsg).isDisplayed();
    }

    /*public boolean errorMsgVisible(){
        waitUntilElementVisible(errorMsg);
        return errorMsg.isDisplayed();
    }*/

    public boolean errorMsgVisible(){
        waitUntilElementVisible(errorMsg);
        return driver.findElement(errorMsg).isDisplayed();
    }
}
