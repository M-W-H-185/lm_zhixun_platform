package com.lm.controller.platform.admin_api.adminuser;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lm.common.anno.IgnoreToken;
import com.lm.common.ex.lthrow.ValidatorExceptionThrow;
import com.lm.common.r.UserResultEnum;
import com.lm.controller.platform.admin_api.BaseController;
import com.lm.entity.bo.adminuser.AdminUserBo;
import com.lm.entity.pojo.adminuser.AdminUser;
import com.lm.entity.vo.adminuser.AdminUserQueryVo;
import com.lm.entity.vo.adminuser.AdminUserRegVo;
import com.lm.entity.vo.adminuser.AdminUserUpdateVo;
import com.lm.service.adminuser.IAdminUserService;
import com.lm.tool.LmAssert;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 后台用户管理web Api
 * @author Lm
 * @since 2022-09-08
 */
@Controller
@Slf4j
public class AdminUserController extends BaseController {
    @Autowired
    private IAdminUserService adminuserService;

    /**
    * 查询后台用户管理列表信息
    ** @path : /admin/adminuser/load
    * @method: findAdminUserList
    * @result : List<AdminUser>
    * 创建人:Lm
    * 创建时间：2022-09-08
    * @return
    */
    @PostMapping("/adminuser/load")
    @ResponseBody
    public List<AdminUserBo> findadminuserList() {
        return adminuserService.findAdminUserList();
    }


    /**
    * 查询后台用户管理列表信息并分页
    * @path : /admin/adminuser/list
    * @method: findAdminUsers
    * @param : AdminUserQueryVo
    * @result : IPage<AdminUserBo>
    * 创建人:Lm
    * 创建时间：2022-09-08
    * @version 1.0.0
    */
    @PostMapping("/adminuser/list")
    @ResponseBody
//    @IgnoreToken
    public IPage<AdminUserBo> findAdminUsers(@RequestBody AdminUserQueryVo adminuserQueryVo){
        return adminuserService.findAdminUserPage(adminuserQueryVo);
    }

    /**
    * 保存和修改后台用户管理
    * @method: saveupdate
    * @path : /admin/adminuser/save
    * @param : AdminUser
    * @result : AdminUser
    * 创建人:Lm
    * 创建时间：2022-09-08
    * @version 1.0.0
    */
    @PostMapping("/adminuser/saveupdate")
    @ResponseBody
//    @IgnoreToken
    public AdminUserBo saveupdateAdminUser(@RequestBody @Validated AdminUserRegVo adminUserRegVo) {
        return adminuserService.saveupdateAdminUser(adminUserRegVo);
    }

    /**
    * 根据后台用户管理id查询明细信息
    * @method: get/{id}
    * @path : /admin/adminuser/get/{id}
    * @param : id
    * @result : AdminUserBo
    * 创建人:Lm
    * 创建时间：2022-09-08
    * @version 1.0.0
    */
    @GetMapping("/adminuser/get/{id}")
    @ResponseBody
    public AdminUserBo getAdminUserById(@PathVariable("id") String id) {
        if(LmAssert.isEmpty(id)){
            throw new ValidatorExceptionThrow(UserResultEnum.ID_NOT_EMPTY);
        }
        return adminuserService.getAdminUserById(new Long(id));
    }

    /**
    * 根据后台用户管理id删除后台用户管理
    * @method: delete/{id}
    * @path : /admin/adminuser/delete/{id}
    * @param : id
    * @result : int
    * 创建人:Lm
    * 创建时间：2022-09-08
    * @version 1.0.0
    */
    @PostMapping("/adminuser/delete/{id}")
    @ResponseBody
    public int deleteAdminUserById(@PathVariable("id") String id) {
        if(LmAssert.isEmpty(id)){
            throw new ValidatorExceptionThrow(UserResultEnum.ID_NOT_EMPTY);
        }
        return adminuserService.deleteAdminUserById(new Long(id));
    }

    /**
    * 根据后台用户管理ids批量删除后台用户管理
    * @method: adminuser/delBatch
    * @path : /admin/adminuser/delBatch
    * @param : AdminUserQueryVo
    * @result : boolean
    * 创建人:Lm
    * 创建时间：2022-09-08
    * @version 1.0.0
    */
    @PostMapping("/adminuser/delBatch")
    public boolean delAdminUser(@RequestBody AdminUserQueryVo adminuserQueryVo) {
        log.info("你要批量删除的IDS是:{}", adminuserQueryVo.getBatchIds());
        if (LmAssert.isEmpty(adminuserQueryVo.getBatchIds())) {
            throw new ValidatorExceptionThrow(UserResultEnum.ID_NOT_EMPTY);
        }
        final val b = adminuserService.delBatchAdminUser(adminuserQueryVo.getBatchIds());
        return b;

    }
    /**
     * 后台用户状态更新
     * @method: adminuser/update
     * @path : /admin/adminuser/update
     * @param : adminUserRegVo
     * @result : boolean
     * 创建人:lm
     * 创建时间：2022-09-13
     * @version 1.0.0
     */
    @PostMapping("/adminuser/update")
    public boolean updateAdminUser(@RequestBody AdminUserUpdateVo adminUserUpdateVo) {
        return adminuserService.updateAdminUser(adminUserUpdateVo);
    }
}
