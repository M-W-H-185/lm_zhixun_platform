package com.lm.entity.vo.adminuser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 后台管理员登录Vo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserLoginVo {
    // 用户姓名
    private String username;
    // 密码
    private String password;
    // 验证码
    private String code;
    // 验证码UUID
    private  String codeuuid;
}
