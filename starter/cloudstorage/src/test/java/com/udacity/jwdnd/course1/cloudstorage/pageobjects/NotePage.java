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

    /* @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id = "logoutButton")
    private WebElement logoutButton;

    @FindBy(id = "addANewNoteButton")
    private WebElement addANewNoteButton;

    @FindBy(id = "editNoteButton")
    private WebElement editNoteButton;

    @FindBy(id = "deleteNoteButton")
    private WebElement deleteNoteButton; */

    private final By navNotesTab = By.id("nav-notes-tab");
    private final By logoutButton = By.id("logoutButton");
    private final By addANewNoteButton = By.id("addANewNoteButton");
    private final By editNoteButton = By.id("editNoteButton");
    private final By deleteNoteButton = By.id("deleteNoteButton");

    private final By mainNoteTitle = By.cssSelector("#notesTable tbody tr td:nth-of-type(2)"); //main note page title text


    private final By mainNoteDescription = By.cssSelector("#notesTable tbody tr td:nth-of-type(3)"); //main note page description text

    private final By noteModalLabel = By.id("noteModalLabel"); //modal heading
    private final By noteTitle = By.id("note-title"); //modal note title text
    private final By noteDescription = By.id("note-description"); //modal note description text
    private final By closeButton = By.id("closeButton");
    private final By saveChangesButton = By.id("saveChangesButton");
    private final By saveSuccessMsg = By.id("saveSuccessMsg");

    /* @FindBy(id = "noteModalLabel") //modal heading
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
    private WebElement saveSuccessMsg; */

    public NotePage(WebDriver driver){
        //PageFactory.initElements(driver, this);
        this.driver = driver;
    }

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

    public String getElementText(By element){
        return driver.findElement(element).getText();
    }

    public void addNote(String title, String description){
        gotoNotesTab(); //GO TO NOTES TAB
        clickElement(addANewNoteButton);
        inputAndSaveNote(title, description);
        clickToReturnToHomePage(); //NOTE SAVED SUCCESS MSG - CLICK TO RETURN TO HOME PAGE
        gotoNotesTab(); //GO TO NOTES TAB
        waitUntilElementClickable(addANewNoteButton); //VERIFY ON NOTES TAB
    }

    public void inputAndSaveNote(String title, String description){
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
        threadSleepSeconds(2);
        waitUntilElementInvisible(noteModalLabel); // WAIT TILL NOT IN NOTES TAB
        waitUntilElementClickable(logoutButton); //WAIT UNTIL LOGOUT BUTTON AVAIL - PROVE NOT IN NOTES TAB
        waitUntilElementClickable(navNotesTab); //WAIT UNTIL NOTES TAB BUTTON AVAIL - PROVE NOT IN NOTES TAB
    }

    public void gotoNotesTab(){
        clickElement(navNotesTab);
        waitUntilElementClickable(addANewNoteButton);
    }

    public void editNote(String title, String description){
        // need gotoNotesTab()??
        clickElement(editNoteButton);
        inputAndSaveNote(title, description);
        clickToReturnToHomePage(); //NOTE SAVED SUCCESS MSG - CLICK TO RETURN TO HOME PAGE
        gotoNotesTab(); //GO TO NOTES TAB
        waitUntilElementClickable(addANewNoteButton); //VERIFY ON NOTES TAB
    }

    public void deleteNote(){
        clickElement(deleteNoteButton);
        waitUntilElementInvisible(addANewNoteButton);
        threadSleepSeconds(2);
        clickToReturnToHomePage();
        gotoNotesTab();
        waitUntilElementClickable(addANewNoteButton); //VERIFY ON NOTES TAB
    }

    public String getTitleOnNotePage(){ //FROM MAIN NOTE PAGE; NOT MODAL
        return getElementText(mainNoteTitle);
    }

    public String getDescriptionOnNotePage(){ //FROM MAIN NOTE PAGE; NOT MODAL
        return getElementText(mainNoteDescription);
    }

    public int getCountOfNotes(){
        return driver.findElements(mainNoteTitle).size();
    }

    //test requiring close button clicked not required

    public void logout(){  //visible on NOtes Page
        waitUntilElementClickable(logoutButton);
        clickElement(logoutButton);
    }
}