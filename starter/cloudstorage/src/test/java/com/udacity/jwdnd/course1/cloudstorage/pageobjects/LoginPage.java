package com.udacity.jwdnd.course1.cloudstorage.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    public WebDriver driver;

    private final By inputUsername = By.id("inputUsername");
    private final By inputPassword = By.id("inputPassword");
    private final By submitButton = By.id("submit-button"); //login button
    private final By signupLink = By.id("signup-link");
    private final By errorMsg = By.id("error-msg");
    private final By logoutMsg = By.id("logout-msg");

    /*@FindBy(id = "inputUsername")
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
    private WebElement logoutMsg;*/

    public LoginPage(WebDriver driver){
        //PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    /* public void waitUntilElementVisible(WebElement element){
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
        waitUntilElementVisible(element);
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
    } */

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

    public void waitUntilLoginPageVisible(){
        threadSleepSeconds(2);
        waitUntilElementVisible(inputUsername);
        waitUntilElementVisible(inputPassword);
        waitUntilElementClickable(submitButton);
        waitUntilElementClickable(signupLink);
        threadSleepSeconds(2);
    }

    public void userEnterDetailsAndLogin(String username, String password){
        waitUntilLoginPageVisible();
        inputElementText(inputUsername, username);
        inputElementText(inputPassword, password);
        clickElement(submitButton);
    }

    public boolean errorMsgVisible(){
        waitUntilElementVisible(errorMsg);
        return driver.findElement(errorMsg).isDisplayed();
    }

}