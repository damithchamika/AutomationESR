
package com.dialog.esr.tests.common.webdriver.env;

import com.dialog.esr.tests.common.webdriver.util.PropertyUtil;

public class TestEnv {
    private static final String PROP_FILE = "config/application.properties";
    public static String browser;
    public static String testEnv;
    public static String testType;
    public EnvPropsReader propsReader;
    public PropertyUtil propertyUtil;

    public TestEnv() {
        propertyUtil = new PropertyUtil(PROP_FILE);
        setTestEnv(propertyUtil.loadProperty("test.env"));
        setTestType(propertyUtil.loadProperty("test.type"));
        setBrowser(propertyUtil.loadProperty("test.browser"));
        propsReader = new EnvPropsReader();
    }

    public static String getTestType() {
        return testType;
    }

    public static void setTestType(String testType) {
        TestEnv.testType = testType;
    }

    public static String getTestEnv() {
        return testEnv;
    }

    public static void setTestEnv(String testEnv) {
        TestEnv.testEnv = testEnv;
    }

    public static String getBrowser() {
        return browser;
    }

    public static void setBrowser(String browser) {
        TestEnv.browser = browser;
    }

}
