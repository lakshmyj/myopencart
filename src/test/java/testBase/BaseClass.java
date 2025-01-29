package testBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {

	public static WebDriver driver;
	public Logger logger;
	public String browserName;
	public Properties propFile;

	@BeforeClass(groups = { "regression", "sanity", "master" })
	@Parameters({ "OS", "browser" })
	public void setup(String OS, String browser) {
		logger = LogManager.getLogger(this.getClass());

		try {
			// Loading config.properties file
			FileReader file = new FileReader("./src//test//resources//config.properties");
			propFile = new Properties();
			propFile.load(file);

		} catch (Exception e) {
			logger.error(browserName + e.getMessage());
		}

		if (propFile.getProperty("execution_env").equalsIgnoreCase("remote")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			if (OS.equalsIgnoreCase("Windows")) {
				capabilities.setPlatform(Platform.WIN11);
			} else if (OS.equalsIgnoreCase("Mac")) {
				capabilities.setPlatform(Platform.MAC);
			} else if (OS.equalsIgnoreCase("Linux")) {
				capabilities.setPlatform(Platform.LINUX);
			}else {
				System.out.println("No matching OS");
				return;
			}
			switch (browser.toLowerCase()) {
			case "chrome":
				capabilities.setBrowserName("chrome");
				break;
			case "firefox":
				capabilities.setBrowserName("firefox");
				break;
			case "edge":
				capabilities.setBrowserName("MicrosoftEdge");
				break;
			default:
				System.out.println("No matching browser");
				break;
			}

			try
			{
			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
			//driver = new RemoteWebDriver(new URL("http://http://192.168.1.71:4444/wd/hub"), capabilities);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		if (propFile.getProperty("execution_env").equalsIgnoreCase("local")) {
			switch (browser.toLowerCase()) {
			case "chrome":
				driver = new ChromeDriver();
				logger.info("Running in Chrome Browser...");
				browserName = "Chrome";
				break;
			case "firefox":
				driver = new FirefoxDriver();
				logger.info("Running in Firefox Browser...");
				browserName = "Firefox";
				break;
			case "edge":
				driver = new EdgeDriver();
				logger.info("Running in Edge Browser...");
				browserName = "MicrosoftEdge";
				break;
			default:
				logger.error("Invalid Browser parameter");
				return;
			}
		}

		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(propFile.getProperty("appURL"));
		driver.manage().window().maximize();
		logger.info("Executing url " + propFile.getProperty("appURL1"));
		logger.info(browserName + " Reading properties from config.properties");
	}

	@AfterClass(groups = { "regression", "sanity", "master" })
	public void tearDown() {
		if(driver!=null)
			driver.quit();
	}

	public String generateRandomString() {
		return RandomStringUtils.randomAlphabetic(5);
	}

	public String generateRandomNumber() {
		return RandomStringUtils.randomNumeric(10);
	}

	public String generateRandomAlphaNumeric() {
		return RandomStringUtils.randomAlphabetic(3) + "#" + RandomStringUtils.randomNumeric(3);
	}

	public String captureScreen(String methodName) {
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot screenShot = (TakesScreenshot) driver;
		File sourceFile = screenShot.getScreenshotAs(OutputType.FILE);
		String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + methodName + "_" + timeStamp
				+ ".png";
		File targetFile = new File(targetFilePath);
		sourceFile.renameTo(targetFile);
		return targetFilePath;
	}
}
