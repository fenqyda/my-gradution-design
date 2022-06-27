package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Recommend;
import com.example.demo.entity.User;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.example.demo.service.ICategoryService;
import com.example.demo.entity.Category;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author llm
 * @since 2022-04-16
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private ICategoryService categoryService;

    @PostMapping ("/insert")
    public boolean save(@RequestBody Category category){
        //新增或者更新
        return categoryService.saveOrUpdate(category);
    }


    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Integer id){ //@pathvariable 是url是数据解析注解
        return categoryService.removeById(id);
    }

    @GetMapping("/findAll")
    public List<Category> index() {
        return categoryService.list();
    }

    @GetMapping("/find/{id}")
    public Category findOne(@PathVariable Integer id) {
        return categoryService.getById(id);
    }

}
