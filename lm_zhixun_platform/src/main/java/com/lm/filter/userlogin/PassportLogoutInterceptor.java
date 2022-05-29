package com.lm.filter.userlogin;

import com.lm.common.ex.lthrow.ValidatorExceptionThrow;
import com.lm.common.r.UserResultEnum;
import com.lm.tool.LmAssert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 判断用户是否在其他地方登录
 */
@Slf4j
@Component
public class PassportLogoutInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求头的UUID
        String token_uuid = request.getHeader("token_uuid");
        log.info("获取到请求头中的token_uuid--->{}",token_uuid);
        // 如果token_UUID为空，抛出用户未登录
        LmAssert.isNotNull(token_uuid, UserResultEnum.USER_NO_LOGIN);
        // 通过请求头获取token_user_id获取Redis里面的UUID
        String token_user_id = request.getHeader("token_user_id");
        // 通过请求头的用户id去获取Redis里面的UUID
        // 先组装UUID的key
        String tokenUuid_key = "lm:user:login:id:" + token_user_id;
        // 通过key去查询Redis的val
        String db_token_uuid = redisTemplate.opsForValue().get(tokenUuid_key);
        // 如果获取不到则抛出异常未登录
        LmAssert.isNotNull(db_token_uuid, UserResultEnum.USER_NO_LOGIN);
        // 把Redis里面的UUID和请求到的UUID对比
        // 对比失败，则用户在其他地方登录，抛出异常
        if (!db_token_uuid.equals(token_uuid)){
           throw new ValidatorExceptionThrow(UserResultEnum.USER_LOGIN_SAME);
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
