package com.cern.controller;

import com.cern.domain.ResponseResult;
import com.cern.domain.dto.ChangeRoleStatusDto;
import com.cern.domain.entity.Role;
import com.cern.service.RoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/role")
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
}