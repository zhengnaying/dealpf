package com.Dealpf.demo.Mapper;

import com.Dealpf.demo.Bean.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AdminMapper {
    //查询所有用户
    @Select("SELECT * FROM User where User_role = '普通用户' or User_role = '商家';")
    List<User> getUserList();
    //查询指定用户
    @Select("SELECT * FROM User WHERE User_name = #{userName} AND User_password= #{userPassword}")
    User getUser(String userName, String userPassword);
    //id查询user
    @Select("SELECT * FROM User WHERE User_id = #{userId}")
    User getUserByID(String userId);
    //删除指定用户
    @Delete("DELETE FROM User WHERE User_name = #{userName} AND User_password = #{userPassword};")
    void deleteUser(String userName, String userPassword);
    //获取所有申请用户
    @Select("SELECT * FROM User_request")
    List<User> getRegistList();
    //修改用户数据
     @Update("UPDATE User SET User_role = #{userRole} WHERE (User_id = #{userId});")
    void update(String userId,String userRole);

}
