package data;

import models.login.LoginRequestBodyModel;

public class TestData {
    private static String LOGIN = "max";
    private static String PASSWORD = "mMiu4*3hPK3U*C@";
    public static LoginRequestBodyModel loginRequestBodyModel = new LoginRequestBodyModel(LOGIN, PASSWORD);
    public static final String AUTH_DATA = "{\"userName\":\"max\",\"password\":\"mMiu4*3hPK3U*C@\"}";
    public static final String ADD_BOOK_DATA = "{\"userId\": \"95e7f595-9f05-420c-9fd0-436cb2d09cf9\", \"collectionOfIsbns\": [{\"isbn\": \"9781449365035\"}]}";
    public static final String ADD_BOOK_DATA_OTHER = "{\"userId\": \"95e7f595-9f05-420c-9fd0-436cb2d09cf9\", \"collectionOfIsbns\": [{\"isbn\": \"9781491904244\"}]}";
    public static final String DELETE_BOOK_DATA = "{\"isbn\": \"9781491904244\", \"userId\": \"95e7f595-9f05-420c-9fd0-436cb2d09cf9\"}";

//    public static final String bookStoreLogin = System.getProperty("bookStoreLogin");
//    public static final String bookStorePassword = System.getProperty("bookStorePassword");
//    public static final String isbn = "9781449365035";
//    public static final String bookName = "Speaking JavaScript";
}
