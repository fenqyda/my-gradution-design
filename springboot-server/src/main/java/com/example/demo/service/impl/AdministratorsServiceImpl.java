package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.controller.dto.AdminDto;
import com.example.demo.controller.dto.UserDto;
import com.example.demo.entity.Administrators;
import com.example.demo.entity.User;
import com.example.demo.mapper.AdministratorsMapper;
import com.example.demo.service.IAdministratorsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author llm
 * @since 2022-04-15
 */
@Service
public class AdministratorsServiceImpl extends ServiceImpl<AdministratorsMapper, Administrators> implements IAdministratorsService {

    @Override
    public boolean login(AdminDto adminDto) {
        QueryWrapper<Administrators> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account",adminDto.getAccount());
        queryWrapper.eq("pwd",adminDto.getPwd());
        Administrators one = getOne(queryWrapper);
        return one != null;
    }
}
