package com.dialog.esr.tests.common.webdriver.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AuthPageUtil extends BasePage {

    @FindBy(xpath = "//input[@name='userName']")
    private WebElement userNameInApprovalModal;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement passwordInApprovalModal;

    @FindBy(xpath = "//button[contains(text(),'Approve')]")
    private WebElement approveButton;

    public AuthPageUtil(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void authorizationLogin(String authorizationId, String authorizationPassword) {
        this.userNameInApprovalModal.sendKeys(authorizationId);
        this.passwordInApprovalModal.sendKeys(authorizationPassword);
        approveButton.click();
    }

}
