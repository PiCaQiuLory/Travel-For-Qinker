package com.ss.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ss.pojo.TargetProduct;
import com.ss.pojo.User;

public interface IUserDao {
    int deleteByPrimaryKey(long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    List<TargetProduct> getTargetProductList(Date dt);

    User findLoginUser(@Param("loginName") String loginName, @Param("password") String password);
    
    User findByUserName(String userName) ;
    
    List<User> findAll();
    
    void save(User user);

    void update(User user);
}