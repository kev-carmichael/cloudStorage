package com.udacity.jwdnd.course1.cloudstorage.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    public WebDriver driver;

    @FindBy(id = "logoutButton")
    private WebElement logoutButton;

    public HomePage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void clickElement(WebElement element){
        waitUntilElementClickable(element);
        element.click();
    }

    public void waitUntilElementClickable(WebElement element){
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void logout(){
        clickElement(logoutButton);
    }

    //no error msgs req'd?
}
