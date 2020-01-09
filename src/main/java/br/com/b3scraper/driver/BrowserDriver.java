package br.com.b3scraper.driver;

import lombok.Builder;
import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

@Data
@Builder
public class BrowserDriver {

    private WebDriver driver;
    private WebDriverWait driverWait;

}
