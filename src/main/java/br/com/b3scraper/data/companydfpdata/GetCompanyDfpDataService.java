package br.com.b3scraper.data.companydfpdata;

import br.com.b3scraper.data.companydfplist.CompanyDfp;
import br.com.b3scraper.driver.BrowserDriver;
import br.com.b3scraper.driver.BrowserDriverFactory;
import br.com.b3scraper.exception.ScraperException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GetCompanyDfpDataService {

    private static String IFRAME_SELECTOR = "#iFrameFormulariosFilho";
    private static String TABLE_SELECTOR = "#ctl00_cphPopUp_tbDados";

    private final PeriodService periodService = new PeriodService();

    private final BrowserDriverFactory browserDriverFactory = new BrowserDriverFactory();

    public List<CompanyDfpRow> getCompanyDfpData(String code, String url) {

        log.info("Iniciando obtenção dos dados do demonstrativos financeiros DFP da empresa {}", code);

        BrowserDriver browserDriver = browserDriverFactory.createBrowserDriver();
        WebDriver driver = browserDriver.getDriver();
        WebDriverWait wait = browserDriver.getDriverWait();

        log.info("Abrindo página com os dados do demonstrativo financeiros DFP");
        driver.get(url);

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector(IFRAME_SELECTOR)));

        WebElement tableData = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(TABLE_SELECTOR)));

        List<CompanyDfpRow> reportData = new ArrayList<>();

        List<WebElement> tableRows = tableData.findElements(By.cssSelector("tr"));
        WebElement headerRow = tableRows.get(0);
        List<WebElement> dataRows = tableRows.subList(1, tableRows.size());

        String firstPeriod = periodService.getPeriod(getColumnText(headerRow, 3));
        String secondPeriod = periodService.getPeriod(getColumnText(headerRow, 4));
        String thirdPeriod = periodService.getPeriod(getColumnText(headerRow, 5));

        for (WebElement row : dataRows) {
            String account = getColumnText(row, 1);
            String description = getColumnText(row, 2);
            String firstPeriodValue = getColumnText(row, 3);
            String secondPeriodValue = getColumnText(row, 4);
            String thirdPeriodValue = getColumnText(row, 5);

            CompanyDfpRow.CompanyDfpRowBuilder builder = CompanyDfpRow.builder().account(account).description(description);

            CompanyDfpRow firstColumn = builder.period(firstPeriod).value(firstPeriodValue).build();
            CompanyDfpRow secondColumn = builder.period(secondPeriod).value(secondPeriodValue).build();
            CompanyDfpRow thirdColumn = builder.period(thirdPeriod).value(thirdPeriodValue).build();

            reportData.addAll(List.of(firstColumn, secondColumn, thirdColumn));
        }

        return reportData;
    }

    private String getColumnText(WebElement rowElement, Integer columnPosition) {
        String cssSelector = "td:nth-child(" + columnPosition + ")";
        WebElement columnElement = rowElement.findElement(By.cssSelector(cssSelector));

        if (columnElement == null) {
            throw new ScraperException("Coluna não existente na tabela.");
        }

        String columnText = columnElement.getText();
        return StringUtils.isNotBlank(columnText) ? columnText : null;
    }

}
