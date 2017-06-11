package org.foi.nwtis.antbaric.helpers;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author javert
 */
public class MiscHelper {

    public static String currentDate(String format) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        
        return dateFormat.format(date);
    }
}
