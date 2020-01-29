package br.com.b3scraper.data.companysummary;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CompanySummary {
    private String name;
    private String cnpj;
    private String mainActivity;
    private String industryClassification;
    private String site;
}