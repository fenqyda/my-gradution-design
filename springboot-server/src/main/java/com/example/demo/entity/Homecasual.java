package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Homecasual {
    @TableId(value = "id")
    private Integer id;
    private String imgurl;
    private String detail;
}
