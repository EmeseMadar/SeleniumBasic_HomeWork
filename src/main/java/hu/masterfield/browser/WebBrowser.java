package hu.masterfield.browser;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;


public class WebBrowser {

    public static WebDriver createDriver(WebBrowserType type) {
        WebDriver driver = null;
        FirefoxOptions ffOptions;
        ChromeOptions chromeOptions;

        switch (type) {
            case Chrome:
                System.setProperty("webdriver.chrome.driver", WebBrowserSetting.getPathToChromedriver());

                chromeOptions = new ChromeOptions();
                chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation", "disable-logging"});
                chromeOptions.setBinary(WebBrowserSetting.getPathToChrome());
                chromeOptions.addArguments("--remote-allow-origins=*");
                //chromeOptions.addArguments("--log-level=3");
//			chromeOptions.addArguments("--headless");
                driver = new ChromeDriver(chromeOptions);
                break;
            case Firefox:
                System.setProperty("webdriver.gecko.driver", WebBrowserSetting.getPathToGeckodriver());
//			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "false");

                ffOptions = new FirefoxOptions();
                ffOptions.setProfile(new FirefoxProfile());
                ffOptions.setLogLevel(FirefoxDriverLogLevel.DEBUG);
//			ffOptions.setCapability("marionette", true);

                ffOptions.setBinary(WebBrowserSetting.getPathToFirefox());
                driver = new FirefoxDriver(ffOptions);
                break;
            case ChromeWDM:
                WebDriverManager.chromedriver().setup();
                chromeOptions = new ChromeOptions();
                chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

                driver = new ChromeDriver(chromeOptions);
                break;
            case ChromeSM:
                driver = new ChromeDriver();
                break;
            case FirefoxWDM:
                WebDriverManager.firefoxdriver().setup();
//			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "false");

                ffOptions = new FirefoxOptions();
                ffOptions.setProfile(new FirefoxProfile());
                ffOptions.setLogLevel(FirefoxDriverLogLevel.DEBUG);

                driver = new FirefoxDriver(ffOptions);
                break;
            case FirefoxSM:
                driver = new FirefoxDriver();
                break;

            case EdgeWDM:
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
                edgeOptions.addArguments("--remote-allow-origins=*");
//			edgeOptions.setCapability("ignore-certificate-errors", true);
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver(edgeOptions);
                break;
            case EdgeSM:
                driver = new EdgeDriver();
                break;

            case SafariWDM:
                WebDriverManager.safaridriver();
                driver = new SafariDriver();
                break;
            default:
                break;
        }
        Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
        String browserName = caps.getBrowserName();
        String browserVersion = caps.getBrowserVersion();
        System.out.println("Browser is: " + browserName + " " + browserVersion);
        return driver;
    }
}
