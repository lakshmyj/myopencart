package testCases;

import java.io.FileReader;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass{
	@Test(groups= {"sanity","master"})
	public void verifyLogin()
	{
		try
		{
			logger.info(browserName+" ***Starting TC002_LoginTest***");
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.ClickLoginLink();
			logger.info(browserName+" Clicked for login page..");

			try
			{
				//Loading config.properties file
				FileReader file = new FileReader("./src//test//resources//config.properties");
				propFile= new Properties();
				propFile.load(file);

			}
			catch(Exception e)
			{
				logger.error(browserName+e.getMessage());
			}

			LoginPage lp = new LoginPage(driver);
			lp.setUserName(propFile.getProperty("userName"));
			lp.setPassword(propFile.getProperty("password"));
			lp.clickLoginButton();
			logger.info(browserName+" Clicked login button");

			MyAccountPage myacc = new MyAccountPage(driver);
			boolean loginStatus = myacc.isMyAccountHeadingDisplayed();
			if(loginStatus==true) 
			{
				logger.info(browserName+" Test Passed");
				logger.info(browserName+" ***Finished TC001_AccountRegistrationTest***");
				Assert.assertTrue(true);
			}
			else
			{
				logger.info(browserName+" Test Failed");
				logger.info(browserName+" ***Finished TC001_AccountRegistrationTest***");
				Assert.fail();
			}
		}
		catch(Exception e)
		{
			logger.error(browserName+"Exception "+e.getMessage());
			Assert.fail();
		}
	}

}
