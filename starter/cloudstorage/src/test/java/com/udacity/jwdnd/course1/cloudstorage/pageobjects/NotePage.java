package com.udacity.jwdnd.course1.cloudstorage.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NotePage {

    public WebDriver driver;

    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id = "logoutButton")
    private WebElement logoutButton;

    @FindBy(id = "addANewNoteButton")
    private WebElement addANewNoteButton;

    @FindBy(id = "editNoteButton")
    private WebElement editNoteButton;

    @FindBy(id = "deleteNoteButton")
    private WebElement deleteNoteButton;

    //note title on note page - ASSUMED NOT REQ'D

    //note title on note page - ASSUMED NOT REQ'D

    @FindBy(id = "noteModalLabel") //modal heading
    private WebElement noteModalLabel;

    @FindBy(id = "note-title") //modal note title text
    private WebElement noteTitle;

    @FindBy(id = "note-description") //modal note description text
    private WebElement noteDescription;

    @FindBy(id = "closeButton")
    private WebElement closeButton;

    @FindBy(id = "saveChangesButton")
    private WebElement saveChangesButton;

    @FindBy(id = "saveSuccessMsg")
    private WebElement saveSuccessMsg;

    public NotePage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    //****************used in other test classes - make into abstract methods?***************
    public void waitUntilElementClickable(WebElement element){
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitUntilElementVisible(WebElement element){
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitUntilElementInvisible(WebElement element){
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.invisibilityOf(element));
    }

    //InterruptedException must be caught or declared
    public void threadSleepSeconds(int seconds){
        try{
            Thread.sleep(seconds * 1000);
        } catch(InterruptedException ie){
            }
    }

    public String getElementText(WebElement element){
        return element.getText();
    }

    public void clickElement(WebElement element){
        waitUntilElementClickable(element);
        element.click();
    }

    public void inputElementText(WebElement element, String text){
        waitUntilElementInvisible(element);
        waitUntilElementClickable(element);
        element.clear();
        element.sendKeys(text);
    }
    //**************************************************************************************

    public void addNote(String title, String description){
        //GO TO NOTES TAB
        gotoNotesTab();

        clickElement(addANewNoteButton);
        submitNote(title, description);
        //NOTE SAVED SUCCESS MSG - CLICK TO RETURN TO HOME PAGE
        clickToReturnToHomePage();
        //CLICK ON NOTES TAB
        gotoNotesTab();
        //PROVE ON NOTES TAB
        waitUntilElementClickable(addANewNoteButton);
    }

    public void submitNote(String title, String description){
        waitUntilElementVisible(noteModalLabel); //note pop-up window
        inputElementText(noteTitle, title);
        inputElementText(noteDescription, description);
        clickElement(saveChangesButton); //still on NOtes tab, or automatically go to Home/Files default?
        threadSleepSeconds(2);
        waitUntilElementVisible(saveSuccessMsg);
    }

    public void clickToReturnToHomePage(){
        waitUntilElementVisible(saveSuccessMsg); //MSG SAVED GO TO HOME
        clickElement(saveSuccessMsg); // CLICK TO GO TO HOME
        waitUntilElementInvisible(noteModalLabel); // WAIT TILL NOT IN NOTES TAB
        waitUntilElementClickable(logoutButton); //WAIT UNTIL LOGOUT BUTTON AVAIL - PROVE NOT IN NOTES TAB
        waitUntilElementClickable(navNotesTab); //WAIT UNTIL NOTES TAB BUTTON AVAIL - PROVE NOT IN NOTES TAB
    }

    public void gotoNotesTab(){
        clickElement(navNotesTab);
        waitUntilElementClickable(addANewNoteButton);
    }









}
