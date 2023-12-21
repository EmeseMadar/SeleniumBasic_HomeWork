package hu.masterfield.browser;

public class WebBrowserSetting {

	private static final String pathToGeckoDriver  = "c:\\Masterfield\\WebBrowsers\\FirefoxPortableDriver_33\\geckodriver.exe";
	private static final String pathToFirefox      = "c:\\Masterfield\\WebBrowsers\\FirefoxPortableBin_117\\firefox.exe";
	private static final String pathToChromeDriver = "c:\\Masterfield\\WebBrowsers\\ChromePortableDriver_117\\chromedriver.exe";
	private static final String pathToChrome       = "c:\\Masterfield\\WebBrowsers\\ChromePortableBin_117\\chrome.exe";
	public static final String baseURL       	   = "https://raczpeterantal.hu";
	
	private static final String pathToScreenShots = "c:\\Masterfield\\Screenshots\\";

	public static String getPathToGeckodriver() {
		return pathToGeckoDriver;
	}
	public static String getPathToFirefox() {
		return pathToFirefox;
	}
	public static String getPathToChromedriver() {
		return pathToChromeDriver;
	}
	public static String getPathToChrome() {
		return pathToChrome;
	}
	public static String getPathToScreenshots() {
		return pathToScreenShots;
	}
	public static String getBaseURL() {
		return baseURL;
	}
}