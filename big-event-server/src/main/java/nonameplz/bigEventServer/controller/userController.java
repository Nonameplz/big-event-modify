package nonameplz.bigEventServer.controller;

import lombok.extern.slf4j.Slf4j;
import nonameplz.bigEventServer.pojo.Result;
import nonameplz.bigEventServer.pojo.user;
import nonameplz.bigEventServer.service.userService;
import nonameplz.bigEventServer.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/my/userinfo")
@CrossOrigin(origins = "*")
public class userController {

    @Autowired
    private userService uService;

    @GetMapping
    public Result getUser(@RequestHeader HttpHeaders headers) {
        String token = headers.getFirst("Authorization");
        user u = uService.getUserByToken(token);

        Map<String, Object> claims=new HashMap<>();
        claims.put("userName",u.getUserName());
        claims.put("userNickName",u.getUserNickName());
        claims.put("userPic",u.getUserImageURL());
        claims.put("email",u.getEmail());

        String jwt = JwtUtils.generateJwt(claims);

        return Result.success(jwt);
    }
}
