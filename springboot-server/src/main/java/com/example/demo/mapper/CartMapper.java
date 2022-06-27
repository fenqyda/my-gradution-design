package com.example.demo.mapper;

import com.example.demo.entity.Cart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author llm
 * @since 2022-04-15
 */
public interface CartMapper extends BaseMapper<Cart> {

    @Select("SELECT cart.goodsid, cart.goodsname, cart.price, cart.buycount, cart.ispay, cart.id, cart.userid, cart.transittime, cart.transit, " +
            "user.username, user.phone, user.address" +
            " FROM cart,user where cart.userid = user.id and cart.state = 0")
    List<Cart> findall();
}
