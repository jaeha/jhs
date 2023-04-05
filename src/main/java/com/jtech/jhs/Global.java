package com.jtech.jhs;

import java.util.Date;
import java.text.Format;
import java.text.SimpleDateFormat;

public class Global {
    public static boolean IsFileScanRunning =  false;
    
    public static String d2s(Date d) {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return formatter.format(d);
    }
}
