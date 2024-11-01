package com.org.codewithsitangshu.util.driver;

public class DriverModule {

    public static DriverManager getManager(DriverType type) {

        DriverManager driverManager;

        switch (type) {
            case CHROME:
                driverManager = new ChromeDriverManager();
                break;
            case FIREFOX:
                driverManager = new FirefoxDriverManager();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        return driverManager;

    }

}
