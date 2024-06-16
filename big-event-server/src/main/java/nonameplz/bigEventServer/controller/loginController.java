package nonameplz.bigEventServer.controller;

import lombok.extern.slf4j.Slf4j;
import nonameplz.bigEventServer.pojo.Result;
import nonameplz.bigEventServer.pojo.token;
import nonameplz.bigEventServer.pojo.user;
import nonameplz.bigEventServer.service.userService;
import nonameplz.bigEventServer.utils.randomStringGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
public class loginController {
    @Autowired
    private userService uService;

    @PostMapping
    public Result registerUser(@RequestBody user u) {
        log.info("用户登录账号:{},密码:{}",u.getUserName(),u.getPassword());

        user logUser=uService.login(u);

        if (logUser != null){

            token t = uService.getUserToken(logUser);
            if (t == null){
                //用户从未登录过时
                t = new token();
                t.setToken(randomStringGetter.generateTokenByShuffle());
                t.setUserUUID(logUser.getUserUUID());
                t.setExpireTime(LocalDateTime.now().plusDays(7));
                t.setCreateTime(LocalDateTime.now());

                uService.saveUserToken(t);

                return Result.success(t.getToken());
            }
            if(t.getExpireTime().isBefore(LocalDateTime.now())) {
                //用户token过期
                t.setToken(randomStringGetter.generateTokenByShuffle());
                t.setExpireTime(LocalDateTime.now().plusDays(7));
                t.setCreateTime(LocalDateTime.now());

                uService.updateUserToken(t);

                return Result.success(t.getToken());
            }

            t.setExpireTime(LocalDateTime.now().plusDays(7));
            uService.updateUserToken(t);
            return Result.success(t.getToken());
        }
        return Result.error("登录失败,用户名或密码错误!");
    }
}
