
package com.dialog.esr.tests.common.webdriver.util;


import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.dialog.esr.tests.common.webdriver.env.EnvPropsReader;
import com.dialog.esr.tests.common.webdriver.env.TestEnv;
import com.dialog.esr.tests.common.webdriver.env.Profile;

import static com.dialog.esr.tests.common.webdriver.env.TestEnv.browser;
import static com.dialog.esr.tests.common.webdriver.env.TestEnv.testType;

public class GenericUiTestUtil extends UserProfile {
    private static final Logger LOG = Logger.getLogger(GenericUiTestUtil.class.getName());
    protected WebDriver webDriver;

    @BeforeSuite(groups = {"BVT", "Regression"})
    public void setUp() {
        if (!EnvPropsReader.isRead) {
            testEnv = new TestEnv();
        }
        LOG.info("Application running Browser is " + StringUtils.upperCase(browser) + ", Test Env is " + StringUtils.upperCase(TestEnv.getTestEnv()) + " and Test Type is " + StringUtils.upperCase(testType));
    }

    @BeforeMethod(groups = {"BVT", "Regression"})
    public void init() {
        webDriver = WebDriverFactory.getInstance(browser, testType);
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        webDriver.manage().window().maximize();
        webDriver.get(Profile.getWebPortal());
    }

    protected void loadProfile() {
        LoginPageUtil loginPage = new LoginPageUtil(webDriver);
        loginPage.appLogin(userId, password);
    }

    protected void loadAuthorizationProfile() {
        AuthPageUtil authorizationPage = new AuthPageUtil(webDriver);
        authorizationPage.authorizationLogin(authorizationUserID, authorizationUserPassword);
    }

    public void loadAccountManagerProfile(String context) {
        LOG.info("Loading Account Manager User Profile");
        getUserProfile(context, "doctor", false);
        loadProfile();
    }

    public void loadEseProfile(String context) {
        LOG.info("Loading Enterprise Solutions Engineer User Profile");
        getUserProfile(context, "doctor", false);
        loadProfile();
    }

    public void loadBusinessControlerProfile(String context) {
        LOG.info("Loading Business Controller User Profile");
        getUserProfile(context, "doctor", false);
        loadProfile();
    }

    @AfterMethod
    public void tearDown() {
        webDriver.quit();
    }
}