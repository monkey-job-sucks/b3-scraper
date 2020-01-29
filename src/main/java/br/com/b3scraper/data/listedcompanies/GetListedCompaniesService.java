package br.com.b3scraper.data.listedcompanies;

import br.com.b3scraper.driver.BrowserDriver;
import br.com.b3scraper.driver.BrowserDriverFactory;
import br.com.b3scraper.exception.ScraperException;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GetListedCompaniesService {

    private static String URL_LISTED_COMPANIES = "http://bvmf.bmfbovespa.com.br/cias-listadas/empresas-listadas/BuscaEmpresaListada.aspx?idioma=pt-br";
    private static String ALL_COMPANIES_BUTTON_SELECTOR = "#ctl00_contentPlaceHolderConteudo_BuscaNomeEmpresa1_btnTodas";
    private static String TABLE_CELL_COMPANY_SELECTOR = "#ctl00_contentPlaceHolderConteudo_BuscaNomeEmpresa1_grdEmpresa_ctl01 tr td:nth-child(1) a";

    private final BrowserDriverFactory browserDriverFactory = new BrowserDriverFactory();

    public List<Company> getListedCompaniesService() throws Exception {

        log.info("Iniciando obtenção da empresas listadas");

        BrowserDriver browserDriver = browserDriverFactory.createBrowserDriver();
        WebDriver driver = browserDriver.getDriver();
        WebDriverWait wait = browserDriver.getDriverWait();

        log.info("Abrindo página com as empresas listadas");
        driver.get(URL_LISTED_COMPANIES);

        WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ALL_COMPANIES_BUTTON_SELECTOR)));
        button.click();

        var companies = new ArrayList<Company>();
        List<WebElement> tds = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(TABLE_CELL_COMPANY_SELECTOR)));
        for (WebElement td : tds) {
            String href = td.getAttribute("href");
            String code = getCodeFromURL(href);
            String name = td.getText();

            companies.add(Company.builder().code(code).name(name).build());
        }

        log.info("Finalizada obtenção das empresas listadas, encontradas {} empresas.", companies.size());

        return companies;
    }

    private static String getCodeFromURL(String url) throws Exception {
        try {
            URI uri = new URI(url);
            String queryString = uri.getQuery();
            return queryString.split("=")[1];
        } catch(Exception e) {
            throw new ScraperException("Erro ao obter código da empresa na url: " + url, e);
        }

    }

}
