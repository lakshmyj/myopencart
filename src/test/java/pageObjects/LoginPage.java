package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{

	public LoginPage(WebDriver driver)
	{
		super(driver);
	}
	
	@FindBy(xpath="//input[@id='input-email']") WebElement txtusername;
	@FindBy(xpath="//input[@id='input-password']") WebElement txtpassword;
	@FindBy(xpath="//input[@value='Login']") WebElement btnlogin;
	
	public void setUserName(String uname)
	{
		txtusername.sendKeys(uname);
	}
	
	public void setPassword(String pword)
	{
		txtpassword.sendKeys(pword);
	}
	
	public void clickLoginButton()
	{
		btnlogin.click();
	}
}
