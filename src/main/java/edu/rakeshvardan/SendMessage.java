package edu.rakeshvardan;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;

public class SendMessage {

    private WebDriver driver;
    private static final int WAIT_TIME = 5000;

    @BeforeClass
    public void setUp() {
        this.setUpChromeDriver();
        open("https://web.whatsapp.com/");
    }

    @Test
    public void potti() {
        Selenide.sleep(20000);
//        String quote = this.getQuote();
//        this.sendText("పొట్టి", "\"" + quote + "\" \n Good Morning");
        this.sendText("పొట్టి", "Chalo, lets watch a movie!");
    }

    @Test(enabled = false)
    public void shilpa() {
        Selenide.sleep(20000);
        this.sendText("Shilpa", "Good Morning!");
    }

    @Test(enabled = false)
    public void telenorGroup() {
        Selenide.sleep(20000);
        this.sendText("Telenor FRIENDS", "Good Morning!");
    }

    private void sendText(String title, String message) {
        this.getContact(title).waitUntil(visible, WAIT_TIME).shouldBe(enabled).click();
        this.getTextBox().waitUntil(visible, WAIT_TIME).shouldBe(enabled).val(message);
        this.getSendButton().waitUntil(visible, WAIT_TIME).shouldBe(enabled).click();
    }

    private SelenideElement getContact(String title) {
        return $(By.xpath(String.format("//span[contains(@title,'%s')]", title)));
    }

    private SelenideElement getTextBox() {
        return $(By.xpath("//div[contains(text(), 'Type a message')]/following-sibling::div"));
    }

    private SelenideElement getSendButton() {
        return $(By.xpath("//span[@data-icon='send']/ancestor::button"));
    }

    @AfterClass
    private void tearDown() {
        if (null != driver) {
            driver.close();
            driver.quit();
        }
    }

    private void setUpChromeDriver() {
        System.setProperty("webdriver.chrome.driver", "./src/main/resources/driver/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--disable-extensions");
        options.addArguments("user-data-dir=./src/main/resources/data");
        driver = new ChromeDriver(options);
        WebDriverRunner.setWebDriver(driver);
    }

    private String getQuote() {
        return given().contentType("application/json").when().get("https://quotes.rest/qod?category=inspire&language=en").then()
                .extract().path("contents.quotes[0].quote").toString();
    }

    private String getGoodQuote() {
        return given().when().get("https://favqs.com/api").path("quote.id").toString();
    }
}
