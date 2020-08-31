package com.dialog.esr.tests.ui;

import com.dialog.esr.pages.page;
import com.dialog.esr.tests.common.webdriver.util.GenericUiTestUtil;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.*;

public class test extends GenericUiTestUtil {
    page pa;

    @BeforeMethod(groups = {"BVT", "Regression"})
    public void ProceedToSoap() {
        loadAccountManagerProfile("accountmaanger");
        pa = new page(webDriver);
    }

    @Feature("Flex - Test Flow")
    @Story("AUT-43")
    @Severity(SeverityLevel.BLOCKER)
    @Test(description = "Positive : Submit Test Flow", groups = {"BVT", "Regression"})
    public void testObjectivePage() {
        pa.addDetails();
    }

}
