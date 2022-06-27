package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Comments;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Recommend;
import org.apache.ibatis.annotations.Param;
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
public interface CommentsMapper extends BaseMapper<Comments> {

    @Select("SELECT comments.userid, user.username, user.nickname, comments.commentdetail, comments.id, comments.rating, comments.goodsid FROM user INNER JOIN comments ON user.id = comments.userid WHERE goodsid = #{id} and comments.state = 0")
    List<Comments> GoodsComment(Integer id);

    @Select("SELECT comments.id, comments.commentdetail, comments.rating, comments.userid, comments.goodsid, user.nickname, user.username, recommend.shortname FROM comments,user,recommend where comments.userid = user.id and comments.goodsid = recommend.goodsid and comments.state = 0")
    List<Comments> Allgoodscomment();
}
