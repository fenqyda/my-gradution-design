package com.example.demo.controller.dto;

import lombok.Data;

/**
 * <p>
 *  接收前端的登录请求
 * </p>
 *
 * @author llm
 * @since 2022-04-15
 */
@Data
public class UserDto {
    private String username;
    private String pwd;
    private String captcha;
}
