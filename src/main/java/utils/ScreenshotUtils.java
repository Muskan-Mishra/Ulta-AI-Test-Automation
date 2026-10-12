package utils;

import org.openqa.selenium.*;
import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class ScreenshotUtils {
    private ScreenshotUtils(){}
    public static byte[] take(WebDriver d,String name){
        byte[] bytes=((TakesScreenshot)d).getScreenshotAs(OutputType.BYTES);
        try{Path dir=Paths.get("reports","screenshots"); Files.createDirectories(dir); Files.write(dir.resolve(new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())+"_"+name+".png"),bytes);}catch(IOException ignored){}
        return bytes;
    }
}
