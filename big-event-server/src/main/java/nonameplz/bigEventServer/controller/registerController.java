package nonameplz.bigEventServer.controller;

import lombok.extern.slf4j.Slf4j;
import nonameplz.bigEventServer.pojo.Result;
import nonameplz.bigEventServer.pojo.user;
import nonameplz.bigEventServer.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/register")
@CrossOrigin(origins = "*")
public class registerController {
    @Autowired
    private userService uService;

    @PostMapping
    public Result registerUser(@RequestBody user u) {
        if (uService.register(u)){
            return Result.success();
        }else {
            return Result.error("用户名已被使用!");
        }
    }
}
