package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage{

	public MyAccountPage(WebDriver driver)
	{
		super(driver);
	}
	
	@FindBy(xpath="//div[@id='content']/h2[1]") WebElement headingMyAccount; 
	@FindBy(xpath="//div[@class='list-group']//a[text()='Logout']") WebElement btnLogout; 
	
	public boolean isMyAccountHeadingDisplayed()
	{
		try
		{
			return headingMyAccount.isDisplayed();
		}
		catch(Exception e)
		{
			return headingMyAccount.isDisplayed();
		}
	}
	
	public void clickLogout()
	{
		btnLogout.click();
	}
}
