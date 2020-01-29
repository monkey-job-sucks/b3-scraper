package br.com.b3scraper.data.companydfplist;

import br.com.b3scraper.driver.BrowserDriver;
import br.com.b3scraper.driver.BrowserDriverFactory;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GetCompanyDfpListService {

    private static String URL_DFP_REPORT_LIST = "http://bvmf.bmfbovespa.com.br/cias-listadas/empresas-listadas/HistoricoFormularioReferencia.aspx?codigoCVM=%s&tipo=dfp&ano=0&idioma=pt-br";
    private static String LIST_REPORT_SELECTOR = "#ctl00_contentPlaceHolderConteudo_divDemonstrativo";
    private static String ITEM_REPORT_SELECTOR = "div[id^=\"ctl00_contentPlaceHolderConteudo_rptDemonstrativo_ct\"]";

    private final BrowserDriverFactory browserDriverFactory = new BrowserDriverFactory();

    public List<CompanyDfp> getCompanyDfpList(String code) {

        log.info("Iniciando obtenção da lista de demonstrativos financeiros DFP da empresa {}", code);

        BrowserDriver browserDriver = browserDriverFactory.createBrowserDriver();
        WebDriver driver = browserDriver.getDriver();
        WebDriverWait wait = browserDriver.getDriverWait();

        log.info("Abrindo página com a lista de demonstrativos financeiros DFP");
        driver.get(getUrl(code));

        WebElement listReports = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(LIST_REPORT_SELECTOR)));

        List<WebElement> itemsReport = listReports.findElements(By.cssSelector(ITEM_REPORT_SELECTOR));

        List<CompanyDfp> list = new ArrayList<>();
        for (WebElement item : itemsReport) {
            WebElement linkReport = item.findElement(By.cssSelector("a"));

            // Obtem o atributo href da tag <a> e extrai a url
            String href = linkReport.getAttribute("href");
            HrefParserService hrefParser = new HrefParserService(href);

            // Obtem o atributo onmouseover e extrai a url
            String onmouseover = linkReport.getAttribute("onmouseover");
            OnMouseOverParserService onMouseOverParser = new OnMouseOverParserService(onmouseover);

            CompanyDfp dfp = CompanyDfp.builder()
                    .title(item.getText())
                    .referenceDate(onMouseOverParser.getReferenceDate())
                    .delivery(onMouseOverParser.getDelivery())
                    .receptionDate(onMouseOverParser.getReceptiondate())
                    .link(hrefParser.getHref())
                    .build();

            list.add(dfp);
        }

        log.info("Finalizada obtenção  da lista de {} demonstrativos financeiros DFP da empresa {}", list.size(), code);

        return list;
    }

    private String getUrl(String code) {
        return String.format(URL_DFP_REPORT_LIST, code);
    }


}
