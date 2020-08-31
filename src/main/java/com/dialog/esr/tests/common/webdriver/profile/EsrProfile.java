
package com.dialog.esr.tests.common.webdriver.profile;

import com.dialog.esr.tests.common.webdriver.db.Databases;
import com.dialog.esr.tests.common.webdriver.util.PropertyUtil;

import java.util.HashMap;
import java.util.List;

public class EsrProfile {
    private static String adtServiceHost = null;

    public EsrProfile(PropertyUtil propertyUtil) {
        setAdtServiceHost(propertyUtil.loadProperty("adt.service.host"));
    }

    public HashMap<String, List<String>> getEsrDbList() {
        HashMap<String, List<String>> ehrDbList = new HashMap<>();
        ehrDbList.put(Databases.ESR_PRICECAL.name(), Databases.ESR_PRICECAL.database());
        return ehrDbList;
    }

    public static String getAdtServiceHost() {
        return adtServiceHost;
    }

    private static void setAdtServiceHost(String adtServiceHost) {
        EsrProfile.adtServiceHost = adtServiceHost;
    }

}