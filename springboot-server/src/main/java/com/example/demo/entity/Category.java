package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Category {
    @TableId(value = "cate_id")
    private Integer cateId;
    private String cateName;
    private String cateIcon;
    private Integer cateCounts;
}
