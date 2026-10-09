package config;

import io.qameta.allure.Attachment;
import ui.base.UiBaseTest;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.nio.charset.StandardCharsets;

/** Снимает диагностику Selenium до закрытия браузера в @AfterEach. */
public class SeleniumFailureAttachments implements AfterTestExecutionCallback {
    @Override
    public void afterTestExecution(ExtensionContext context) {
        if (context.getExecutionException().isPresent()
                && context.getRequiredTestInstance() instanceof UiBaseTest test
                && test.driver != null) {
            screenshot(test);
            pageSource(test);
        }
    }

    @Attachment(value = "Selenium: скриншот при падении", type = "image/png")
    private byte[] screenshot(UiBaseTest test) {
        return ((TakesScreenshot) test.driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Selenium: HTML при падении", type = "text/html", fileExtension = ".html")
    private byte[] pageSource(UiBaseTest test) {
        return test.driver.getPageSource().getBytes(StandardCharsets.UTF_8);
    }
}
