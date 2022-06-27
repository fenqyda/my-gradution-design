package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Recommend;
import com.example.demo.mapper.CommentsMapper;
import com.example.demo.mapper.RecommendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.example.demo.service.ICommentsService;
import com.example.demo.entity.Comments;

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
@RequestMapping("/comments")
public class CommentsController {

    @Resource
    private CommentsMapper commentsMapper;

    @Resource
    private ICommentsService commentsService;

    @PostMapping ("/insert")
    public boolean save(@RequestBody Comments comments){
        //新增或者更新
        return commentsService.saveOrUpdate(comments);
    }


    @DeleteMapping("/delete")
    public boolean delete(@RequestParam("commentid") Integer commentid){ //@pathvariable 是url是数据解析注解
        UpdateWrapper<Comments> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",commentid).set("state", 1);
        return commentsService.update(null, updateWrapper);
//        return cartService.removeById(id);
    }

    @GetMapping("/findAll")
    public List<Comments> Allgoodscomment() {
        return commentsMapper.Allgoodscomment();
    }

    @GetMapping("/findbyid")
    public List<Comments> findComment(@RequestParam("goodsId") Integer id) {
        return commentsMapper.GoodsComment(id);
    }

    @GetMapping("/findbuid/{userid}")
    public List<Comments> findByuser(@PathVariable Integer userid) {
        QueryWrapper<Comments> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", userid);
        return commentsService.list(queryWrapper);
    }

    @GetMapping("/page")
    public Page<Comments> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        QueryWrapper<Comments> queryWrapper = new QueryWrapper<>();
        return commentsService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

}
