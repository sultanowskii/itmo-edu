package org.example.mainpage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.common.Kit;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CategoryTest {
    @ParameterizedTest
    @MethodSource("org.example.common.Kit#driversAsArguments")
    public void categoryHouse(WebDriver driver) {
        var kit = new Kit(driver);

        try {
            driver.get("https://market.yandex.ru");

            kit.skipLoginSuggestionPopup();
    
            var openCategoriesButton = kit.waitAndGet(By.xpath("//*[@id=\"/content/header/header/catalogEntrypoint\"]"));
            openCategoriesButton.click();

            var houseCategorySelect = kit.waitAndGet(By.xpath("/jhgjh/body/div[10]/div/div/div/div/div/div/div[1]/div/ul/li[2]/a"), 3);
            assertEquals("Дом", houseCategorySelect.getText());
            houseCategorySelect.click();

            kit.assertContains(driver.getCurrentUrl(), "https://market.yandex.ru/catalog--tovary-dlia-doma"); 
        } finally {
            driver.quit();
        }
    }
}
