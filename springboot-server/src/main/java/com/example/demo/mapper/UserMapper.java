package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

//@Mapper
public interface UserMapper extends BaseMapper<User> {

      @Select("SELECT * from user where username = #{username} and pwd = #{pwd} and state = 0")
      List<User> customlogin(String username,String pwd);

      @Update("UPDATE user SET phone = #{newphone} WHERE id = #{id};")
      Boolean savenewphone(Integer id,String newphone);

      @Select("SELECT * from user where id = #{id} and pwd = #{pwd} and state = 0")
      List<User> testpwd(Integer id,String pwd);
//    @Select("SELECT * from user where state = 0")
//    List<User> findAll();
//
//    @Insert("INSERT INTO user(username, pwd, phone, sex, address, birthday, sign, nickname, avatar) " +
//            "VALUES (#{username}, #{pwd}, #{phone}, #{sex}, #{address}, #{birthday}, #{sign}, #{nickname}, #{avatar})")
//    int insert(User user);
//
//    int update(User user);
//
////    @Delete("DELETE from user where id = #{id}")
//    @Delete("UPDATE user set state = 1 where id = #{id}") //假删除
//    int deletebyID(@Param("id") Integer id);
//
//    //模糊查询
//    @Select("SELECT * from user where nickname like concat('%',#{nickname},'%') limit #{pageNum}, #{pageSize}")
//    List<User> selectPage(Integer pageNum, Integer pageSize, String nickname);
//
//    @Select("SELECT count(*) from user where state = 0 and nickname like concat('%',#{nickname},'%')")
//    Integer  selectTotal(String nickname);
//
//    //分页查询

}
