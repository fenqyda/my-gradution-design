package com.example.demo.service;

import com.example.demo.controller.dto.UserDto;
import com.example.demo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author llm
 * @since 2022-04-14
 */
public interface IUserService extends IService<User> {

    boolean login(UserDto userDto);
}
