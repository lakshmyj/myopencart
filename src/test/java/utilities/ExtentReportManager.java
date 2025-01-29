package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener{

	public ExtentSparkReporter  sparkReporter;
	public ExtentReports extentReport;
	public ExtentTest extentTest;
	String reportName;
	
	public void onStart(ITestContext testContext)
	{
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		reportName = "Test-Report-"+timeStamp+".html";
		sparkReporter = new ExtentSparkReporter(".\\reports\\"+reportName);
		
		sparkReporter.config().setDocumentTitle("Opencart Automation Report");
		sparkReporter.config().setReportName("Opencart Functional Testing");
		sparkReporter.config().setTheme(Theme.STANDARD);
		
		extentReport = new ExtentReports();
		extentReport.attachReporter(sparkReporter);
		extentReport.setSystemInfo("Application","openCart");
		extentReport.setSystemInfo("Module","Admin");
		extentReport.setSystemInfo("Sub-Module","Customer");
		extentReport.setSystemInfo("User Name",System.getProperty("user.name"));
		extentReport.setSystemInfo("Environment","QA");
		extentReport.setSystemInfo("Operating System",testContext.getCurrentXmlTest().getParameter("OS"));
		extentReport.setSystemInfo("Browser",testContext.getCurrentXmlTest().getParameter("browser"));
		List<String> includedTestGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		if(!includedTestGroups.isEmpty())
			extentReport.setSystemInfo("Test Groups",includedTestGroups.toString());
	}
	
	public void onTestSuccess(ITestResult result) 
	{
		extentTest = extentReport.createTest(result.getTestClass().getName());
		extentTest.assignCategory(result.getMethod().getGroups());
		extentTest.log(Status.PASS,result.getName()+" got successfully executed");
	}
	
	public void onTestFailure(ITestResult result)
	{
		extentTest = extentReport.createTest(result.getClass().getName());
		extentTest.assignCategory(result.getMethod().getGroups());
		extentTest.log(Status.FAIL,result.getName()+" got failed");
		extentTest.log(Status.INFO,result.getThrowable().getMessage());
		try
		{
			String imgPath = new BaseClass().captureScreen(result.getName());
			extentTest.addScreenCaptureFromPath(imgPath);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void onTestSkip(ITestResult result)
	{
		extentTest=extentReport.createTest(result.getClass().getName());
		extentTest.assignCategory(result.getMethod().getGroups());
		extentTest.log(Status.SKIP,result.getName()+" got skipped");
		extentTest.log(Status.INFO,result.getThrowable().getMessage());
	}
	public void onFinish(ITestContext testContext)
	{
		extentReport.flush();
		/*To open the report automatically in the browser--Start*/
		String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\"+reportName;
		File extentRep = new File(pathOfExtentReport);
		try
		{
			Desktop.getDesktop().browse(extentRep.toURI());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		/*To open the report automatically in the browser--End*/
		
		/*Send the report as email as soon as it is generated --start*/
//		try
//		{
//			URL url = new URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+reportName);
//			//Create email message
//			ImageHtmlEmail email = new ImageHtmlEmail();
//			email.setDataSourceResolver(new DataSourceUrlResolver(url));
//			email.setHostName("smtp.googlemail.com");
//			email.setSmtpPort(465);
//			email.setAuthenticator(new DefaultAuthenticator("lakshmyj@gmail.com","password"));
//			email.setSSLOnConnect(true);
//			email.setFrom("lakshmyj@gmail.com"); //sender
//			email.setSubject("Test Results");
//			email.setMsg("Please find attached report...");
//			email.addTo("lakshmyj@gmail.com"); //receiver
//			email.attach(url,"extent report","please check report...");
//			email.send(); //send the email
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			
//		}
		/*Send the report as email as soon as it is generated --end*/
		
	}
	
}
