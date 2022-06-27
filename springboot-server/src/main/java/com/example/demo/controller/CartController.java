package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Comments;
import com.example.demo.mapper.CartMapper;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.example.demo.service.ICartService;
import com.example.demo.entity.Cart;

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
@RequestMapping("/cart")
public class CartController {

    @Resource
    private CartMapper cartMapper;
    @Resource
    private ICartService cartService;

    @PostMapping ("/insert")
    public boolean save(@RequestBody Cart cart){
        //新增或者更新
        return cartService.saveOrUpdate(cart);
    }

    @PostMapping ("/paygoods")
    public boolean paygoods(@RequestBody Cart cart){
        cart.setIspay("是");
        System.out.println(cart);
        return cartService.saveOrUpdate(cart);
//        return false;
    }

    @DeleteMapping("/deletebyuid")
    public boolean deletebyuid(@RequestParam("userid") Integer userid){ //@pathvariable 是url是数据解析注解
        UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("userid",userid).set("state", 1);
        return cartService.update(null, updateWrapper);
//        return cartService.removeById(id);
    }

    @DeleteMapping("/delete")
    public boolean delete(@RequestParam("id") Integer id){ //@pathvariable 是url是数据解析注解
        UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id).set("state", 1);
        return cartService.update(null, updateWrapper);
//        return cartService.removeById(id);
    }

    @GetMapping("/findAll")
    public List<Cart> findall() {
        return cartMapper.findall();
    }

    @GetMapping("/find/{id}")
    public Cart findOne(@PathVariable Integer id) {
        return cartService.getById(id);
    }

    @GetMapping("/findbuid")
    public List<Cart> findByuser(@RequestParam Integer userid) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", userid);
        queryWrapper.eq("state",0);
        return cartService.list(queryWrapper);
    }

    @GetMapping("/findbuidnp")
    public List<Cart> findByusernp(@RequestParam Integer userid) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", userid);
        queryWrapper.eq("ispay", "否");
        queryWrapper.eq("state",0);
        return cartService.list(queryWrapper);
    }

    @GetMapping("/findbuidpd")
    public List<Cart> findByuserpd(@RequestParam Integer userid) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", userid);
        queryWrapper.eq("ispay", "是");
        queryWrapper.ne("transit", 2);
        queryWrapper.eq("state",0);
        return cartService.list(queryWrapper);
    }

    @GetMapping("/findbuidts")
    public List<Cart> findByuserts(@RequestParam Integer userid) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", userid);
        queryWrapper.eq("transit", "1");
        queryWrapper.eq("state",0);
        return cartService.list(queryWrapper);
    }

    @GetMapping("/findbuidsended")
    public List<Cart> findByuidsended(@RequestParam Integer userid) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", userid);
        queryWrapper.eq("transit", "2");
        queryWrapper.eq("state",0);
        return cartService.list(queryWrapper);
    }

    @GetMapping("/pagepay")
    public Page<Cart> findPagepayed(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ispay",1);
        return cartService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }
    @GetMapping("/pagenpay")
    public Page<Cart> findPagenpay(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ispay",0);
        return cartService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }
}
