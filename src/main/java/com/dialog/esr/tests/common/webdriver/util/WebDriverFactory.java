
package com.dialog.esr.tests.common.webdriver.util;

import com.dialog.esr.tests.common.webdriver.env.Profile;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

public class WebDriverFactory {
    private static final Logger LOG = Logger.getLogger(WebDriverFactory.class.getName());
    static Profile profile;

    /* Browser constants */
    public static final String CHROME = "chrome";
    public static final String FIREFOX = "firefox";
    public static final String INTERNET_EXPLORER = "ie";
    public static final String PHANTOMJS = "phantomjs";

    public static WebDriver getInstance(String browser, String type) {
        WebDriver driver = null;
        URL url = null;
        String seleniumHub = Profile.getSeleniumHub();
        String os = System.getProperty("os.name").toLowerCase().substring(0, 3);
        DesiredCapabilities capability;

        try {
            url = seleniumHub.isEmpty() ? null : new URL(seleniumHub);
        } catch (MalformedURLException e) {
            LOG.warning("Error occurred while reading selenium hub url " + e);
        }

        switch (browser) {
            case CHROME:
                capability = DesiredCapabilities.chrome();
                if (type.equals("smoke")) {
                    // headless browser testing
                    /*ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--headless");
                    capability.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                    setDriver(os, browser);
                    driver = url != null ? new RemoteWebDriver(url, capability) : new ChromeDriver(chromeOptions);*/
                    setCapabilities(capability);
                    setDriver(os, browser);
                    driver = url != null ? new RemoteWebDriver(url, capability) : new ChromeDriver();
                } else {
                    setCapabilities(capability);
                    setDriver(os, browser);
                    driver = url != null ? new RemoteWebDriver(url, capability) : new ChromeDriver();
                }
                break;
            case FIREFOX:
                capability = DesiredCapabilities.firefox();
                setCapabilities(capability);
                setDriver(os, "gecko");
                driver = url != null ? new RemoteWebDriver(url, capability) : new FirefoxDriver();
                break;
            case INTERNET_EXPLORER:
                capability = DesiredCapabilities.internetExplorer();
                setDriver(os, browser);
                driver = url != null ? new RemoteWebDriver(url, capability) : new InternetExplorerDriver();
                break;
            case PHANTOMJS:
                capability = DesiredCapabilities.phantomjs();
                break;
            default:
                capability = DesiredCapabilities.htmlUnit();
                //driver = new HtmlUnitDriver(true);
                break;
        }
        return driver;
    }

    private static void setCapabilities(DesiredCapabilities capability) {
        capability.setJavascriptEnabled(true);
        capability.setPlatform(Platform.WINDOWS);
    }

    private static void setDriver(String os, String name) {
        String binary = System.getProperty("user.home")  + File.separator + "driver" + File.separator + name + "driver" + (os.equals("win") ? ".exe" : "");
        System.setProperty("webdriver." + name + ".driver", binary);
    }
}
