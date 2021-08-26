package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pageobjects.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pageobjects.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pageobjects.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private static WebDriver driver; //made static, as per lessons code
	private LoginPage loginPage;
	private SignupPage signupPage;
	private HomePage homePage;
	//add addn Page Objects as fields here

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test  // keep this - is this one of the tests req'd?
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
		assertEquals("Home", driver.getTitle());
	}

	@Test
	public void _1b_testUserSignupLoginAccessHomePageLogOutAndVerifyHomePageNotAccessible(){
		//SET USER VALUES
		String firstName = "John";
		String lastName = "Smith";
		String username = "jsmith";
		String password = "123";

		//USER SIGN IN
		driver.get("http://localhost:" + this.port + "/signup");
		signupPage = new SignupPage(driver);  //removed redeclaration of SignupPage
		signupPage.signup(firstName, lastName, username, password);

		//USER LOG IN
		driver.get("http://localhost:" + this.port + "/login");
		loginPage = new LoginPage(driver); //removed redeclaration of LoginPage
		loginPage.login(username, password);

		//VERIFY HOME PAGE ACCESSIBLE
		driver.get("http://localhost:" + this.port + "/home");
		assertEquals("Home", driver.getTitle());

		//USER LOG OUT
		homePage = new HomePage(driver); //removed redeclaration of HomePage
		homePage.logout();
		assertEquals("Login", driver.getTitle()); //goes to Login page when press Log Out

		//VERIFY HOME PAGE NO LONGER ACCESSIBLE
		driver.get("http://localhost" + this.port + "/home");
		assertNotEquals("Home", driver.getTitle());
	}



}
