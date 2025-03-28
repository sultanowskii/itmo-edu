package org.example.mainpage;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.common.Kit;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchTest {
    @ParameterizedTest
    @MethodSource("org.example.common.Kit#driversAsArguments")
    public void search(WebDriver driver) {
        var kit = new Kit(driver);
        var prompt = "WH-1000XM4";

        try {
            driver.get("https://market.yandex.ru");

            kit.skipLoginSuggestionPopup();
    
            var searchInput = kit.waitAndGet(By.xpath("/html/body/div[1]/div/div[1]/div[2]/div/header/div[1]/div/div/div[2]/div[1]/div/div/form/div[1]/div/div/div/div/input[1]"));
            searchInput.click();
            searchInput.sendKeys(prompt);

            var searchButton = kit.waitAndGet(By.xpath("/html/body/div[1]/div/div[1]/div[2]/div/header/div[1]/div/div/div[2]/div[1]/div/div/form/div[1]/button"));
            searchButton.click();

            kit.assertContains(driver.getCurrentUrl(), "https://market.yandex.ru/search?text=" + prompt);

            var title = kit.waitAndGet(By.xpath("/html/body/div[1]/div/div[2]/div/div[1]/div[3]/div/div/div/h1"), 3);
            kit.assertContains(title.getText(), prompt + " в");
        } finally {
            driver.quit();
        }
    }

    @ParameterizedTest
    @MethodSource("org.example.common.Kit#driversAsArguments")
    public void searchEmpty(WebDriver driver) {
        var kit = new Kit(driver);

        try {
            driver.get("https://market.yandex.ru");

            kit.skipLoginSuggestionPopup();

            var searchButton = kit.waitAndGet(By.xpath("/html/body/div[1]/div/div[1]/div[2]/div/header/div[1]/div/div/div[2]/div[1]/div/div/form/div[1]/button"));
            searchButton.click();

            assertEquals("https://market.yandex.ru/", driver.getCurrentUrl());
        } finally {
            driver.quit();
        }
    }
}
