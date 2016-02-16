package com.vgilab.geobot.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ljzhang
 */
public class ExifUtil {

    public static Calendar getCalendarFromExifDate(String timeStamp, String dateStamp) {
        if (StringUtils.isNotBlank(timeStamp) && StringUtils.isNotBlank(dateStamp)) {
            final DateFormat formatter = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
            final Date exifDate;
            try {
                exifDate = formatter.parse(dateStamp + " " + timeStamp);
                final Calendar cal = Calendar.getInstance();
                cal.setTime(exifDate);
            // are there any offsets for the timezone 
                // cal.add(Calendar.HOUR, hourOffset);
                // cal.add(Calendar.MINUTE, minOffset);
                return cal;
            } catch (ParseException ex) {
                Logger.getLogger(ExifUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static BigDecimal getDecimalDegreeFromExif(final String angle, final String angleRef) {
        try {
            if (StringUtils.isBlank(angleRef)) {
                final DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                symbols.setGroupingSeparator(',');
                symbols.setDecimalSeparator('.');
                final DecimalFormat decimalFormat = new DecimalFormat("#,##0.0#", symbols);
                decimalFormat.setParseBigDecimal(true);
                return (BigDecimal) decimalFormat.parse(angle);
            } else {
                final Pattern regex = Pattern.compile("(\\d+(?:\\.\\d+)?)");
                final Matcher matcher = regex.matcher(angle);
                final Double degrees = matcher.find() ? Double.valueOf(matcher.group(1)) : 0d;
                final Double minutes = matcher.find() ? Double.valueOf(matcher.group(1)) : 0d;
                final Double seconds = matcher.find() ? Double.valueOf(matcher.group(1)) : 0d;
                return ExifUtil.degreesMinutesSecondsToDecimal(degrees, minutes, seconds, (StringUtils.equalsIgnoreCase("S", angleRef) || StringUtils.equalsIgnoreCase("SOUTH", angleRef) || StringUtils.equalsIgnoreCase("W", angleRef) || StringUtils.equalsIgnoreCase("WEST", angleRef)));
            }
        } catch (ParseException ex) {
            Logger.getLogger(ExifUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static BigDecimal getAltitudeFromExif(final String altitude, final String altitudeRef) {
        if (StringUtils.isNotBlank(altitude)) {
            final DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setGroupingSeparator(',');
            symbols.setDecimalSeparator('.');
            final DecimalFormat decimalFormat = new DecimalFormat("#,##0.0#", symbols);
            decimalFormat.setParseBigDecimal(true);
            // parse the string
            try {
                BigDecimal altitudeNumeric;
                if (altitude.endsWith("m")) {
                    altitudeNumeric = (BigDecimal) decimalFormat.parse(altitude.replace("m", ""));
                } else {
                    altitudeNumeric = (BigDecimal) decimalFormat.parse(altitude);
                }
                if (StringUtils.containsIgnoreCase("BELOW", altitudeRef)) {
                    return altitudeNumeric.negate();
                }
                return altitudeNumeric;
            } catch (ParseException ex) {
                Logger.getLogger(ExifUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static BigDecimal degreesMinutesSecondsToDecimal(final Double degs, final Double mins, final Double secs, final boolean isNegative) {
        double decimal = Math.abs(degs)
                + mins / 60.0d
                + secs / 3600.0d;

        if (Double.isNaN(decimal)) {
            return null;
        }

        if (isNegative) {
            decimal *= -1;
        }

        return BigDecimal.valueOf(decimal);
    }

}
