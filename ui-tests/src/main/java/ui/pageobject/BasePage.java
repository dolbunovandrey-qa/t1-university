package ui.pageobject;

import config.ConfigReader;
import config.TestConfig;

/** Конфиг общий для всех страниц, селекторы остаются в конкретных Page. */
public abstract class BasePage {
    protected final TestConfig CONFIG = ConfigReader.load();
}
