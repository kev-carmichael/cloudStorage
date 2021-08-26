package com.udacity.jwdnd.course1.cloudstorage.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

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

    //from lessons  *******************************************************
    public void signup(String firstName, String lastName, String username, String password) {
        this.inputFirstName.sendKeys(firstName);
        this.inputLastName.sendKeys(lastName);
        this.inputUsername.sendKeys(username);
        this.inputPassword.sendKeys(password);
        this.submitButton.click();
    }


    //first try methods *****************************************
    public void gotoLoginPage(){
        loginLink.click();
    }

    public String getDisplayedFirstName(){
        return inputFirstName.getText();
    }

    public String getDisplayedLastName(){
        return inputLastName.getText();
    }

    public String getDisplayedUsername(){
        return inputUsername.getText();
    }

    public String getDisplayedPassword(){
        return inputPassword.getText();
    }

    public void signupButton(){
        submitButton.click();
    }

    public String getDisplayedSuccessMsg(){
        return successMsg.getText();
    }

    public String getDisplayedErrorMsg(){
        return errorMsg.getText();
    }

}
