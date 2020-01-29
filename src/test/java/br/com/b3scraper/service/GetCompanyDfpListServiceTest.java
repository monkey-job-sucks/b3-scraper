package br.com.b3scraper.service;

import br.com.b3scraper.data.companydfplist.CompanyDfp;
import br.com.b3scraper.data.companydfplist.GetCompanyDfpListService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetCompanyDfpListServiceTest {

    private GetCompanyDfpListService getCompanyDfpListService = new GetCompanyDfpListService();

    @Test
    public void getCompanyDfpList_petrobras() throws Exception {
        String petrobrasCode = "9512";
        List<CompanyDfp> dfpList = getCompanyDfpListService.getCompanyDfpList(petrobrasCode);

        CompanyDfp company1 = CompanyDfp.builder()
                .title("31/12/2018 - Demonstrações Financeiras Padronizadas - Versão 1.0")
                .referenceDate(LocalDate.of(2018, 12, 31))
                .delivery("Apresentação")
                .receptionDate(LocalDateTime.of(2019, 02, 27, 19, 45))
                .link("http://www.rad.cvm.gov.br/ENETCONSULTA/frmGerenciaPaginaFRE.aspx?NumeroSequencialDocumento=80929&CodigoTipoInstituicao=2")
                .build();

        assertThat(dfpList, hasItems(company1));
    }
}
