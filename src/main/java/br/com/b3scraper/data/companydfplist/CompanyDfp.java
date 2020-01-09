package br.com.b3scraper.data.companydfplist;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
public class CompanyDfp {

    private String title;
    private LocalDate referenceDate;
    private String delivery;
    private LocalDateTime receptionDate;
    private String link;
}
