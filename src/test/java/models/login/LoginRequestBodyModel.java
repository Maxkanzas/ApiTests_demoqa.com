package models.login;

import lombok.Data;

@Data
public class LoginRequestBodyModel {
    private final String userName;
    private final String password;
}
