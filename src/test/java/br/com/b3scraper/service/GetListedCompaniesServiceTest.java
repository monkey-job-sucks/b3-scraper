package br.com.b3scraper.service;

import br.com.b3scraper.data.listedcompanies.Company;
import br.com.b3scraper.data.listedcompanies.GetListedCompaniesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GetListedCompaniesServiceTest {

    private GetListedCompaniesService getListedCompaniesService = new GetListedCompaniesService();

    @Test
    public void getListedCompaniesService() throws Exception {
        Integer totalCompanies = 423;
        List<Company> companies = getListedCompaniesService.getListedCompaniesService();
        Assertions.assertEquals(totalCompanies, companies.size());
    }

}
