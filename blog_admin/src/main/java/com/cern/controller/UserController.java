package com.cern.controller;

import com.cern.domain.ResponseResult;
import com.cern.domain.entity.Role;
import com.cern.domain.entity.User;
import com.cern.domain.vo.UserInfoAndRoleIdsVo;
import com.cern.enums.AppHttpCodeEnum;
import com.cern.exception.SystemException;
import com.cern.service.RoleService;
import com.cern.service.UserService;
import com.cern.utils.SecurityUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/user")
@ApiOperation("系统-后台：用户接口")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    @ApiOperation("后台：获取用户列表")
    public ResponseResult list(User user, Integer pageNum, Integer pageSize) {
        return userService.selectUserPage(user,pageNum,pageSize);
    }

    @PostMapping
    @ApiOperation("后台：新增用户")
    public ResponseResult add(@RequestBody User user) {
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if (!userService.checkUserNameUnique(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (!userService.checkPhoneUnique(user)){
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        if (!userService.checkEmailUnique(user)){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        return userService.addUser(user);
    }

    @DeleteMapping("/{userIds}")
    @ApiOperation("后台：删除用户")
    public ResponseResult remove(@PathVariable List<Long> userIds) {
        if(userIds.contains(SecurityUtils.getUserId())){
            return ResponseResult.errorResult(500,"不能删除当前你正在使用的用户");
        }
        userService.removeByIds(userIds);
        return ResponseResult.okResult();
    }

    //-----------------------修改用户-①根据id查询用户信息-----------------------------
    @GetMapping(value = { "/{userId}" })
    @ApiOperation("后台：修改用户时查询用户信息")
    public ResponseResult getUserInfoAndRoleIds(@PathVariable(value = "userId") Long userId) {
        // 查询出所有角色列表
        List<Role> roles = roleService.selectRoleAll();
        User user = userService.getById(userId);
        // 当前用户所具有的角色id列表
        List<Long> roleIds = roleService.selectRoleIdByUserId(userId);
        UserInfoAndRoleIdsVo vo = new UserInfoAndRoleIdsVo(user,roles,roleIds);
        return ResponseResult.okResult(vo);
    }

    //-------------------------修改用户-②更新用户信息--------------------------------
    @PutMapping
    @ApiOperation("后台：修改用户信息")
    public ResponseResult edit(@RequestBody User user) {
        userService.updateUser(user);
        return ResponseResult.okResult();
    }
}