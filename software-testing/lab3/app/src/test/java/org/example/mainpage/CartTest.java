package org.example.mainpage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.example.common.Kit;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartTest {
    @ParameterizedTest
    @MethodSource("org.example.common.Kit#driversAsArguments")
    public void later(WebDriver driver) {
        var kit = new Kit(driver);

        try {
            driver.get("https://market.yandex.ru");

            kit.skipLoginSuggestionPopup();
    
            var openCartButton = kit.waitAndGet(By.xpath("//*[@id=\"CART_ENTRY_POINT_ANCHOR\"]/a/div"));
            openCartButton.click();

            var laterButton = kit.waitAndGet(By.xpath("//*[@id=\"/content/goToAuthPopup\"]/div/div/div/button[1]"));
            laterButton.click();

            assertEquals("https://market.yandex.ru/", driver.getCurrentUrl(), driver.getCurrentUrl()); 
        } finally {
            driver.quit();
        }
    }

    @ParameterizedTest
    @MethodSource("org.example.common.Kit#driversAsArguments")
    public void login(WebDriver driver) {
        var kit = new Kit(driver);

        try {
            driver.get("https://market.yandex.ru");

            kit.skipLoginSuggestionPopup();
    
            var openCartButton = kit.waitAndGet(By.xpath("//*[@id=\"CART_ENTRY_POINT_ANCHOR\"]/a/div"));
            openCartButton.click();

            var laterButton = kit.waitAndGet(By.xpath("//*[@id=\"/content/goToAuthPopup\"]/div/div/div/button[2]"));
            laterButton.click();

            assertNotEquals("https://market.yandex.ru/", driver.getCurrentUrl()); 
        } finally {
            driver.quit();
        }
    }
}
