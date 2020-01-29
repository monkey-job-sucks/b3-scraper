package br.com.b3scraper.data.listedcompanies;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Company {
    private String code;
    private String name;
}