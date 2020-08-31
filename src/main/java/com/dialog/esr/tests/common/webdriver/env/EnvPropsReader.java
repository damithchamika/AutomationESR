
package com.dialog.esr.tests.common.webdriver.env;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import com.dialog.esr.tests.common.webdriver.profile.EsrProfile;
import com.dialog.esr.tests.common.webdriver.util.PropertyUtil;

public class EnvPropsReader {
    private static final Logger LOG = Logger.getLogger(EnvPropsReader.class.getName());
    public static boolean isRead = false;
    Profile profile;
    EsrProfile esrProfile;
    PropertyUtil propertyUtil;

    public EnvPropsReader() {
        profile = new Profile();
        propertyUtil = new PropertyUtil("config/" + TestEnv.getTestEnv() + ".properties");
        esrProfile = new EsrProfile(propertyUtil);
        profile.setAuthHost(propertyUtil.loadProperty("flex.auth"));
        profile.setSeleniumHub(propertyUtil.loadProperty("selenium.hub"));
        profile.setWebPortal(propertyUtil.loadProperty("flex.web.portal"));
        profile.setDatabases(getDbList());
        isRead = true;
    }

    private HashMap<String, List<String>> getDbList() {
        HashMap<String, List<String>> dbList = new HashMap<>();
        dbList.putAll(esrProfile.getEsrDbList());
        return dbList;
    }

}