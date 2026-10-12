package utils;

public final class ConfigReader {
    private ConfigReader() {}
    public static String baseUrl(){ return System.getProperty("baseUrl", "https://www.ulta.com/"); }
    public static String browser(){ return System.getProperty("browser", "chrome"); }
    public static boolean headless(){ return Boolean.parseBoolean(System.getProperty("headless", "false")); }
    public static int timeout(){ return Integer.parseInt(System.getProperty("timeout", "20")); }
}
