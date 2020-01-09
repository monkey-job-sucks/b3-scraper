package br.com.b3scraper.data.companydfpdata;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CompanyDfpRow {
    private String account;
    private String description;
    private String period;
    private String value;
}
