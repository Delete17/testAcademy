package automate1;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class cura_loginTest {

	WebDriver driver;
	Select s;

	@BeforeTest
	public void launchBrowser() {

		WebDriverManager.chromedriver().setup();

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://katalon-demo-cura.herokuapp.com/");
		driver.findElement(By.id("btn-make-appointment")).click();

	}

	@Test(priority = 2)
	public void LoginPositiveTC() {
		driver.findElement(By.id("txt-username")).sendKeys("John Doe");
		driver.findElement(By.id("txt-password")).sendKeys("ThisIsNotAPassword");
		driver.findElement(By.id("btn-login")).click();

	}

	@Test(priority = 1)
	public void LoginNegativeTC() {
		driver.findElement(By.id("txt-username")).sendKeys("John DoeDoe");
		driver.findElement(By.id("txt-password")).sendKeys("ThisIsNotPassword");
		driver.findElement(By.id("btn-login")).click();
		String err = "Login failed! Please ensure the username and password are valid.";
		WebElement ErrMsg = driver.findElement(By.cssSelector("p[class='lead text-danger']"));
		Assert.assertEquals(err, ErrMsg.getText());

	}

	@Test(priority = 3)
	public void MakeAppointment() throws InterruptedException {
		WebElement facilitydropDown =driver.findElement(By.id("combo_facility"));
		s = new Select(facilitydropDown);
		s.selectByValue("Hongkong CURA Healthcare Center");
		driver.findElement(By.id("chk_hospotal_readmission")).click();
		driver.findElement(By.id("txt_visit_date")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//table/tbody/tr/td[.='17']")).click();
		driver.findElement(By.id("txt_comment")).sendKeys("QA testing");
		driver.findElement(By.id("btn-book-appointment")).click();
	}
	
	@Test(priority=4)
	public void confirmation()
	{
		String confirm =driver.findElement(By.cssSelector(".lead")).getText();
		String cfn = "Please be informed that your appointment has been booked as following:";
		Assert.assertEquals(confirm, cfn);
	}

}
