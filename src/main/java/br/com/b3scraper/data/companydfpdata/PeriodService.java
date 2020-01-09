package br.com.b3scraper.data.companydfpdata;

import br.com.b3scraper.exception.ScraperException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class PeriodService {

    private static DateTimeFormatter PERIOD_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public LocalDate getStart(String date) {
        return LocalDate.parse(date, PERIOD_DATE_FORMAT);
    }

    public LocalDate getEnd(String date) {
        return LocalDate.parse(date, PERIOD_DATE_FORMAT);
    }

    public String getPeriod(String columnValue) {
        String[] periodValues = getPeriodValueFromColumn(columnValue);
        return getPeriod(periodValues[0], periodValues[1]);
    }

    public String getPeriod(String startDate, String endDate) {
        LocalDate start = getStart(startDate);
        LocalDate end = getEnd(endDate);

        Integer yearPeriod = start.getYear();
        Integer startPeriod = getStartPeriod(start);
        Integer endPeriod = getEndPeriod(end);

        String quarterPeriod = startPeriod == endPeriod ? endPeriod.toString() : startPeriod.toString() + "-" + endPeriod.toString();

        return quarterPeriod + "/" + yearPeriod;
    }

    public Integer getStartPeriod(LocalDate date) {

        if (date.getDayOfMonth() != 1) {
            throw new ScraperException("Data inválida, o dia não corresponde ao início de um trimestre.");
        }

        Integer monthValue = date.getMonthValue();
        if (monthValue == 1) {
            return 1;
        } else if (monthValue == 4) {
            return 2;
        } else if (monthValue == 7) {
            return 3;
        } else if (monthValue == 10) {
            return 4;
        }

        throw new ScraperException("Data inválida, o mês não corresponde ao início de um trimestre.");
    }

    public Integer getEndPeriod(LocalDate date) {

        if (date.getDayOfMonth() != getLastDayMonth(date)) {
            throw new ScraperException("Data inválida, o dia não corresponde ao fim de um trimestre.");
        }

        Integer monthValue = date.getMonthValue();
        if (monthValue == 3) {
            return 1;
        } else if (monthValue == 6) {
            return 2;
        } else if (monthValue == 9) {
            return 3;
        } else if (monthValue == 12) {
            return 4;
        }

        throw new ScraperException("Data inválida, o mês não corresponde ao fim de um trimestre.");
    }

    private int getLastDayMonth(LocalDate date) {
        Calendar calendar = GregorianCalendar.from(date.atStartOfDay(ZoneId.systemDefault()));
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public String[] getPeriodValueFromColumn(String columnValue) {
        String normalizeValue = columnValue.replaceAll("\n", "");
        normalizeValue = normalizeValue.replaceAll(" ", "");
        return normalizeValue.split("a");
    }

}
