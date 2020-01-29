package br.com.b3scraper.data.companydfplist;

import java.text.MessageFormat;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OnMouseOverParserService {

    private static String TEMPLATE = "writetxt(MontaHint(''{0}'',''{1}'',''{2}'',''{3}'',''{4}''))";
    private static MessageFormat MESSAGE_FORMAT = new MessageFormat(TEMPLATE);
    private static int REFERENCE_DATE_INDEX = 0;
    private static int DELIVERY_INDEX = 2;
    private static int RECEPTION_DATE_INDEX = 3;
    private static DateTimeFormatter REFERENCE_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static DateTimeFormatter RECEPTION_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy H:mm");

    private final Object[] parseValues;

    public OnMouseOverParserService(String value) {
        this.parseValues = MESSAGE_FORMAT.parse(value, new ParsePosition(0));
    }

    public LocalDate getReferenceDate() {
        String referenceDateValue = (String) parseValues[REFERENCE_DATE_INDEX];
        return LocalDate.parse(referenceDateValue, REFERENCE_DATE_FORMAT);
    }

    public String getDelivery() {
        return (String) parseValues[DELIVERY_INDEX];
    }

    public LocalDateTime getReceptiondate() {
        String receptionDateValue = (String) parseValues[RECEPTION_DATE_INDEX];
        return LocalDateTime.parse(receptionDateValue, RECEPTION_DATE_FORMAT);
    }

}
