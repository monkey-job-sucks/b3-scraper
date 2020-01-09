package br.com.b3scraper.service;

import br.com.b3scraper.data.companysummary.CompanySummary;
import br.com.b3scraper.data.companysummary.GetCompanySummaryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GetCompanySummaryServiceTest {

    private GetCompanySummaryService getCompanySummaryService = new GetCompanySummaryService();

    @Test
    public void getCompanySummary_petrobras() throws Exception {
        String petrobrasCode = "9512";
        CompanySummary summary = getCompanySummaryService.getCompanySummary(petrobrasCode);
        Assertions.assertEquals("PETROBRAS", summary.getName());
    }

    @Test
    public void getCompanySummary_itau() throws Exception {
        String itauCode = "7617";
        CompanySummary summary = getCompanySummaryService.getCompanySummary(itauCode);
        Assertions.assertEquals("ITAUSA", summary.getName());
    }

}