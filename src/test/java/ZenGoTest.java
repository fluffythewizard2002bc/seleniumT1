import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import java.time.Duration;
import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfWindowsToBe;

public class ZenGoTest {
    @Before
    public void setupClass() {
        WebDriverManager.chromedriver().setup();
    }
    @After
    public void tearDown() {
        driver.quit();
    }
    public WebDriver driver;

    @Test
    public void checkPageLoadTabChangeLogoFound() {
        driver = getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
        driver.navigate().to("https://zengo.com/");
        driver.manage().window().maximize();
        String originalWindow=driver.getWindowHandle();

        WebElement featuresButton = driver.findElement(By.linkText("Features"));
        featuresButton.click();
        WebElement buyButton =driver.findElement(By.linkText("Buy"));
        buyButton.click();
        //Wait for the new window or tab


        // Waiting 30 seconds for an element to be present on the page, checking
        // for its presence once every 5 seconds.
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(6))
                .pollingEvery(Duration.ofSeconds(2));
        wait.until(numberOfWindowsToBe(2));

        //Loop through until we find a new window handle
        for (String windowHandle : driver.getWindowHandles()) {
            if(!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        String url=driver.getCurrentUrl();
        //Assert.assertEquals(url,"https://zengowallet.banxa.com/");
        boolean Display = driver.findElement(By.xpath("//*[@src=\"https://btc-storage-syd-p1-ap-southeast-2-branding-files.s3.ap-southeast-2.amazonaws.com/ZENGOWEB/logo/M8BBuMvyNP1RKDZAFfadxXzV0Rui1JCPC0zoi0v9.png\"]")).isDisplayed();
        Assert.assertTrue(Display);

        Assert.assertEquals(url,"https://zengowallet.banxa.com/");
        driver.close();
        // change focus back to home
        driver.switchTo().window(originalWindow);
        //driver.navigate().back(); if it wasn't in a new tab
        driver.quit();
    }
    private WebDriver getDriver() {
        //Using WebDriverManager package, we are able to not worry about
        return new ChromeDriver();
    }

}
