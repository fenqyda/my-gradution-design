package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Recommend {
    @TableId(value = "goodsid",type = IdType.AUTO)
    private Integer goodsid;
    private String goodsname;
    private String shortname;
    private String thumburl;
    private String hdthumburl;
    private String imageurl;
    private Integer price;
    private Integer normalprice;
    private Integer marketprice;
    private String salestip;
    private Integer category;
    private Integer commentscount;
    @TableField(exist = false)
    private String keywords;
}
