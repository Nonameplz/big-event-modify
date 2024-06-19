package nonameplz.bigEventServer.service;

import nonameplz.bigEventServer.pojo.token;
import nonameplz.bigEventServer.pojo.user;

public interface userService {
    public boolean register(user u);

    public user login(user u);

    public token getUserToken(user u);

    public user getUserByToken(String token);

    public String getUserUUID(String token);

    public void saveUserToken(token t);

    public void updateUserToken(token t);

    public boolean checkToken(String token);
}
