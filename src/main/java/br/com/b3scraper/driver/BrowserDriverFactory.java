package br.com.b3scraper.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BrowserDriverFactory {

    private int timeoutSeconds = 10;

    public BrowserDriver createBrowserDriver() {
        WebDriverManager.firefoxdriver().setup();
        WebDriver driver = new FirefoxDriver();
        WebDriverWait driverWait = new WebDriverWait(driver, timeoutSeconds);
        return BrowserDriver.builder().driver(driver).driverWait(driverWait).build();
    }

}