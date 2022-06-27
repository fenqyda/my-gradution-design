package com.example.demo.service;

import com.example.demo.controller.dto.AdminDto;
import com.example.demo.controller.dto.UserDto;
import com.example.demo.entity.Administrators;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author llm
 * @since 2022-04-15
 */
public interface IAdministratorsService extends IService<Administrators> {

    boolean login(AdminDto adminDto);

}
