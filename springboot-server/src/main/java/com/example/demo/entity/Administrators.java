package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName(value = "administrators")
public class Administrators {
    private Integer id;
    private String account;
    private String pwd;
}
