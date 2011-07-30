package com.wordpong.util;

import java.util.Date;

import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

public class TimeUtil {
    private static PeriodFormatter cEnglishWords;
    
    // eg 1 day 3 hours 4 mins ago
    public static String getElapsedTimeString(Date d) {
        String result = "unknown";
        if (d != null) {
            Date endDate = new Date();
            Period period = new Period(d.getTime(), endDate.getTime());
            //TODO: localize date strings -years, months, days, etc
            result = getFormatEN().print(period) + " ago";
        }
        return result;
    }
    
    // English years, months, hours, etc
    public static PeriodFormatter getFormatEN()
    {
        if(cEnglishWords == null)
        {
            String as[] = {
                " ", ",", ",and ", ", and "
            };
            cEnglishWords = (new PeriodFormatterBuilder()).appendYears().appendSuffix(" year", " years").appendSeparator(", ", " and ", as).appendMonths().appendSuffix(" month", " months").appendSeparator(", ", " and ", as).appendWeeks().appendSuffix(" week", " weeks").appendSeparator(", ", " and ", as).appendDays().appendSuffix(" day", " days").appendSeparator(", ", " and ", as).appendHours().appendSuffix(" hour", " hours").appendSeparator(", ", " and ", as).appendMinutes().appendSuffix(" minute", " minutes").appendSeparator(", ", " and ", as).appendSeconds().appendSuffix(" second", " seconds").toFormatter();
        }
        return cEnglishWords;
    }

}
