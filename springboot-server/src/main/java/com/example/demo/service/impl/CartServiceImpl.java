package com.example.demo.service.impl;

import com.example.demo.entity.Cart;
import com.example.demo.mapper.CartMapper;
import com.example.demo.service.ICartService;
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
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements ICartService {

}
