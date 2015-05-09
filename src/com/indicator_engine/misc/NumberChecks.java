package com.indicator_engine.misc;

import java.text.NumberFormat;
import java.text.ParsePosition;

/**
 * Created by Tanmaya Mahapatra on 09-05-2015.
 */
public class NumberChecks {

    public static boolean isNumeric(String str)
    {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }
}
