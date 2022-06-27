package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.controller.dto.AdminDto;
import com.example.demo.controller.dto.UserDto;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.example.demo.service.IAdministratorsService;
import com.example.demo.entity.Administrators;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author llm
 * @since 2022-04-15
 */
@RestController
@RequestMapping("/administrators")
public class AdministratorsController {

    @Resource
    private IAdministratorsService administratorsService;

    @PostMapping ("/insert")
    public boolean save(@RequestBody Administrators administrators){
        //新增或者更新
        return administratorsService.saveOrUpdate(administrators);
    }

    @PostMapping ("/login")
    public boolean login(@RequestBody AdminDto adminDto){ //RequestBody 是把前端的json转换成java对象
        String account = adminDto.getAccount();
        String pwd = adminDto.getPwd();
        if (StrUtil.isBlank(account) || StrUtil.isBlank(pwd)) {
            return false;
        }
        return administratorsService.login(adminDto);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Integer id){ //@pathvariable 是url是数据解析注解
        return administratorsService.removeById(id);
    }

    @GetMapping("/findAll")
    public List<Administrators> index() {
        return administratorsService.list();
    }

    @GetMapping("/find/{id}")
    public Administrators findOne(@PathVariable Integer id) {
        return administratorsService.getById(id);
    }

    @GetMapping("/page")
    public Page<Administrators> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        QueryWrapper<Administrators> queryWrapper = new QueryWrapper<>();
        return administratorsService.page(new Page<>(pageNum, pageSize), queryWrapper);
        }

}
