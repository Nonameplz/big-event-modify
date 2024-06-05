package nonameplz.bigEventServer.service.impl;

import nonameplz.bigEventServer.mapper.tokenMapper;
import nonameplz.bigEventServer.mapper.userMapper;
import nonameplz.bigEventServer.pojo.token;
import nonameplz.bigEventServer.pojo.user;
import nonameplz.bigEventServer.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class userServiceImpl implements userService {
    @Autowired
    private userMapper uMapper;
    @Autowired
    private tokenMapper tMapper;

    @Override
    public boolean register(user u) {
        if (uMapper.register_isExist(u) != null) {
            return false;
        } else {
            long time = System.currentTimeMillis();
            int random = (int) (Math.random() * Integer.MAX_VALUE);
            UUID uuid = new UUID(time, random);

            u.setUserUUID(uuid.toString());
            u.setPhoneNumber(null);
            u.setUserImageURL(null);
            u.setCreateTime(LocalDateTime.now());
            u.setUpdateTime(LocalDateTime.now());
            uMapper.register(u);
            return true;
        }
    }

    @Override
    public user login(user u) {
        return uMapper.userLogin(u);
    }

    @Override
    public token getUserToken(user u) {
        return tMapper.getUserToken(u);
    }

    @Override
    public user getUserByToken(String token) {
        return tMapper.getUserByToken(token);
    }

    @Override
    public void saveUserToken(token t) {
        tMapper.saveUserToken(t);
    }

    @Override
    public void updateUserToken(token t) {
        tMapper.updateUserToken(t);
    }


}
