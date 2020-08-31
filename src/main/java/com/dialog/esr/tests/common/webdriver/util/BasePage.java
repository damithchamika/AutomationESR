
package com.dialog.esr.tests.common.webdriver.util;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

public abstract class BasePage {
    private static final Logger LOG = Logger.getLogger(BasePage.class.getName());
    protected final String MAX_WAIT = "30000";
    protected final String AVG_WAIT = "10000";
    protected final String MIN_WAIT = "5000";
    protected WebDriver webDriver;
    protected WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.webDriver = driver;
    }

    public void waitForElementToPresent(WebElement webElement) {
        if (!webElement.isDisplayed() && !webElement.isEnabled()) {
            new WebDriverWait(webDriver, 10)
                    .until(ExpectedConditions.visibilityOf(webElement));
        }
    }

    public void waitForElementToPresent(WebElement webElement, long time) {
        if (!webElement.isDisplayed() && !webElement.isEnabled()) {
            new WebDriverWait(webDriver, time)
                    .until(ExpectedConditions.visibilityOf(webElement));
        }
    }

    private void wait(String waitTime) {
        try {
            Thread.sleep(Integer.parseInt(waitTime));
        } catch (InterruptedException e) {
            LOG.warning("Error loading on page" + e);
        }
    }

    public void waitForPageLoad(String waitType) {
        switch (waitType) {
            case MAX_WAIT:
                wait(MAX_WAIT);
                break;
            case MIN_WAIT:
                wait(MIN_WAIT);
                break;
            default:
                wait(AVG_WAIT);
        }
    }

    public int navigateToRandomPage(List<WebElement> elementList) {
        int randomPage = getRandomValue(elementList.size());
        elementList.get(randomPage).click();
        LOG.info("Navigate to Page " + randomPage);
        return randomPage;
    }

    public void sendText(WebElement element, String text) {
        if (element.isEnabled()) {
            element.sendKeys(text);
        } else {
            LOG.info(element + " is disabled.");
        }
    }

    public String getCurrantSystemDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public String getCurrantSystemTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }

    protected void clearAndWriteText(WebElement element, String text) {
        if (element.isEnabled()) {
            element.clear();
            element.sendKeys(text);
        } else {
            LOG.info(element + " is disabled.");
        }
    }

    protected void click(WebElement element) {
        if (element.isEnabled()) {
            element.click();
        } else {
            LOG.info(element + " is disabled.");
        }
    }

    protected void waitAndClick(WebElement element, long waitTime) {
        waitForElementToPresent(element, waitTime);
        new WebDriverWait(webDriver, waitTime).until(ExpectedConditions.elementToBeClickable(element));
        try {
            click(element);
        } catch (Throwable t) {
                LOG.info(t.toString());
                LOG.info("Trying to click using jave script executor");
                LOG.info("Element xpath = "+element);
                JavascriptExecutor js = (JavascriptExecutor) webDriver;
                js.executeScript("arguments[0].click();", element);
        }
    }

    protected void waitAndClick(WebElement element, String waitTime) {
        Long waitTimeL = Long.parseLong(waitTime);
        waitForElementToPresent(element, waitTimeL);
        new WebDriverWait(webDriver, waitTimeL).until(ExpectedConditions.elementToBeClickable(element));
        try {
            click(element);
        } catch (Throwable t) {
            LOG.info(t.toString());
            LOG.info("Trying to click using jave script executor for "+element);
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            js.executeScript("arguments[0].click();", element);
        }
    }

    protected String readText(WebElement element) {
        waitForElementToPresent(element);
        String text;
        if (element.getText().trim() != null && !element.getText().trim().isEmpty()) {
            text = element.getText().trim();
        } else {
            text = element.getAttribute("value");
        }
        return text;
    }

    protected void selectValueFromDropDown(WebElement element, String value) {
        if (value != null && !value.isEmpty()) {
            waitForElementToPresent(element);
            Select dropdown = new Select(element);
            LOG.info("Selected "+value);
            dropdown.selectByVisibleText(value);
        }
    }

    protected List<WebElement> getWebElementsForDynamicXpath(String xpathValue, String key, String substituteValue) {
        return webDriver.findElements(By.xpath(xpathValue.replace(key, substituteValue)));
    }

    public String commonFormatCreatedDate(String createDate, String ActualFormat, String ExpectedFormat) {
        SimpleDateFormat expectedDateFormat = null;
        Date date = null;
        try {
            DateFormat formatter = new SimpleDateFormat(ActualFormat);
            date = formatter.parse(createDate);
            expectedDateFormat = new SimpleDateFormat(ExpectedFormat);
        } catch (ParseException ex) {
            LOG.info("Date Format Exception" + ex);
        }
        return expectedDateFormat.format(date).trim();
    }

    protected void selectRandomValueFromList(List<WebElement> list) {
        waitForElementToPresent(list.get(0));
        int listSize = list.size();
        if (listSize > 0) {
            int random = getRandomValue(listSize);
            wait = new WebDriverWait(webDriver, 5);
            wait.until(ExpectedConditions.elementToBeClickable(list.get(random)));
            click(list.get(random));
        } else {
            LOG.info(list + " is not available");
        }
    }

    protected void scrollIntoView(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("arguments[0].scrollIntoView()", element);
    }

    protected int getTableRowCount(List<WebElement> tableRows, WebElement nextButton) {
        int rowCount;
        rowCount = tableRows.size();
        scrollIntoView(nextButton);
        while (nextButton.isEnabled()) {
            waitForElementToPresent(nextButton);
            nextButton.click();
            rowCount = rowCount + tableRows.size();
        }
        return rowCount;
    }

    protected List<HashMap<String, String>> readTableData(List<WebElement> tableRows, List<WebElement> tableHeaders) {
        List<HashMap<String, String>> tableData = new ArrayList<>();
        int rowIndex = 0;
        waitForPageLoad(MIN_WAIT);
        for (WebElement rowElement : tableRows) {
            List<WebElement> tableColumns = rowElement.findElements(By.xpath("div"));
            HashMap<String, String> value = new HashMap<>();
            int colIndex = 0;
            for (WebElement colElement : tableColumns) {
                value.put(tableHeaders.get(colIndex).getText(), colElement.getText());
                colIndex = colIndex + 1;
            }
            tableData.add(rowIndex, value);
            rowIndex = rowIndex + 1;
        }
        return tableData;
    }

    public void moveOverElement(WebElement element, int xOffSet, int yOffSet) {
        Actions actions = new Actions(webDriver);
        actions.clickAndHold(element).moveByOffset(xOffSet, yOffSet).release().perform();
    }

    protected int getRandomValue(int size) {
        return new Random().nextInt(size);
    }

    protected int getRandomNumber(int low, int high) {
        return (new Random()).nextInt(high - low) + low;
    }

    public void selectRecordFromGrid(List<WebElement> gridRows, String searchValue) {
        for (WebElement doctor : gridRows) {
            if (doctor.getText().contains(searchValue)) {
                doctor.click();
                break;
            }
        }
    }

}