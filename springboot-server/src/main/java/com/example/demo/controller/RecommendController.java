package com.example.demo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.ResultData;
import com.example.demo.mapper.RecommendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.service.IRecommendService;
import com.example.demo.entity.Recommend;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author llm
 * @since 2022-04-15
 */
@RestController
@RequestMapping("/recommend")
public class RecommendController {

    @Autowired(required=false)
    private RecommendMapper recommendMapper;

    @Resource
    private IRecommendService recommendService;

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @PostMapping("/uploaddata")
    public ResultData<String> upload(@RequestParam MultipartFile file, Recommend recommend) throws IOException {
        QueryWrapper<Recommend> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goodsid",recommend.getGoodsid());
        Integer count = Math.toIntExact(recommendMapper.selectCount(queryWrapper));
        if(count > 0) {
            return ResultData.fail(999,"数据已存在");
        }else {
            String originalFilename = file.getOriginalFilename();
            String type = FileUtil.extName(originalFilename);
            long size = file.getSize();
            File uploadParentFile = new File(fileUploadPath);
            //判断目录是否存在，若不存在就创建一个新目录
            if(!uploadParentFile.exists()){
                uploadParentFile.mkdirs();
            }
            String uuid = IdUtil.fastSimpleUUID();
            File uploadFile = new File(fileUploadPath +"/uploads/"+ uuid + StrUtil.DOT + type);
            file.transferTo(uploadFile);
            String url = "http://localhost:3000/uploads/" + uuid + StrUtil.DOT + type;
            recommend.setThumburl(url);
            recommend.setImageurl(url);
            recommend.setHdthumburl(url);
            if (recommendService.save(recommend)) {
                return ResultData.success("true");
            }
        }
        return ResultData.fail(500,"上传失败");
    }

    @PostMapping ("/insert")
    public boolean save(@RequestBody Recommend recommend){
        //新增或者更新
        System.out.println(recommend);
        return recommendService.saveOrUpdate(recommend);
    }

    @DeleteMapping("/delete")
    public boolean delete(@RequestParam("id") Integer id){ //@pathvariable 是url是数据解析注解
        UpdateWrapper<Recommend> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("goodsid",id).set("state", 1);
        return recommendService.update(null, updateWrapper);
//        return cartService.removeById(id);
    }

    @GetMapping("/findAll")
    public List<Recommend> index() {
        QueryWrapper<Recommend> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state",0);
        return recommendService.list(queryWrapper);
    }

    @PostMapping("/search")
    public List<Recommend> search(@RequestBody Recommend recommend){
        String keywords = recommend.getKeywords();
        System.out.println(recommend);
        System.out.println(keywords);
        QueryWrapper<Recommend> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state",0);
        queryWrapper.like("goodsname",keywords);
        return recommendService.list(queryWrapper);
    }

    @GetMapping("/findbyid")
    public Recommend findOne(@RequestParam("goodsNo") Integer id) {
        return recommendService.getById(id);
    }

    @GetMapping("/homeshoplist")
    public List<Recommend> homeshoplist() {
        return recommendMapper.homeshoplist();
    }

    @GetMapping("/page")
    public Page<Recommend> findPage(@RequestParam("pageNum") Integer pageNum,
                                   @RequestParam("pageSize") Integer pageSize,
                                   @RequestParam("category") Integer category) {
        QueryWrapper<Recommend> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category",category);
        return recommendService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

}
