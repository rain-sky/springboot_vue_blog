package com.cern.controller;

import com.cern.domain.ResponseResult;
import com.cern.domain.entity.Menu;
import com.cern.domain.vo.MenuTreeVo;
import com.cern.domain.vo.MenuVo;
import com.cern.domain.vo.RoleMenuTreeSelectVo;
import com.cern.service.MenuService;
import com.cern.utils.BeanCopyUtils;
import com.cern.utils.MenuConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/menu")
@Api(tags = "系统-后台：菜单接口")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    @ApiOperation("后台：菜单列表")
    public ResponseResult list(Menu menu) {
        List<Menu> menus = menuService.selectMenuList(menu);
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menus, MenuVo.class);
        return ResponseResult.okResult(menuVos);
    }

    @PostMapping
    @ApiOperation("后台：新增菜单")
    public ResponseResult add(@RequestBody Menu menu) {
        menuService.save(menu);
        return ResponseResult.okResult();
    }

    @GetMapping(value = "/{menuId}")
    @ApiOperation("后台：获取菜单")
    //①先查询根据菜单id查询对应的权限菜单
    public ResponseResult getInfo(@PathVariable Long menuId) {
        return ResponseResult.okResult(menuService.getById(menuId));
    }

    @PutMapping
    @ApiOperation("后台：更新菜单")
    //②然后才是更新菜单
    public ResponseResult edit(@RequestBody Menu menu) {
        if (menu.getId().equals(menu.getParentId())) {
            return ResponseResult.errorResult(500,"修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        menuService.updateById(menu);
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{menuId}")
    @ApiOperation("后台：删除菜单")
    public ResponseResult remove(@PathVariable("menuId") Long menuId) {
        if (menuService.hasChild(menuId)) {
            return ResponseResult.errorResult(500,"存在子菜单不允许删除");
        }
        menuService.removeById(menuId);
        return ResponseResult.okResult();
    }

    // 新增角色时使用
    @GetMapping("/treeselect")
    @ApiOperation("后台：获取全部菜单权限树")
    public ResponseResult treeSelect() {
        // 复用之前的selectMenuList方法。方法需要参数，参数可以用来进行条件查询，而这个方法不需要条件，所以直接new Menu()传入
        List<Menu> menus = menuService.selectMenuList(new Menu());
        // 将菜单列表封装成树的形式
        List<MenuTreeVo> options =  MenuConverter.buildMenuSelectTree(menus);
        return ResponseResult.okResult(options);
    }

    // 在修改角色信息时用于展示已有的角色信息时需要使用到
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    @ApiOperation("后台：获取角色选中的菜单权限树")
    public ResponseResult roleMenuTreeSelect(@PathVariable("roleId") Long roleId) {
        List<Menu> menus = menuService.selectMenuList(new Menu());
        List<Long> checkedKeys = menuService.selectMenuListByRoleId(roleId);
        List<MenuTreeVo> menuTreeVos = MenuConverter.buildMenuSelectTree(menus);
        RoleMenuTreeSelectVo vo = new RoleMenuTreeSelectVo(checkedKeys,menuTreeVos);
        return ResponseResult.okResult(vo);
    }
}