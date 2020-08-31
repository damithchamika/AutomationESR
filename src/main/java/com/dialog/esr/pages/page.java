package com.dialog.esr.pages;

import com.dialog.esr.tests.common.webdriver.util.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class page extends BasePage {

    public page(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//h4[contains(text(),'ESR')]")
    WebElement flexMainWidget;

    public void addDetails() {
        flexMainWidget.click();
    }
}
