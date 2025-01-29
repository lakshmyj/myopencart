package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage{
	
	public AccountRegistrationPage(WebDriver driver)
	{
		super(driver);
	}

	@FindBy(xpath="//input[@id='input-firstname']") WebElement txtfirstname;
	@FindBy(xpath="//input[@id='input-lastname']") WebElement txtlastName;
	@FindBy(xpath="//input[@id='input-email']") WebElement txtEmail;
	@FindBy(xpath="//input[@id='input-telephone']") WebElement txtTelephone;
	@FindBy(xpath="//input[@id='input-password']") WebElement txtPassword;
	@FindBy(xpath="//input[@id='input-confirm']") WebElement txtConfirmPassword;
	@FindBy(xpath="//input[@value='0']") WebElement radioBtnNewsletterNo;
	@FindBy(xpath="//label[normalize-space()='Yes']/input[@value='1']") WebElement radioBtnNewsletterYes;
	@FindBy(xpath="//input[@name='agree']") WebElement ChkbxPolicy;
	@FindBy(xpath="//input[@value='Continue']") WebElement BtnContinue;
	@FindBy(xpath="//div[@id='content']//h1") WebElement txtRegistrationStatus;
	
	public void setFirstName(String fname)
	{
		txtfirstname.sendKeys(fname);
	}
	public void setLastName(String lname)
	{
		txtlastName.sendKeys(lname);
	}
	public void setEmail(String email) 
	{
		txtEmail.sendKeys(email);
	}
	public void setTelephone(String phoneNumber)
	{
		txtTelephone.sendKeys(phoneNumber);
	}
	public void setPassword(String pword)
	{
		txtPassword.sendKeys(pword);
	}
	public void setConfirmPassword(String confpwrd)
	{
		txtConfirmPassword.sendKeys(confpwrd);
	}
	public void setNewsLetterOption(String option)
	{
		if(option.equals("No"))
			radioBtnNewsletterNo.click();
		else 
			radioBtnNewsletterYes.click();
	}
	public void SetPolicyAgree()
	{
		ChkbxPolicy.click();
	}
	public void clickContinue()
	{
		BtnContinue.click();
	}
	public String getRegistrationStatus()
	{
		try
		{
			return txtRegistrationStatus.getText();
		}
		catch(Exception e)
		{
			return e.getMessage();
		}
	}

}
