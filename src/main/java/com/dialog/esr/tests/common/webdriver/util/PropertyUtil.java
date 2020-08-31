
package com.dialog.esr.tests.common.webdriver.util;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertyUtil {
    private static final Logger LOG = Logger.getLogger(PropertyUtil.class.getName());
    private String PROP_FILE;

    public PropertyUtil(String propertyFile) {
        this.PROP_FILE = propertyFile;
    }

    public String loadProperty(String name) {
        Properties props = new Properties();
        try {
            props.load(getClass().getClassLoader().getResourceAsStream(PROP_FILE));
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error occurred while loading property ", e);
        }
        String value = "";
        if (name != null) {
            value = props.getProperty(name);
        }
        return value;
    }

}