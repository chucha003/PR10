import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.*;

public class CardOrderFormTests {
    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        // Настройка WebDriver с использованием WebDriverManager и запуск в headless-режиме
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);

        // Переходим на страницу тестируемого приложения
        driver.get("http://localhost:9999");
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


    @Test
    public void testInvalidNameValidation() {
        // Находим элементы формы
        WebElement nameInput = driver.findElement(By.cssSelector("[data-test-id='name'] input"));
        WebElement phoneInput = driver.findElement(By.cssSelector("[data-test-id='phone'] input"));
        WebElement agreementCheckbox = driver.findElement(By.cssSelector("[data-test-id='agreement']"));
        WebElement submitButton = driver.findElement(By.cssSelector("button.button"));

        // Заполняем форму некорректным именем
        nameInput.sendKeys("John Doe");
        phoneInput.sendKeys("+79270000000");
        agreementCheckbox.click();
        submitButton.click();

        // Проверяем сообщение об ошибке валидации поля имени
        WebElement errorMessage = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));
        assertTrue(errorMessage.isDisplayed(), "Сообщение об ошибке валидации не отображается.");
    }

    @Test
    public void testInvalidName() {
        WebElement nameInput = driver.findElement(By.cssSelector("[data-test-id='name'] input"));
        WebElement continueButton = driver.findElement(By.tagName("button"));

        nameInput.sendKeys("John Doe");
        continueButton.click();

        WebElement errorMessage = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));
        assertTrue(errorMessage.isDisplayed(), "Error message should be displayed for invalid name input.");
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", errorMessage.getText());
    }

}
