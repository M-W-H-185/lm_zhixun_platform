package com.lm.entity.bo.adminuser;

import com.lm.entity.pojo.adminuser.AdminUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * 管理员后台登录回调
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserLoginBo implements Serializable {
    // 存储jwt 登录秘钥
    private String tokenJj;
    // 用于下线的UUID
    private String tokenUuid;
    // 登录的用户信息
    private AdminUser user;
    // 角色列表
    private List<String> RoleNames;
    // 权限列表
    private List<String> Permissions;
}
