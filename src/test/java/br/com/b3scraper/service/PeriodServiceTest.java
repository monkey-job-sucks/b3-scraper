package br.com.b3scraper.service;

import br.com.b3scraper.data.companydfpdata.PeriodService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PeriodServiceTest {

    PeriodService periodService = new PeriodService();

    @Test
    public void getPeriod_1_period() {
        String start = "01/01/2018";
        String end = "31/03/2018";
        String period = periodService.getPeriod(start, end);
        Assertions.assertEquals(period, "1/2018");
    }


    @Test
    public void getPeriod_2_period() {
        String start = "01/04/2019";
        String end = "30/06/2019";
        String period = periodService.getPeriod(start, end);
        Assertions.assertEquals(period, "2/2019");
    }

    @Test
    public void getPeriod_3_period() {
        String start = "01/07/2019";
        String end = "30/09/2019";
        String period = periodService.getPeriod(start, end);
        Assertions.assertEquals(period, "3/2019");
    }

    @Test
    public void getPeriod_4_period() {
        String start = "01/10/2019";
        String end = "31/12/2019";
        String period = periodService.getPeriod(start, end);
        Assertions.assertEquals(period, "4/2019");
    }


    @Test
    public void getPeriod_1_2_period() {
        String start = "01/01/2018";
        String end = "30/06/2018";
        String period = periodService.getPeriod(start, end);
        Assertions.assertEquals(period, "1-2/2018");
    }

    @Test
    public void getPeriod_1_3_period() {
        String start = "01/01/2018";
        String end = "30/09/2018";
        String period = periodService.getPeriod(start, end);
        Assertions.assertEquals(period, "1-3/2018");
    }

    @Test
    public void getPeriod_1_4_period() {
        String start = "01/01/2017";
        String end = "31/12/2017";
        String period = periodService.getPeriod(start, end);
        Assertions.assertEquals(period, "1-4/2017");
    }


}