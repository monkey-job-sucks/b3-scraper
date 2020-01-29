package br.com.b3scraper.data.companydfplist;

import java.text.MessageFormat;
import java.text.ParsePosition;

public class HrefParserService {

    private static String TEMPLATE = "javascript:AbreFormularioCadastral(''{0}'')";
    private static MessageFormat MESSAGE_FORMAT = new MessageFormat(TEMPLATE);
    private static String TEMPLATE_DXW = "javascript:ConsultarDXW(''{0}'')";
    private static MessageFormat MESSAGE_FORMAT_DXW = new MessageFormat(TEMPLATE_DXW);
    private static int HREF_INDEX = 0;

    private final Object[] parseValues;

    public HrefParserService(String value) {
        this.parseValues = getParseValues(value);
    }

    private Object[] getParseValues(String value) {
        Object[] parseValues = MESSAGE_FORMAT.parse(value, new ParsePosition(0));
        if (parseValues == null || parseValues.length == 0) {
            parseValues = MESSAGE_FORMAT_DXW.parse(value, new ParsePosition(0));
        }
        return parseValues;
    }

    public String getHref() {
        return (String) parseValues[HREF_INDEX];
    }

}
