package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.pageobjects.*;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	UserService userService;
	@Autowired
	NoteService noteService;

	//WebDriver was made static in lessons code, changed here
	private WebDriver driver;
	private LoginPage loginPage;
	private SignupPage signupPage;
	private HomePage homePage;
	private NotePage notePage;
	private CredentialPage credentialPage;
	private static final String testFirstName = "TestFirstName";
	private static final String testLastName = "TestLastName";
	private static final String testUsername = "TestUsername";
	private static final String testPassword = "TestPassword";
	private static final String editedTestUsername = "EditedTestUsername";
	private static final String editedTestPassword = "EditedTestPassword";
	private static final String testNoteTitle = "TestNoteTitle";
	private static final String testNoteDescription = "TestNoteDescription";
	private static final String editedTestNoteTitle = "EditedTestNoteTitle";
	private static final String editedTestNoteDescription = "EditedTestNoteDescription";
	private static final String testCredentialUrl = "https://www.selenium.dev";
	private static final String editedTestCredentialUrl = "https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/WebDriver.html";

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		loginPage = new LoginPage(driver);
		signupPage = new SignupPage(driver);
		homePage = new HomePage(driver);
		notePage = new NotePage(driver);
		credentialPage = new CredentialPage(driver);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test  // keep this legacy code? is this one of the tests req'd?
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		assertEquals("Login", driver.getTitle());
	}

	//***************  My Tests start from here  **********************
	@Test
	public void _1a_testVerifyUnauthorisedUserOnlyAccessLoginAndSignUp(){
		driver.get("http://localhost:" + this.port + "/login");
		threadSleepSeconds(2);
		assertEquals("Login", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/signup");
		threadSleepSeconds(2);
		assertEquals("Sign Up", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/home");
		threadSleepSeconds(2);
		assertNotEquals("Home", driver.getTitle());
	}

	@Test
	public void _1b_testUserSignupLoginAccessHomePageLogOutAndVerifyHomePageNotAccessible(){
		//USER SIGN UP
		driver.get("http://localhost:" + this.port + "/signup");
		//signupPage.waitUntilSignupPageVisible();
		signupPage.userEnterDetailsAndSignup(testFirstName, testLastName,
											testUsername, testPassword);
		//assertTrue(signupPage.successMsgVisible());
		//USER LOG IN & HOME PAGE ACCESSIBLE
		driver.get("http://localhost:" + this.port + "/login");
		//loginPage.waitUntilLoginPageVisible();
		loginPage.userEnterDetailsAndLogin(testUsername, testPassword);
		//check if stored username and password?
		homePage.waitUntilHomePageVisible();
		assertEquals("Home", driver.getTitle());
		//USER LOG OUT
		homePage.logout();
		loginPage.waitUntilLoginPageVisible();
		assertEquals("Login", driver.getTitle()); //check goes to Login page when press Log Out
		//VERIFY HOME PAGE NO LONGER ACCESSIBLE
		driver.get("http://localhost:" + this.port + "/home");
		threadSleepSeconds(1);
		assertNotEquals("Home", driver.getTitle());
	}

	@Test
	public void _2a_testAddNoteAndVerifyDisplayed(){
		userLoginForTest();
		notePage.addNote(testNoteTitle, testNoteDescription);
		threadSleepSeconds(2);
		assertEquals(testNoteTitle, notePage.getTitleOnNotePage());
		threadSleepSeconds(2);
		assertEquals(testNoteDescription, notePage.getDescriptionOnNotePage());
	}

	@Test
	public void _2b_testEditExistingNoteAndVerifyChangesDisplayed(){
		//LOGIN AND ADD NOTE FIRST
		userLoginForTest();
		notePage.addNote(testNoteTitle, testNoteDescription);
		//EDIT NOTE
		notePage.editNote(editedTestNoteTitle, editedTestNoteDescription);
		//VERIFY IS DISPLAYED
		assertEquals(editedTestNoteTitle, notePage.getTitleOnNotePage());
		assertEquals(editedTestNoteDescription, notePage.getDescriptionOnNotePage());
	}

	@Test
	public void _2c_testDeleteNoteAndVerifyNoteNotDisplayed() {
		User user = addUser();
		userLoginForTest(user);
		notePage.gotoNotesTab();
		notePage.addNote(testNoteTitle, testNoteDescription);
		notePage.logout();
		userLoginForTest(user);
		notePage.gotoNotesTab();
		notePage.deleteNote();
		threadSleepSeconds(2);
		notePage.gotoNotesTab();
		assertEquals(0, notePage.getCountOfNotes());
		assertThrows(NoSuchElementException.class, () -> notePage.getTitleOnNotePage());
	}

	@Test
	public void _3a_testAddCredentialsVerifyDisplayedAndPasswordEncrypted(){
		userLoginForTest();
		credentialPage.addCredential(testCredentialUrl, testUsername, testPassword);
		assertEquals(testCredentialUrl, credentialPage.getUrlOnCredentialPage());
		assertEquals(testUsername, credentialPage.getUsernameOnCredentialPage());
		assertNotEquals(testPassword, credentialPage.getPasswordOnCredentialPage()); //NOT equals as should now be displayed encrypted
		credentialPage.editFirstCredential();
		Credential credentialAttributes = credentialPage.getCredentialAttributes();
		assertEquals(testCredentialUrl, credentialAttributes.getUrl());
		assertEquals(testUsername, credentialAttributes.getUsername());
		assertEquals(testPassword, credentialAttributes.getPassword());
	}

	@Test
	public void _3b_testViewCredentialsVerifyPasswordEncryptedEditVerifyChanges(){
		User user = addUser();
		userLoginForTest(user);
		credentialPage.gotoCredentialsTab();
		credentialPage.addCredential(testCredentialUrl, testUsername, testPassword);
		credentialPage.logout();
		userLoginForTest(user);
		credentialPage.gotoCredentialsTab();
		credentialPage.editCredential(editedTestCredentialUrl, editedTestUsername, editedTestPassword);
		assertEquals(editedTestCredentialUrl, credentialPage.getUrlOnCredentialPage());
		assertEquals(editedTestUsername, credentialPage.getUsernameOnCredentialPage());
		assertNotEquals(editedTestPassword, credentialPage.getPasswordOnCredentialPage());
		credentialPage.editFirstCredential();
		Credential credentialProperties = credentialPage.getCredentialAttributes();
		assertEquals(editedTestCredentialUrl, credentialProperties.getUrl());
		assertEquals(editedTestUsername, credentialProperties.getUsername());
	}

	@Test
	public void _3c_testDeleteCredentialAndVerifyNotDisplayed(){
		User user = addUser();
		userLoginForTest(user);
		credentialPage.gotoCredentialsTab();
		credentialPage.addCredential(testCredentialUrl, testUsername, testPassword);
		credentialPage.logout();
		userLoginForTest(user);
		credentialPage.gotoCredentialsTab();
		credentialPage.deleteCredential();
		assertEquals(0, credentialPage.getCountOfCredentials());
		assertThrows(NoSuchElementException.class, () -> credentialPage.getUrlOnCredentialPage());
	}

	//******** methods to support @Test methods *************************
	public User addUser(){
		/*byte[] array = new byte[8];
		new Random().nextBytes(array);
		String username = new String(array, Charset.forName("UTF-8"));
		String password = new String(array, Charset.forName("UTF-8"));*/
		String username = RandomStringUtils.random(4, true, false);
		String password = RandomStringUtils.random(4, true, true);
		User user = new User();
		user.setUserId(1);
		user.setFirstName(testFirstName);
		user.setLastName(testLastName);
		user.setUsername(username);
		user.setPassword(password);
		userService.createUser(user);
		user.setPassword(password); // req#d again
		return user;
	}

	public void userLoginForTest(User... users){
		User user;
		if(users.length==0){
			user = addUser();
		} else{
			user = users[0];
		}
		driver.get("http://localhost:" + this.port + "/login");
		loginPage.userEnterDetailsAndLogin(user.getUsername(), user.getPassword());
	}

	//InterruptedException must be caught or declared
	public void threadSleepSeconds(int seconds){
		try{
			Thread.sleep(seconds * 1000);
		} catch(InterruptedException ie){
		}
	}
}
