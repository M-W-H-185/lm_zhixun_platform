package com.lm.common.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 跳过校验token 在拦截器中写了 检测到这个注解就跳过
 */
@Target({ ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)// 你可以通过反射获取到注解消息
public @interface IgnoreToken {
}
