package br.com.b3scraper.data.companysummary;

import br.com.b3scraper.driver.BrowserDriver;
import br.com.b3scraper.driver.BrowserDriverFactory;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class GetCompanySummaryService {

    private static String URL_COMPANY_SUMMARY = "http://bvmf.bmfbovespa.com.br/cias-listadas/empresas-listadas/ResumoEmpresaPrincipal.aspx?codigoCvm=%s&idioma=pt-br";
    private static String IFRAME_SELECTOR = "#ctl00_contentPlaceHolderConteudo_iframeCarregadorPaginaExterna";
    private static String TABLE_SELECTOR = "#accordionDados table.ficha";

    private static String NAME_CELL_TEXT = "Nome de Pregão:";
    private static String CNPJ_CELL_TEXT = "CNPJ:";
    private static String MAIN_ACTIVITY_CELL_TEXT = "Atividade Principal:";
    private static String INDUSTRY_CLASSIFICATION_CELL_TEXT = "Classificação Setorial:";
    private static String SITE_CELL_TEXT = "Site:";

    private final BrowserDriverFactory browserDriverFactory = new BrowserDriverFactory();

    public CompanySummary getCompanySummary(String code) throws Exception {

        log.info("Iniciando obtenção dos dados da empresa {}", code);

        BrowserDriver browserDriver = browserDriverFactory.createBrowserDriver();
        WebDriver driver = browserDriver.getDriver();
        WebDriverWait wait = browserDriver.getDriverWait();

        log.info("Abrindo página com dados da empresa {}", code);
        driver.get(getUrl(code));

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector(IFRAME_SELECTOR)));

        WebElement tableData = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(TABLE_SELECTOR)));
        List<WebElement> tableRows = tableData.findElements(By.cssSelector("tr"));

        Map<String, String> summaryData = new HashMap<>();
        for (WebElement tableRow : tableRows) {
            WebElement cellName = tableRow.findElement(By.cssSelector("td:nth-child(1)"));
            WebElement cellValue = tableRow.findElement(By.cssSelector("td:nth-child(2)"));
            summaryData.put(cellName.getText(), cellValue.getText());
        }

        CompanySummary summary = CompanySummary.builder()
                .cnpj(summaryData.get(CNPJ_CELL_TEXT))
                .name(summaryData.get(NAME_CELL_TEXT))
                .mainActivity(summaryData.get(MAIN_ACTIVITY_CELL_TEXT))
                .industryClassification(summaryData.get(INDUSTRY_CLASSIFICATION_CELL_TEXT))
                .site(summaryData.get(SITE_CELL_TEXT))
                .build();

        log.info("Finalizada obtenção dos dados da empresa {}", code);

        return summary;
    }

    private String getUrl(String code) {
        return String.format(URL_COMPANY_SUMMARY, code);
    }

}
