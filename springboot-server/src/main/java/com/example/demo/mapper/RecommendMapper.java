package com.example.demo.mapper;

import com.example.demo.entity.Recommend;
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
public interface RecommendMapper extends BaseMapper<Recommend> {

    @Select("(SELECT * FROM recommend where category = 1 LIMIT 3)\n" +
            "union \n" +
            "(SELECT * FROM recommend where category = 2 LIMIT 3)\n" +
            "union \n" +
            "(SELECT * FROM recommend where category = 3 LIMIT 3)\n" +
            "union \n" +
            "(SELECT * FROM recommend where category = 4 LIMIT 3)\n" +
            "union\n" +
            "(SELECT * FROM recommend where category = 5 LIMIT 3)")
    List<Recommend> homeshoplist();



}
