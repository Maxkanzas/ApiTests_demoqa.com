package config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class WebDriverProvider implements Supplier<WebDriver> {

    private final WebDriverConfig config;

    public WebDriverProvider() {
        this.config = ConfigFactory.create(WebDriverConfig.class, System.getProperties());
    }

    @Override
    public WebDriver get() {
        WebDriver driver = createDriver();
        driver.get(config.getBaseUrl());
        return driver;
    }

    public WebDriver createDriver() {
        if (config.getRemoteUrl() != null && !config.getRemoteUrl().isEmpty()) {
            return createRemoteDriver();
        } else {
            return createLocalDriver();
        }
    }

    private WebDriver createLocalDriver() {
        switch (config.getBrowser()) {
            case CHROME: {
                WebDriverManager.chromedriver().browserVersion(config.getBrowserVersion()).setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--window-size=" + config.getBrowserSize());
                return new ChromeDriver(options);
            }
            case FIREFOX: {
                WebDriverManager.firefoxdriver().browserVersion(config.getBrowserVersion()).setup();
                FirefoxOptions options = new FirefoxOptions();
                options.addArguments("--width=" + config.getBrowserSize().split("x")[0]);
                options.addArguments("--height=" + config.getBrowserSize().split("x")[1]);
                return new FirefoxDriver(options);
            }
            default: {
                throw new RuntimeException("No such driver");
            }
        }
    }
    private WebDriver createRemoteDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(config.getBrowser().name().toLowerCase());
        capabilities.setVersion(config.getBrowserVersion());

        Map<String, Object> selenoidOptions = new HashMap<>();
        selenoidOptions.put("enableVNC", true);
        selenoidOptions.put("enableVideo", true);
        selenoidOptions.put("screenResolution", config.getBrowserSize());
        capabilities.setCapability("selenoid:options", selenoidOptions);

        String remoteUrl = config.getRemoteUrl();
        if (!remoteUrl.startsWith("http://") && !remoteUrl.startsWith("https://")) {
            remoteUrl = "https://" + remoteUrl;
        }

        try {
            return new RemoteWebDriver(new URL(remoteUrl), capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Failed to create RemoteWebDriver", e);
        }
    }
}