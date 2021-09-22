package com.udacity.jwdnd.course1.cloudstorage.pageobjects;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CredentialPage {

    public WebDriver driver;

    private final By navCredentialsTab = By.id("nav-credentials-tab");
    private final By navNotesTab = By.id("nav-notes-tab");
    private final By logoutButton = By.id("logoutButton");
    private final By addANewCredentialButton = By.id("addANewCredentialButton");
    private final By editCredentialButton = By.id("editCredentialButton");
    private final By deleteCredentialButton = By.id("deleteCredentialButton");
    private final By credentialUrl = By.id("credentialUrl"); //in cred page table
    private final By credentialUsername = By.id("credentialUsername"); //in cred page table
    private final By credentialPassword = By.id("credentialPassword"); //in cred page table
    private final By credentialModalLabel = By.id("credentialModalLabel");
    private final By inputCredentialUrl = By.id("credential-url"); //in modal
    private final By inputCredentialUser = By.id("credential-username"); //in modal
    private final By inputCredentialPassword = By.id("credential-password"); //in modal
    private final By saveCredentialButton = By.id("saveCredentialButton");
    private final By saveSuccessMsg = By.id("saveSuccessMsg");

    public CredentialPage(WebDriver driver){
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

    public void addCredential(String url, String username, String password){
        gotoCredentialsTab(); //GO TO CREDENTIALS TAB
        clickElement(addANewCredentialButton);
        inputAndSaveCredential(url, username, password);
        clickToReturnToHomePage(); //CREDENTIAL SAVED SUCCESS MSG - CLICK TO RETURN TO HOME PAGE
        gotoCredentialsTab(); //GO TO CREDENTIALS TAB
        waitUntilElementClickable(addANewCredentialButton); //VERIFY ON CREDENTIALS TAB
    }

    public void editCredential(String url, String username, String password){
        clickElement(editCredentialButton);
        inputAndSaveCredential(url, username, password);
        clickToReturnToHomePage(); //CRED SAVED SUCCESS MSG - CLICK TO RETURN TO HOME PAGE
        gotoCredentialsTab(); //GO TO NOTES TAB
        waitUntilElementClickable(addANewCredentialButton); //VERIFY ON CRED TAB
    }
    //*****************************************************************************
    //**need method here to verify password encrypted or in main test app driver?**
    //*****************************************************************************
    public void deleteNote(){
        clickElement(deleteCredentialButton);
        waitUntilElementInvisible(addANewCredentialButton);
        threadSleepSeconds(2);
        clickToReturnToHomePage();
        gotoCredentialsTab();
        waitUntilElementClickable(addANewCredentialButton); //VERIFY ON cred TAB
    }

    public String getUrlOnCredentialPage(){ //FROM MAIN NOTE PAGE; NOT MODAL
        return getElementText(credentialUrl);
    }

    public String getUsernameOnCredentialPage(){ //FROM MAIN NOTE PAGE; NOT MODAL
        return getElementText(credentialUsername);
    }

    public String getPasswordOnCredentialPage(){ //FROM MAIN NOTE PAGE; NOT MODAL
        return getElementText(credentialPassword);
    }

    public int getCountOfNotes(){
        return driver.findElements(credentialUrl).size();
    }

    public void gotoCredentialsTab(){
        clickElement(navCredentialsTab);
        waitUntilElementClickable(addANewCredentialButton);
    }

    public void inputAndSaveCredential(String url, String username, String password){
        waitUntilElementVisible(credentialModalLabel); //credential pop-up window
        inputElementText(credentialUrl, url);
        inputElementText(credentialUsername, username);
        inputElementText(credentialPassword, password);
        clickElement(saveCredentialButton);
        threadSleepSeconds(2);
        waitUntilElementVisible(saveSuccessMsg);
    }

    public void clickToReturnToHomePage(){
        waitUntilElementVisible(saveSuccessMsg); //MSG SAVED GO TO HOME
        clickElement(saveSuccessMsg); // CLICK TO GO TO HOME
        threadSleepSeconds(2);
        waitUntilElementInvisible(credentialModalLabel); // WAIT TILL NOT IN cred TAB
        waitUntilElementClickable(logoutButton); //WAIT UNTIL LOGOUT BUTTON AVAIL - PROVE NOT IN cred TAB
        waitUntilElementClickable(navNotesTab); //WAIT UNTIL NOTES TAB BUTTON AVAIL - PROVE NOT IN cred TAB
    }

    public void editFirstCredential() {
        clickElement(editCredentialButton);
        waitUntilElementVisible(credentialModalLabel);
    }

    public Credential getCredentialProperties() {
        Credential credential = new Credential();
        credential.setUrl(driver.findElement(inputCredentialUrl).getAttribute("value"));
        credential.setUsername(driver.findElement(inputCredentialUser).getAttribute("value"));
        credential.setPassword(driver.findElement(inputCredentialPassword).getAttribute("value"));
        return credential;
    }

    public void logout(){  //visible on CRED Page
        waitUntilElementClickable(logoutButton);
        clickElement(logoutButton);
    }
}
