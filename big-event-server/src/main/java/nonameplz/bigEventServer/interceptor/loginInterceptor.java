package nonameplz.bigEventServer.interceptor;

import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import nonameplz.bigEventServer.pojo.Result;
import nonameplz.bigEventServer.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;


@Slf4j
@Component
public class loginInterceptor implements HandlerInterceptor {
    @Autowired
    private userService uService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String url = request.getRequestURI();

        log.info("请求路径为:{}",url);

        String token = request.getHeader("Authorization");
//        log.info("请求头为{}",token);

        if (!StringUtils.hasLength(token)) {
            log.info("token不存在");

            Result error = Result.error("NOT_LOGIN");

            String NOT_LOGIN = JSONObject.toJSONString(error);

            response.getWriter().write(NOT_LOGIN);

            return false;
        }

        if (!uService.checkToken(token)){
            log.info("token失效");

            Result error = Result.error("NOT_LOGIN");

            String NOT_LOGIN = JSONObject.toJSONString(error);

            response.getWriter().write(NOT_LOGIN);
            return false;
        }

        return true;
    }
}
