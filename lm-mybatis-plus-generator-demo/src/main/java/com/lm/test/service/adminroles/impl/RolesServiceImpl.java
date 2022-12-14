package com.lm.test.service.adminroles.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.lm.test.mapper.AdminRolesMapper;
import com.lm.test.service.adminroles.AdminRolesService;
import com.lm.test.entity.pojo.adminroles.AdminRoles;
import com.lm.test.entity.vo.adminroles.AdminRolesVo;
import com.lm.test.entity.bo.adminroles.AdminRolesBo;
import com.lm.tool.LmAssert;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
/**
*  服务实现类
*
* @author Lm
* @since 2022-09-11
*/
@Service
public class RolesServiceImpl extends ServiceImpl<AdminRolesMapper, AdminRoles> implements AdminRolesService {

    /**
    * 查询后台用户管理管理列表信息
    * @method: findAdminRolesList
    * @result : List<AdminRoles>
    * 创建人:Lm
    * 创建时间：2022-09-11
    * @return List<AdminRolesBo>
    */
    public List<AdminRolesBo> findAdminRolesList(){
        // 1：设置查询条件
        LambdaQueryWrapper<AdminRoles> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 2：查询发布的 0 未发布  1 发布
        lambdaQueryWrapper.eq(AdminRoles::getStatus, 1);
        lambdaQueryWrapper.eq(AdminRoles::getIsdelete, 0);
        List<AdminRoles> list = this.list(lambdaQueryWrapper);
        List<AdminRolesBo> list_bo = new ArrayList<>();
            list.forEach(item->{
            AdminRolesBo bo = new AdminRolesBo();
            BeanUtils.copyProperties(item,bo);
            list_bo.add(bo);
        });
        return list_bo;
    }

    /**
    * 查询后台用户管理管理列表信息并分页
    * 方法名：findAdminRoles<br/>
    * 创建人：Lm <br/>
    * 时间：2022-09-11<br/>
    * @param adminrolesVo
    * @return IPage<AdminRolesBo><br />
    */
    @Override
    public IPage<AdminRolesBo> findAdminRolesPage(AdminRolesVo adminrolesVo){
        // 先设置分页信息
        Page<AdminRoles> page = new Page<>(adminrolesVo.getPageNo(),adminrolesVo.getPageSize());
        // 设置条件查询
        LambdaQueryWrapper<AdminRoles> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 2：查询发布的 0 未发布  1 发布
        lambdaQueryWrapper.eq(AdminRoles::getStatus, 1);
        lambdaQueryWrapper.eq(AdminRoles::getIsdelete, 0);
        lambdaQueryWrapper.and(LmAssert.isNotEmpty(adminrolesVo.getKeyword()) , wrapper->{wrapper
            .like(AdminRoles::getUsername, adminrolesVo.getKeyword())
            .or()
            .like(AdminRoles::getAccount, adminrolesVo.getKeyword());
        });
        // 根据时间排降序
        lambdaQueryWrapper.orderByDesc(AdminRoles::getCreateTime);
        // 查询分页返回
        IPage<AdminRoles> results = this.page(page, lambdaQueryWrapper);
        IPage<AdminRolesBo> results_bo = new Page<>();
        ArrayList<AdminRolesBo> results_list_bo = new ArrayList<>();
        BeanUtils.copyProperties(results,results_bo,"records");
        results.getRecords().forEach(item->{
        AdminRolesBo adminrolesBo = new AdminRolesBo();
            BeanUtils.copyProperties(item,adminrolesBo);
            results_list_bo.add(adminrolesBo);
        });
        results_bo.setRecords(results_list_bo);
        return results_bo;
    }

    /**
    * 保存&修改后台用户管理管理
    * 方法名：saveupdateAdminRoles<br/>
    * 创建人：Lm <br/>
    * 时间：2022-09-11<br/>
    * @param adminroles
    * @return AdminRolesBo<br />
    */
    @Override
    public AdminRolesBo saveupdateAdminRoles(AdminRoles adminroles){
        boolean flag = this.saveOrUpdate(adminroles);
        AdminRolesBo adminrolesBo = new AdminRolesBo();
        BeanUtils.copyProperties(adminroles,adminrolesBo);
        return flag ? adminrolesBo : null;
    }

    /**
    * 根据Id删除后台用户管理管理
    * 方法名：deleteAdminRolesById<br/>
    * 创建人：Lm <br/>
    * 时间：2022-09-11<br/>
    * @param id
    * @return int <br />
    */
    @Override
    public int deleteAdminRolesById(Long id){
        boolean b = this.removeById(id);
        return b ? 1 : 0;
    }

    /**
    * 根据Id查询后台用户管理管理明细信息
    * 方法名：getAdminRolesById<br/>
    * 创建人：Lm <br/>
    * 时间：2022-09-11<br/>
    * @param id
    * @return AdminRolesBo <br />
    */
    @Override
    public AdminRolesBo getAdminRolesById(Long id){
        AdminRoles byId = this.getById(id);
        AdminRolesBo adminrolesBo = new AdminRolesBo();
        BeanUtils.copyProperties(byId,adminrolesBo);
        return adminrolesBo;
    }

    /**
    * 根据后台用户管理管理ids批量删除后台用户管理管理
    * 方法名：delBatchAdminRoles<br/>
    * 创建人：Lm <br/>
    * 时间：2022-09-11<br/>
    * @param ids
    * @return boolean <br />
    */
    @Override
    public boolean delBatchAdminRoles(String ids){
        // 1 : 把数据分割
        String[] strings = ids.split(",");
        // 2: 组装成一个List<AdminRoles>
        List<AdminRoles> adminrolesList = Arrays.stream(strings).map(idstr -> {
            AdminRoles adminroles = new AdminRoles();
            adminroles.setId(new Long(idstr));
            adminroles.setIsdelete(1);
            return adminroles;
        }).collect(Collectors.toList());
        // 3: 批量删除
        return this.updateBatchById(adminrolesList);
    }

}
