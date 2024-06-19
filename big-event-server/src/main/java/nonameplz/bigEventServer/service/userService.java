package nonameplz.bigEventServer.service;

import nonameplz.bigEventServer.pojo.token;
import nonameplz.bigEventServer.pojo.user;

public interface userService {
    boolean register(user u);

    user login(user u);

    token getUserToken(user u);

    user getUserByToken(String token);

    String getUserUUID(String token);

    void saveUserToken(token t);

    void updateUserToken(token t);

    boolean checkToken(String token);
}
