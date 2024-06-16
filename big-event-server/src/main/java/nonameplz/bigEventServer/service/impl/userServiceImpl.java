package nonameplz.bigEventServer.service.impl;

import nonameplz.bigEventServer.mapper.tokenMapper;
import nonameplz.bigEventServer.mapper.userMapper;
import nonameplz.bigEventServer.pojo.token;
import nonameplz.bigEventServer.pojo.user;
import nonameplz.bigEventServer.service.userService;
import nonameplz.bigEventServer.utils.randomStringGetter;
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
            u.setUserUUID(randomStringGetter.generateUUIDRandom());
            u.setPhoneNumber(null);
            u.setUserImageURL(null);
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

    @Override
    public boolean checkToken(String token) {
        token t = tMapper.getToken(token);
        return t != null && !t.getExpireTime().isBefore(LocalDateTime.now());
    }


}
