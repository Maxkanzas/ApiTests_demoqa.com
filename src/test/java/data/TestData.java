package data;

import models.login.LoginRequestBodyModel;

public class TestData {
    public static final String LOGIN = System.getProperty("demoqaLogin");
    public static final String PASSWORD = System.getProperty("demoqaPassword");
    public static LoginRequestBodyModel loginRequestBodyModel = new LoginRequestBodyModel(LOGIN, PASSWORD);
}
