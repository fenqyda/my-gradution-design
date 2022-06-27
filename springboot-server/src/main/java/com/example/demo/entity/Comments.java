package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "comments")
public class Comments {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Integer goodsid;
    private String commentdetail;
    private Integer rating;
    private Integer userid;
    @TableField(exist = false)
    private String username;
    @TableField(exist = false)
    private String nickname;
    @TableField(exist = false)
    private String shortname;
}
