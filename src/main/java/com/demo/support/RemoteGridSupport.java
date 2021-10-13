package com.demo.support;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.demo.base.DriverScript;

import io.github.bonigarcia.wdm.WebDriverManager;

public class RemoteGridSupport extends DriverScript {
    public static String GRID_REMOTE_URL = null;
    public static String SELENOID_IP = "34.125.168.229";
    public static String SELENOID_PORT = "4444";

    public static void setUp(String browser, String methodName) {
        GRID_REMOTE_URL = "http://" + SELENOID_IP + ":" + SELENOID_PORT + "/wd/hub";
        System.out.println(GRID_REMOTE_URL);
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("name", methodName);
        cap.setCapability("enableVNC", true);

        if (browser.toUpperCase().equals("CHROME")) {
            System.out.println("Chrome running in grid..");
            WebDriverManager.chromedriver().setup();
            cap.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
        } else if (browser.toUpperCase().equals("FIREFOX")) {
            System.out.println("Firfox running in grid..");
            WebDriverManager.firefoxdriver().setup();
            cap.setCapability(CapabilityType.BROWSER_NAME, BrowserType.FIREFOX);
        }
        try {
            driver = new RemoteWebDriver(new URL(GRID_REMOTE_URL), cap);
            System.out.println("Selenoid hub initiated..");
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            driver.get(baseUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}