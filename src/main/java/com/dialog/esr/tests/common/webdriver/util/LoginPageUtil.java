
package com.dialog.esr.tests.common.webdriver.util;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.logging.Logger;

public class LoginPageUtil extends BasePage {
    private static final Logger LOG = Logger.getLogger(LoginPageUtil.class.getName());

    @FindBy(id="username")
    WebElement username;

    @FindBy(id="password")
    WebElement password;

    @FindBy(xpath = "//button[@class='btn btn-primary btn-lg login-btn']")
    WebElement button;

    @FindBy(xpath = "//a[contains(text(),'Forgot Password ?')]")
    WebElement forgotPasswordLink;

    public LoginPageUtil(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void appLogin(String id, String password) {
        this.username.sendKeys(id);
        this.password.sendKeys(password);
        this.password.sendKeys(Keys.TAB);
        this.password.sendKeys(Keys.ENTER);
    }
}