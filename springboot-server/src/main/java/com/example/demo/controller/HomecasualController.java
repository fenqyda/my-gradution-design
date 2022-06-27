package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.example.demo.service.IHomecasualService;
import com.example.demo.entity.Homecasual;

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
@RequestMapping("/homecasual")
public class HomecasualController {

    @Resource
    private IHomecasualService homecasualService;

    @PostMapping ("/insert")
    public boolean save(@RequestBody Homecasual homecasual){
        //新增或者更新
        return homecasualService.saveOrUpdate(homecasual);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Integer id){ //@pathvariable 是url是数据解析注解
        return homecasualService.removeById(id);
    }

    @GetMapping("/findAll")
    public List<Homecasual> index() {
        return homecasualService.list();
    }

    @GetMapping("/find/{id}")
    public Homecasual findOne(@PathVariable Integer id) {
        return homecasualService.getById(id);
    }


}
