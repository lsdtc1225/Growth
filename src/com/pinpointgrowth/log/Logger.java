package com.pinpointgrowth.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletContext;

public class Logger {

    private static BufferedWriter writer;

    public static void logStackTrace(StackTraceElement[] stackTraceArray,
            ServletContext context) {
        Date date = new Date();
        String dateString = date.toString().replace(':', '-');
        String root = context.getRealPath("/");
        File path = new File(root + "/uploads");
        if (!path.exists()) {
            boolean status = path.mkdirs();
        }

        String fullFileName = path + "/" + dateString + ".log";
        try {
            writer = new BufferedWriter(new FileWriter(
                    fullFileName));
            for (StackTraceElement element : stackTraceArray) {
                writer.write(element.toString() + "\n");
            }
            writer.write("\n");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
