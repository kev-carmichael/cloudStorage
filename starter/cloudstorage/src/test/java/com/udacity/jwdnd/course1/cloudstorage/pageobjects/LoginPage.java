package com.udacity.jwdnd.course1.cloudstorage.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "submit-button")
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

    public String getDisplayedUsername(){
        return inputUsername.getText();
    }

    public String getDisplayedPassword(){
        return inputPassword.getText();
    }

    public void loginButton(){
        submitButton.click();
    }

    public void gotoSignupPage(){
        signupLink.click();
    }

    public String getDisplayedErrorMsg(){
        return errorMsg.getText();
    }

    public String getDisplayedLogoutMsg(){
        return logoutMsg.getText();
    }

}
