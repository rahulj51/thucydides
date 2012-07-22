package net.thucydides.core.pages.integration;


import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.MockEnvironmentVariables;
import net.thucydides.core.webdriver.SupportedWebDriver;
import net.thucydides.core.webdriver.WebDriverFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class WhenResizingTheBrower {

    private StaticSitePage page;
    private EnvironmentVariables environmentVariables;
    private WebDriver driver;
    WebDriverFactory factory;

    @Before
    public void setupFactory() {
        environmentVariables = new MockEnvironmentVariables();
        factory = new WebDriverFactory(environmentVariables);
    }

    @After
    public void shutdownDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void should_resize_chrome_automatically() {
        environmentVariables.setProperty("thucydides.browser.height", "200");
        environmentVariables.setProperty("thucydides.browser.width", "400");

        driver = factory.newInstanceOf(SupportedWebDriver.CHROME);
        page = new StaticSitePage(driver, 1000);
        page.open();

        int width = ((Long)(((JavascriptExecutor)driver).executeScript("return window.innerWidth"))).intValue();
        assertThat(width, allOf(lessThanOrEqualTo(400), greaterThan(380)));
    }

    @Test
    public void should_resize_firefox_automatically() {
        environmentVariables.setProperty("thucydides.browser.height", "200");
        environmentVariables.setProperty("thucydides.browser.width", "400");

        driver = factory.newInstanceOf(SupportedWebDriver.FIREFOX);
        page = new StaticSitePage(driver, 1000);
        page.open();

        int width = ((Long)(((JavascriptExecutor)driver).executeScript("return window.innerWidth"))).intValue();
        assertThat(width, allOf(lessThanOrEqualTo(400), greaterThan(380)));
    }


    class StubbedWebDriverFactory extends WebDriverFactory {
        StubbedWebDriverFactory(EnvironmentVariables environmentVariables) {
            super(environmentVariables);
        }

        public boolean screenWasResized;
        @Override
        protected void resizeBrowserTo(WebDriver driver, int height, int width) {
            screenWasResized = true;
        }
    };

    @Test
    public void should_not_resize_browser_if_dimension_are_not_provided() {
        StubbedWebDriverFactory stubbedFactory = new StubbedWebDriverFactory(environmentVariables);
        driver = stubbedFactory.newInstanceOf(SupportedWebDriver.FIREFOX);
        page = new StaticSitePage(driver, 1000);
        page.open();
        assertThat(stubbedFactory.screenWasResized, is(false));
    }

    @Test
    public void should_not_resize_htmlunit_automatically() {
        environmentVariables.setProperty("thucydides.browser.height", "200");
        environmentVariables.setProperty("thucydides.browser.width", "400");

        driver = factory.newInstanceOf(SupportedWebDriver.HTMLUNIT);
        page = new StaticSitePage(driver, 1024);
        page.open();

    }
}
