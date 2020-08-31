package com.dialog.esr.tests.common.webdriver.util;

import com.dialog.esr.tests.common.webdriver.env.TestEnv;

import com.dialog.esr.tests.common.webdriver.env.Profile;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.dialog.esr.tests.common.webdriver.env.EnvPropsReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class UserProfile {
    private static final Logger LOG = Logger.getLogger(UserProfile.class.getName());
    private static String USER_PROFILE_FILE;
    private File file;
    protected String userId;
    protected String password;
    protected static String token;
    TestEnv testEnv;
    protected String authorizationUserID;
    protected String authorizationUserPassword;

    @SuppressWarnings("ConstantConditions")
    public UserProfile() {
        if (!EnvPropsReader.isRead) {
            testEnv = new TestEnv();
        }
        USER_PROFILE_FILE = "config/" + TestEnv.getTestEnv() + "-user-profiles.xml";
        file = new File(this.getClass().getClassLoader().getResource(USER_PROFILE_FILE).getFile());
    }

    private ArrayList getProfile() {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        HashMap<String, String> profileMap;
        ArrayList<HashMap<String, String>> profileList = null;
        try {
            DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();
            NodeList profileNodeList = document.getElementsByTagName("user");
            profileList = new ArrayList();
            for (int index = 0; index < profileNodeList.getLength(); index++) {
                Node node = profileNodeList.item(index);
                profileMap = new HashMap<>();
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    profileMap.put("type", element.getAttribute("type"));
                    profileMap.put("username", element.getElementsByTagName("username").item(0).getTextContent());
                    profileMap.put("password", element.getElementsByTagName("password").item(0).getTextContent());
                    profileMap.put("context", element.getElementsByTagName("context").item(0).getTextContent());
                }
                profileList.add(profileMap);
            }
        } catch (ParserConfigurationException | SAXException e) {
            LOG.info("Error parsing document " + e);
        } catch (IOException e) {
            LOG.info("Error parsing file " + e);
        }
        return profileList;
    }

    public void getUserProfile(String context, String type, boolean isSecurityIntegrated) {
        List userList = getProfile();
        for (Object element : userList) {
            if (((HashMap) element).get("type").equals(type) && ((HashMap) element).get("context").equals(context)) {
                this.userId = (String) ((HashMap) element).get("username");
                this.password = (String) ((HashMap) element).get("password");
                if (isSecurityIntegrated) {
                    List tokenList = getTokenList();
                    String tokenUrl = tokenList.get(0) + "token?code=" + tokenList.get(1) + "&grant_type=authorization_code&client_secret=" + tokenList.get(2) + "&redirect_uri=https://google.com&client_id=web&username=" + this.userId + "&password=" + this.password;
                    Response response = RestAssured.given().header("Content-Type", "application/x-www-form-urlencoded").when().post(tokenUrl);
                    setToken(response.jsonPath().get("entity").toString());
                }
            }
        }
    }

    public void getAuthorizationUserProfile(String context, String type, boolean isSecurityIntegrated) {
        List userList = getProfile();
        for (Object element : userList) {
            if (((HashMap) element).get("type").equals(type) && ((HashMap) element).get("context").equals(context)) {
                this.authorizationUserID = (String) ((HashMap) element).get("username");
                this.authorizationUserPassword = (String) ((HashMap) element).get("password");
                if (isSecurityIntegrated) {
                    List tokenList = getTokenList();
                    String tokenUrl = tokenList.get(0) + "token?code=" + tokenList.get(1) + "&grant_type=authorization_code&client_secret=" + tokenList.get(2) + "&redirect_uri=https://google.com&client_id=web&username=" + this.userId + "&password=" + this.password;
                    Response response = RestAssured.given().header("Content-Type", "application/x-www-form-urlencoded").when().post(tokenUrl);
                    setToken(response.jsonPath().get("entity").toString());
                }
            }
        }
    }

    private static void setToken(String entity) {
        token = entity.split("access_token=")[1];
    }

    private static List<String> getTokenList() {
        List<String> tokenList = new ArrayList<>();
        HashMap userAuth = RestAssured.given().when().get(getAuthHost() + "auth?response_type=code&client_id=web").jsonPath().get();
        HashMap entity = (HashMap) userAuth.get("entity");
        tokenList.add(0, Profile.getAuthHost());
        tokenList.add(1, (String) entity.get("authorization_code"));
        tokenList.add(2, (String) entity.get("client_secret"));
        return tokenList;
    }

    private static String getAuthHost() {
        return Profile.getAuthHost();
    }

}