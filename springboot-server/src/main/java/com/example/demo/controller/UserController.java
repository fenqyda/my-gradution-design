package com.example.demo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.RedisUtils;
import com.example.demo.mapper.UserMapper;
import com.example.demo.utils.CodeUtil;
import com.example.demo.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.service.IUserService;
import com.example.demo.entity.User;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    RedisUtils redisUtils;

    @Resource
    private UserMapper userMapper;

    @Resource
    private IUserService userService;

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @PostMapping("/upuserthumb")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
//        long size = file.getSize();
        File uploadParentFile = new File(fileUploadPath);
        //判断目录是否存在，若不存在就创建一个新目录
        if(!uploadParentFile.exists()){
            uploadParentFile.mkdirs();
        }
        String uuid = IdUtil.fastSimpleUUID();
        File uploadFile = new File(fileUploadPath +"/avataruploads/"+ uuid + StrUtil.DOT + type);
        file.transferTo(uploadFile);
        String url = "http://localhost:3000/avataruploads/" + uuid + StrUtil.DOT + type;
        return url;
    }

    @RequestMapping("/testkaptcha")
    public boolean hello(HttpSession session, String captcha) {
        JSONObject verifyCode = (JSONObject)redisUtils.get("picCode");
        System.out.println(verifyCode);
        System.out.println(captcha);
        if(verifyCode.get("code").equals(captcha)) {
            return true;
        } else {
            return false;
        }
    }

    @PostMapping ("/login")
    public List<User> customlogin(@RequestBody User user){ //RequestBody 是把前端的json转换成java对象
        String username = user.getUsername();
        String pwd = user.getPwd();
        List<User> list = new ArrayList<>();
        if (StrUtil.isNotBlank(username) && StrUtil.isNotBlank(pwd)) {
            list = userMapper.customlogin(username,pwd);
        }
        String token = TokenUtils.genToken(user.getUsername(),user.getPwd());
//        System.out.println(token);
        for(User users:list){
            users.setToken(token);
        }
        System.out.println(list);
        return list;
    }

    @PostMapping ("/insertnewphone")
    public boolean savenewphone(@RequestBody User user){
        //更新手机号码
        Integer id = user.getId();
        String Phone = user.getPhone();
        String code = user.getCode();
        String newphone = user.getNewphone();
        System.out.println(newphone);
        if(code != null){
            JSONObject verifyCode = (JSONObject)redisUtils.get("verifyCode"+Phone);
            if(!verifyCode.get("code").equals(code)){
                System.out.println("验证码不正确");
                return false;
            }
        }
        return userMapper.savenewphone(id, newphone);
    }

    @PostMapping ("/updatepwd")
    public boolean uppwd(@RequestBody User user){
        String newpwd = user.getNewpwd();
        String pwd = user.getPwd();
        Integer id = user.getId();
        List<User> tempu = userMapper.testpwd(id,pwd);
//        System.out.println(tempu.size());
        if(newpwd != null) {
            if (tempu.size() == 1) {
                user.setPwd(newpwd);
                return userService.saveOrUpdate(user);
            }else {
                return false;
            }
        }
        return false;
    }

    @PostMapping ("/insert")
    public boolean save(@RequestBody User user){
        Integer id = user.getId();
        String Phone = user.getPhone();
        String code = user.getCode();
        //新增或者更新
        if(code != null){
            JSONObject verifyCode = (JSONObject)redisUtils.get("verifyCode"+Phone);
            if(!verifyCode.get("code").equals(code)){
                System.out.println("验证码不正确");
                return false;
            }
        }
        return userService.saveOrUpdate(user);
    }

    @DeleteMapping("/delete")
    public boolean delete(@RequestParam Integer id){ //@pathvariable 是url是数据解析注解
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id).set("state", 1);
        return userService.update(null, updateWrapper);
//        return cartService.removeById(id);
    }

    @GetMapping("/findAll")
    public List<User> index() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state",0);
        return userService.list(queryWrapper);
    }

    @GetMapping("/find")
    public User findOne(@RequestParam Integer id) {
        return userService.getById(id);
    }

    @GetMapping("/page")
    public Page<User> findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam(defaultValue = "") String nickname) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.orderByDesc("id");
        queryWrapper.eq("state",0);
        if(!"".equals(nickname)){
            queryWrapper.like("nickname",nickname);
        }
        return userService.page(new Page<>(pageNum, pageSize), queryWrapper);
        }

}
