package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.pageobjects.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pageobjects.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pageobjects.NotePage;
import com.udacity.jwdnd.course1.cloudstorage.pageobjects.SignupPage;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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

	UserService userService;
	NoteService noteService;

	private static WebDriver driver; //made static, as per lessons code
	private LoginPage loginPage;
	private SignupPage signupPage;
	private HomePage homePage;
	private NotePage notePage;
	private CredentialPage credentialPage;
	private static final String testFirstName = "TestFirstName";
	private static final String testLastName = "TestLastName";
	private static final String testUsername = "TestUsername";
	private static final String testPassword = "TestPassword";
	private static final String editedUsername = "EditedUsername";
	private static final String editedPassword = "EditedPassword";
	private static final String testNoteTitle = "TestNoteTitle";
	private static final String testNoteDescription = "TestNoteDescription";
	private static final String editedNoteTitle = "EditedNoteTitle";
	private static final String editedNoteDescription = "EditedNoteDescription";
	private static final String testCredentialUrl = "https://www.selenium.dev";
	private static final String editedCredentialUrl = "https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/WebDriver.html";


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
		driver.get("http://localhost" + this.port + "/login");
		assertEquals("Login", driver.getTitle());

		driver.get("http://localhost" + this.port + "/signup");
		assertEquals("Sign Up", driver.getTitle());

		driver.get("http://localhost" + this.port + "/home");
		assertNotEquals("Home", driver.getTitle());
	}

	@Test
	public void _1b_testUserSignupLoginAccessHomePageLogOutAndVerifyHomePageNotAccessible(){
		//USER SIGN UP
		driver.get("http://localhost:" + this.port + "/signup");
		signupPage.waitUntilSignupPageVisible();
		signupPage.userEnterDetailsAndSignup(testFirstName, testLastName,
											testUsername, testPassword);
		assertTrue(signupPage.successMsgVisible());

		//USER LOG IN & HOME PAGE ACCESSIBLE
		driver.get("http://localhost:" + this.port + "/login");
		loginPage.waitUntilLoginPageVisible();
		loginPage.userEnterDetailsAndLogin(testUsername, testPassword);
		//check if stored username and password?
		assertEquals("Home", driver.getTitle());

		//USER LOG OUT
		homePage.logout();
		loginPage.waitUntilLoginPageVisible();
		assertEquals("Login", driver.getTitle()); //check goes to Login page when press Log Out

		//VERIFY HOME PAGE NO LONGER ACCESSIBLE
		driver.get("http://localhost" + this.port + "/home");
		assertNotEquals("Home", driver.getTitle());
	}

	@Test
	public void _2a_testAddNoteAndVerifyDisplayed(){
		userLoginForTest();
		notePage.addNote(testNoteTitle, testNoteDescription);
		assertEquals(testNoteTitle, notePage.getTitleOnNotePage());
		assertEquals(testNoteDescription, notePage.getDescriptionOnNotePage());
	}

	@Test
	public void _2b_testEditExistingNoteAndVerifyChangesDisplayed(){
		//LOGIN AND ADD NOTE FIRST
		userLoginForTest();
		notePage.addNote(testNoteTitle, testNoteDescription);
		//EDIT NOTE
		notePage.editNote(editedNoteTitle, editedNoteDescription);
		//VERIFY IS DISPLAYED
		assertEquals(editedNoteTitle, notePage.getTitleOnNotePage());
		assertEquals(editedNoteDescription, notePage.getDescriptionOnNotePage());
	}

	@Test
	public void _2c_testDeleteNoteAndVerifyNoteNotDisplayed(){
		//LOGIN AND ADD NOTE FIRST
		userLoginForTest();
		notePage.addNote(testNoteTitle, testNoteDescription);
		//DELETE NOTE
		notePage.deleteNote();
		//VERIFY IS NOT DISPLAYED
		assertNotEquals(testNoteTitle, notePage.getTitleOnNotePage());
		assertNotEquals(testNoteDescription, notePage.getDescriptionOnNotePage());
		//will this throw exception?
		// or could check count of notes == 0
	}

	@Test
	public void _3a_testAddCredentialsVerifyDisplayedAndPasswordEncrypted(){
		
	}

	//******** methods to support @Test methods *************************
	public User addUser(){
		byte[] array = new byte[8];
		new Random().nextBytes(array);
		String username = new String(array, Charset.forName("UTF-8"));
		String password = new String(array, Charset.forName("UTF-8"));
		User user = new User();
		user.setUserId(1);
		user.setFirstName(testFirstName);
		user.setLastName(testLastName);
		user.setUsername(username);
		user.setPassword(password);
		userService.createUser(user);
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



}
