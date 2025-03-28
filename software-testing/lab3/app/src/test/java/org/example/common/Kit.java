package org.example.common;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.provider.Arguments;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Kit {
    public static List<WebDriver> drivers() {
        List<WebDriver> ds = List.of(
            new ChromeDriver(),
            new FirefoxDriver()
        );

        ds.stream().forEach(driver -> {
            driver.manage().window().setSize(new Dimension(1920, 1080));
        });

        return ds;
    }

    public static Stream<Arguments> driversAsArguments() {
        return drivers().stream().map(driver -> {
            return Arguments.of(driver);
        });
    }

    private WebDriver driver;

    public Kit(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriverWait wait(int seconds) {
        return new WebDriverWait(this.driver, Duration.ofSeconds(seconds));
    }

    public WebElement waitAndGet(By selector) {
        return waitAndGet(selector, 1);
    }

    public WebElement waitAndGet(By selector, int seconds) {
        WebDriverWait driverWait = wait(seconds);
        return driverWait.until(ExpectedConditions.visibilityOfElementLocated(selector));
    }

    public Function<WebDriver, Boolean> conditionTextChange(By selector, String initialValue) {
        return ExpectedConditions.visibilityOfElementLocated(selector)
            .andThen(
                driver -> {
                    try {
                        String current = driver.findElement(selector).getText();
                        return !current.equals(initialValue);
                    } catch (Exception e) {
                        return false;
                    }
                }
            );
    }

    public void assertContains(String actual, String substring) {
        assertTrue(actual.contains(substring), "'" + actual + "' doesn't contain expected '" + substring + "'");
    }

    public void skipLoginSuggestionPopup() {
        waitAndGet(By.xpath("/html/body/div[1]/div/div[7]/div/div[2]/div/div/div/div/div/div/div/div/div/div/a"), 10);
        var popupOuterSpace = waitAndGet(By.xpath("/html/body/div[1]/div/div[7]/div/div[1]"));
        ((JavascriptExecutor)this.driver).executeScript("arguments[0].click();", popupOuterSpace);
    }
}
