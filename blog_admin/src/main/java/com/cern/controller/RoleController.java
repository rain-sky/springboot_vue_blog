package com.cern.controller;

import com.cern.domain.ResponseResult;
import com.cern.domain.dto.ChangeRoleStatusDto;
import com.cern.domain.entity.Role;
import com.cern.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/role")
@Api(tags = "系统-后台：角色接口")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    @ApiOperation("后台：获取角色列表")
    public ResponseResult list(Role role, Integer pageNum, Integer pageSize) {
        return roleService.selectRolePage(role,pageNum,pageSize);
    }


    @PutMapping("/changeStatus")
    @ApiOperation("后台：修改角色状态")
    public ResponseResult changeStatus(@RequestBody ChangeRoleStatusDto roleStatusDto){
        Role role = new Role();
        role.setId(roleStatusDto.getRoleId());
        role.setStatus(roleStatusDto.getStatus());
        return ResponseResult.okResult(roleService.updateById(role));
    }

    /**
     * 请求体如下所示
     * {
     *     "roleName":"测试新增角色",
     *     "roleKey":"wds",
     *     "roleSort":0,
     *     "status":"0",
     *     "menuIds":[
     *         "1",
     *         "100"
     *     ],
     *     "remark":"我是角色备注"
     * }
     */
    @PostMapping
    @ApiOperation("后台：新增角色")
    public ResponseResult add(@RequestBody Role role) {
        roleService.insertRole(role);
        return ResponseResult.okResult();
    }

    @GetMapping(value = "/{roleId}")
    @ApiOperation("后台：获取角色信息")
    public ResponseResult getInfo(@PathVariable Long roleId) {
        Role role = roleService.getById(roleId);
        return ResponseResult.okResult(role);
    }

    /**
     *请求体如下
     *{
     *     "id":"13",
     *     "remark":"我是角色备注",
     *     "roleKey":"wds",
     *     "roleName":"测试新增角色",
     *     "roleSort":0,
     *     "status":"0",
     *     "menuIds":[
     *         "1",
     *         "100",
     *         "1001"
     *     ]
     * }
     */
    @PutMapping
    @ApiOperation("后台：更新角色信息")
    public ResponseResult edit(@RequestBody Role role) {
        roleService.updateRole(role);
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("后台：删除角色")
    public ResponseResult remove(@PathVariable(name = "id") Long id) {
        roleService.removeById(id);
        return ResponseResult.okResult();
    }

    @GetMapping("/listAllRole")
    @ApiOperation("后台：获取所有角色")
    //①查询角色列表接口
    public ResponseResult listAllRole(){
        List<Role> roles = roleService.selectRoleAll();
        return ResponseResult.okResult(roles);
    }
}