package com.cern.controller;

import com.cern.domain.ResponseResult;
import com.cern.domain.entity.Menu;
import com.cern.domain.entity.User;
import com.cern.domain.vo.AdminUserInfoVo;
import com.cern.domain.vo.RoutersVo;
import com.cern.domain.vo.UserInfoVo;
import com.cern.enums.AppHttpCodeEnum;
import com.cern.exception.SystemException;
import com.cern.service.MenuService;
import com.cern.service.RoleService;
import com.cern.service.SystemLoginService;
import com.cern.utils.BeanCopyUtils;
import com.cern.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "系统-后台：登录接口")
public class LoginController {

    @Autowired
    private SystemLoginService systemLoginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/user/login")
    @ApiOperation("用户登录")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return systemLoginService.login(user);
    }

    @PostMapping("/user/logout")
    @ApiOperation("用户登出")
    public ResponseResult logout(){
        return systemLoginService.logout();
    }

    /**
     * {
     * 	"code":200,
     * 	"data":{
     * 		"permissions":[
     * 			"system:user:list",
     *          "system:role:list",
     * 			"system:menu:list",
     * 			"system:user:query",
     * 			"system:user:add"
     *             //此次省略1000字
     * 		],
     * 		"roles":[
     * 			"admin"
     * 		],
     * 		"user":{
     * 			"avatar":"http://r7yxkqloa.bkt.clouddn.com/2022/03/05/75fd15587811443a9a9a771f24da458d.png",
     * 			"email":"23412332@qq.com",
     * 			"id":1,
     * 			"nickName":"sg3334",
     * 			"sex":"1"
     *                }* 	},
     * 	"msg":"操作成功"
     * }
     */
    @GetMapping("/getInfo")
    @ApiOperation("获取用户菜单、角色、信息")
    public ResponseResult getInfo(){
        // 获取当前登录的用户信息
        User loginUser = SecurityUtils.getLoginUser().getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser, UserInfoVo.class);
        // 获取用户角色
        List<String> hasRole = roleService.selectRoleKeyByUserId(loginUser.getId());
        // 获取用户权限perms列表
        List<String> hasMenu = menuService.selectPermsByUserId(loginUser.getId());
        // 封装传输对象
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(hasMenu, hasRole, userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @GetMapping("/getRouters")
    @ApiOperation("获取用户所有动态路由组件")
    public ResponseResult getRouters(){
        Long userId = SecurityUtils.getUserId();
        // 获取动态路由树
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        return ResponseResult.okResult(new RoutersVo(menus));
    }
}