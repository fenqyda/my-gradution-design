package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "cart")
public class Cart {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Integer goodsid;
    private String goodsname;
    private String thumburl;
    private String price;
    private Integer buycount;
    private String ispay;
    private String userid;
    private String createtime;
    private Integer transit;
    private String transittime;
    private Integer flag;
    private String transiedtime;
    @TableField(exist = false)
    private String username;
    @TableField(exist = false)
    private String phone;
    @TableField(exist = false)
    private String address;
}
