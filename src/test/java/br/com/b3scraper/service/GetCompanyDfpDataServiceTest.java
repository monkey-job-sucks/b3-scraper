package br.com.b3scraper.service;

import br.com.b3scraper.data.companydfpdata.CompanyDfpRow;
import br.com.b3scraper.data.companydfpdata.GetCompanyDfpDataService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GetCompanyDfpDataServiceTest {

    private GetCompanyDfpDataService getCompanyDfpDataService = new GetCompanyDfpDataService();

    @Test
    public void getListedCompaniesService() throws Exception {
        var code = "1";
        var url = "http://www.rad.cvm.gov.br/ENETCONSULTA/frmGerenciaPaginaFRE.aspx?NumeroSequencialDocumento=80929&CodigoTipoInstituicao=2";
        var expected = 120; // 40 rows x 3 columns

        List<CompanyDfpRow> reportData = getCompanyDfpDataService.getCompanyDfpData(code, url);

        Assertions.assertEquals(expected, reportData.size());
    }

}
