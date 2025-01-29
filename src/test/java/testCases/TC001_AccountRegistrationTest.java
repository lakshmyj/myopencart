package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass{
	@Test(groups= {"regression","master"})
	public void registrationTest()
	{
		try
		{
		logger.info(browserName+" ***Starting TC001_AccountRegistrationTest***");
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		hp.ClickRegisterLink();
		logger.info(browserName+" Clicked registration page..");
		
		AccountRegistrationPage arp = new AccountRegistrationPage(driver);
		logger.info(browserName+" Providing customer details for registration..");
		//arp.setFirstName("John");
		arp.setFirstName(generateRandomString());
		//arp.setLastName("Doe");
		arp.setLastName(generateRandomString());
		//arp.setEmail("abcefg@gmail.com");
		arp.setEmail(generateRandomString()+"@gmail.com");
		//arp.setTelephone("9876567598");
		arp.setTelephone(generateRandomNumber());
		//arp.setPassword("xyz123");
		//arp.setConfirmPassword("xyz123");
		String passwordData = generateRandomAlphaNumeric(); 
		arp.setPassword(passwordData);
		arp.setConfirmPassword(passwordData);
		//arp.setConfirmPassword(passwordData+"wrong");
		arp.setNewsLetterOption("No");
		arp.SetPolicyAgree();
		arp.clickContinue();
		String confMessage = arp.getRegistrationStatus();
		logger.info(browserName+" Asserting confirmation message..");
		//Assert.assertEquals(confMessage,"Your Account Has Been Created!");
		if(confMessage.equals("Your Account Has Been Created!"))
		{
			logger.info(browserName+" Test Passed");
			logger.info(browserName+" ***Finished TC001_AccountRegistrationTest***");
			Assert.assertTrue(true);
		}
		else
		{
			logger.error("Test Failed");
			logger.info("***Finished TC001_AccountRegistrationTest***");
			Assert.fail();
		}
		}
		catch(Exception e)
		{
			//logger.error("Assertion Failed...Test Failed..");
			//logger.info("Assertion Failed...Test Failed..");
			//logger.debug("Debug logs..");
			logger.error(browserName+"Exception Occured"+e.getMessage());
			//Assert.fail();
		}
	}
	
}
