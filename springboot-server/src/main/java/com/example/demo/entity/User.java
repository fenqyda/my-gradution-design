package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "user")
public class User {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String username;
    private String pwd;
    private String phone;
    private String sex;
    private String address;
    private String birthday;
    private String sign;
    private String nickname;
    private String avatar;
    private Integer state;
    @TableField(exist = false)
    private String token;
    @TableField(exist = false)
    private String code;
    @TableField(exist = false)
    private String newphone;
    @TableField(exist = false)
    private String newpwd;
}
