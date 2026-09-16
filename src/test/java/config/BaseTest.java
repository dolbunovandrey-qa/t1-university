package config;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static io.restassured.RestAssured.given;

public abstract class BaseTest {

    protected static final TestConfig CONFIG = ConfigReader.load();
    private final List<Integer> ids = new ArrayList<>();
    public WebDriver driver;
    @BeforeEach
    void configureTest() {
        Configuration.baseUrl = CONFIG.getWebUrl();
        Configuration.timeout = CONFIG.getTimeout();

        RestAssured.baseURI = CONFIG.getApiUrl();

        printConfig();
    }
    @AfterEach
    public void selQuit(){
        if(driver !=null){
            driver.quit();
        }
    }
    @AfterEach
    public void afterEach() {
        closeWebDriver();
    }
    @AfterEach
    void tearDown(){
        for(Integer id : ids) {
            given()
                    .baseUri(CONFIG.getApiUrl())
                    .auth().basic(CONFIG.getAdminUsername(),
                            CONFIG.getAdminPassword())
                    .pathParam("id", id)
                    .log().all()
                    .when()
                    .delete("/goods/{id}")
                    .then()
                    .log().all();
        }
    }
    protected void addForDelete(int id){
        ids.add(id);
    }

    private void printConfig() {
        System.out.println("========== TEST CONFIG ==========");
        System.out.println("Web URL: " + CONFIG.getWebUrl());
        System.out.println("API URL: " + CONFIG.getApiUrl());
        System.out.println("Timeout: " + CONFIG.getTimeout());
        System.out.println("Logging: " + CONFIG.getLogging());
        System.out.println("Start product name: " + CONFIG.getStartProductName());
        System.out.println("Start product price: " + CONFIG.getStartProductPrice());
        System.out.println("=================================");
    }
}