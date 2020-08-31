
package com.dialog.esr.tests.common.webdriver.db;

import com.dialog.esr.tests.common.webdriver.env.TestEnv;
import com.dialog.esr.tests.common.webdriver.util.PropertyUtil;

import java.util.ArrayList;
import java.util.List;

public enum Databases {
    ESR_PRICECAL("dialog.esr.db.host", "dialog.esr.prise.dbname", "dialog.esr.prise.username", "dialog.esr.prise.password");

    private List<String> dbList;

    Databases(String host, String dbName, String user, String pass) {
        PropertyUtil propertyUtil = new PropertyUtil("config/" + TestEnv.getTestEnv() + ".properties");
        List<String> list = new ArrayList<>();
        list.add(propertyUtil.loadProperty(host));
        list.add(propertyUtil.loadProperty(dbName));
        list.add(propertyUtil.loadProperty(user));
        list.add(propertyUtil.loadProperty(pass));
        this.dbList = list;
    }

    public List<String> database() {
        return dbList;
    }
}
