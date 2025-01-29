package testCases;

import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDataDrivenTest extends BaseClass{

	/*
	 * Valid data - login successful - Test Passed - logout
	 * Valid data - login unsuccessful - Test Failed
	 * 
	 * Invalid data - login successful - Test Failed
	 * Invalid data - login unsuccessful - Test Passed - logout
	 * */

	@Test(dataProvider = "LoginData",dataProviderClass=DataProviders.class,groups= {"master"})
	public void loginDDT(String uname,String pwd, String expectedResult)
	{
		try
		{
			logger.info(browserName+" ***Starting loginDDT***");
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.ClickLoginLink();

			LoginPage lp = new LoginPage(driver);
			logger.info(browserName+" Trying new data for testing login "+uname+" "+pwd+" "+expectedResult);
			lp.setUserName(uname);
			lp.setPassword(pwd);
			lp.clickLoginButton();

			MyAccountPage myacc = new MyAccountPage(driver);
			boolean targetPage = false;
			try
			{
			targetPage = myacc.isMyAccountHeadingDisplayed();
			}
			catch(NoSuchElementException e)
			{
				targetPage = false;
				logger.error(browserName+" 'myAccountPageHeader' not found");
				e.getMessage();
			}

			if(expectedResult.equalsIgnoreCase("valid"))
			{
				if(targetPage)
				{
					//Valid data - login successful - Test Passed - logout
					logger.info(browserName+" Provided valid credentials and test has passed");
					myacc.clickLogout();
					logger.info(browserName+" Logging out..");
					logger.info(browserName+" ***Finished TC001_AccountRegistrationTest***");
					Assert.assertTrue(true);
				}
				else
				{
					//Valid data - login unsuccessful - Test Failed
					logger.info(browserName+" Provided valid credentials but login failed... test failed");
					logger.info(browserName+" ***Finished TC001_AccountRegistrationTest***");
					Assert.fail();
				}
			}
			else if(expectedResult.equalsIgnoreCase("invalid"))
			{
				if(targetPage)
				{
					//Invalid data - login successful - Test fail - logout
					logger.info(browserName+" Provided invalid credentials but login successful..test failed");
					myacc.clickLogout();
					logger.info(browserName+" Logging out..");
					logger.info(browserName+" ***Finished TC001_AccountRegistrationTest***");
					Assert.fail();

				}
				else
				{
					//Invalid data - login unsuccessful - Test Passed 
					logger.info(browserName+" Provided invalid credentials and test has passed");
					logger.info(browserName+" ***Finished TC001_AccountRegistrationTest***");
					Assert.assertTrue(true);
				}
			}

		}
		catch(Exception e)
		{
			logger.info(browserName+" "+e.getMessage());
		}
	}
}
