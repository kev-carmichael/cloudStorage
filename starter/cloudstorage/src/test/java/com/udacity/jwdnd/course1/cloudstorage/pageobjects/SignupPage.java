package com.udacity.jwdnd.course1.cloudstorage.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignupPage {

    public WebDriver driver;

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
    private WebElement errorMsg;

    public SignupPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    //****************used in other test classes - make into abstract methods?***************
    public void waitUntilElementVisible(WebElement element){
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

    //InterruptedException must be caught or declared
    public void threadSleepSeconds(int seconds){
        try{
            Thread.sleep(seconds * 1000);
        } catch(InterruptedException ie){
        }
    }
    //**************************************************************************************

    public void waitUntilSignupPageVisible(){
        waitUntilElementVisible(inputFirstName);
        waitUntilElementVisible(inputLastName);
        waitUntilElementVisible(inputUsername);
        waitUntilElementVisible(inputPassword);
        waitUntilElementClickable(submitButton);
        threadSleepSeconds(2);
    }

    public void userEnterDetailsAndSignup(String firstName, String lastName,
                                          String username, String password){
        waitUntilSignupPageVisible();
        inputElementText(inputFirstName, firstName);
        inputElementText(inputLastName, lastName);
        inputElementText(inputUsername, username);
        inputElementText(inputPassword, password);
        clickElement(submitButton);
    }

    public boolean successMsgVisible(){
        waitUntilElementVisible(successMsg);
        return successMsg.isDisplayed();
    }

    public boolean errorMsgVisible(){
        waitUntilElementVisible(errorMsg);
        return errorMsg.isDisplayed();
    }
}
