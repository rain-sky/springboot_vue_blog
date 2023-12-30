package com.cern.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.cern.domain.ResponseResult;
import com.cern.domain.dto.CategoryDto;
import com.cern.domain.entity.Category;
import com.cern.domain.vo.CategoryVo;
import com.cern.domain.vo.ExcelCategoryVo;
import com.cern.domain.vo.PageVo;
import com.cern.enums.AppHttpCodeEnum;
import com.cern.service.CategoryService;
import com.cern.utils.BeanCopyUtils;
import com.cern.utils.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/content/category")
@Api(tags = "系统-后台：分类接口")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    @ApiOperation("获取全部分类列表")
    public ResponseResult listAllCategory(){
        List<CategoryVo> list = categoryService.listAllCategory();
        return ResponseResult.okResult(list);
    }

    @GetMapping("/list")
    @ApiOperation("条件查询分类列表")
    public ResponseResult list(Category category, Integer pageNum, Integer pageSize) {
        PageVo pageVo = categoryService.selectCategoryPage(category,pageNum,pageSize);
        return ResponseResult.okResult(pageVo);
    }


    @PostMapping
    @ApiOperation("新增分类")
    public ResponseResult add(@RequestBody CategoryDto categoryDto){
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        categoryService.save(category);
        return ResponseResult.okResult();
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation("后台：删除分类")
    public ResponseResult remove(@PathVariable(value = "id")Long id){
        categoryService.removeById(id);
        return ResponseResult.okResult();
    }


    @GetMapping(value = "/{id}")
    @ApiOperation("后台：查询分类")
    //①根据分类的id来查询分类
    public ResponseResult getInfo(@PathVariable(value = "id")Long id){
        Category category = categoryService.getById(id);
        return ResponseResult.okResult(category);
    }

    @PutMapping
    @ApiOperation("后台：修改分类")
    //②根据分类的id来修改分类
    public ResponseResult edit(@RequestBody Category category){
        categoryService.updateById(category);
        return ResponseResult.okResult();
    }

    @GetMapping("/export")
    @ApiOperation("后台：分类导出")
    @PreAuthorize("@permissionServiceImpl.hasPermission('content:category:export')") // 使用自己鉴权方法
    //注意返回值类型是void
    public void export(HttpServletResponse response){
        try {
            // 使用工具类设置下载文件的响应头，下载的文件名为category.xlsx
            WebUtils.setDownLoadHeader("category.xlsx",response);
            // 获取需要导出到excel的数据
            List<Category> categories = categoryService.list();
            // 将数据库源数据格式转换
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categories, ExcelCategoryVo.class);
            // 把数据写入到Excel中，也就是把ExcelCategoryVo实体类的字段作为Excel表格的列头
            // sheet方法里面的字符串是Excel表格左下角工作簿的名字
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("文章分类")
                    .doWrite(excelCategoryVos);
        } catch (Exception e) {
            //如果出现异常,就返回失败的json数据给前端。
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            //WebUtils中的renderString方法是将json字符串写入到请求体，然后返回给前端
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }
}