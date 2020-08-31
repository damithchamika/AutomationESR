
package com.dialog.esr.tests.common.webdriver.env;

import java.util.HashMap;
import java.util.List;

public class Profile {
    public static String seleniumHub = null;
    public static String webPortal = null;
    public static String auth = null;
    public static HashMap<String, List<String>> databases = null;

    public HashMap<String, List<String>> getDatabases() {
        return databases;
    }

    public static void setDatabases(HashMap<String, List<String>> dbList) {
        Profile.databases = dbList;
    }

    public static String getSeleniumHub() {
        return seleniumHub;
    }

    public static void setSeleniumHub(String seleniumHub) {
        Profile.seleniumHub = seleniumHub;
    }

    public static String getWebPortal() {
        return webPortal;
    }

    public static void setWebPortal(String webPortal) {
        Profile.webPortal = webPortal;
    }

    public static String getAuthHost() {
        return auth;
    }

    public static void setAuthHost(String auth) {
        Profile.auth = auth;
    }

}