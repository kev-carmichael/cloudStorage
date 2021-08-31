package com.udacity.jwdnd.course1.cloudstorage.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    public WebDriver driver;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "submit-button")  //login button
    private WebElement submitButton;

    @FindBy(id = "signup-link")
    private WebElement signupLink;

    @FindBy(id = "error-msg")
    private WebElement errorMsg;

    @FindBy(id = "logout-msg")
    private WebElement logoutMsg;

    public LoginPage(WebDriver driver){
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
    //**************************************************************************************

    public void waitUntilLoginPageVisible(){
        waitUntilElementVisible(inputUsername);
        waitUntilElementVisible(inputPassword);
        waitUntilElementClickable(submitButton);
        waitUntilElementClickable(signupLink);
    }

    public void userEnterDetailsAndLogin(String username, String password){
        waitUntilLoginPageVisible();
        inputElementText(inputUsername, username);
        inputElementText(inputPassword, password);
        clickElement(submitButton);
    }

    public boolean errorMsgVisible(){
        waitUntilElementVisible(errorMsg);
        return errorMsg.isDisplayed();
    }
}