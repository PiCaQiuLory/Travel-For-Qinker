package com.ss.service.impl;

import com.ss.mapper.IUserDao;
import com.ss.pojo.User;
import com.ss.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements IUserService {
	@Resource
	private IUserDao userDao;

	public User getUserById(int userId) {
		return userDao.selectByPrimaryKey(userId);
	}
	public User login(String loginName, String password){
		return userDao.findLoginUser(loginName, password);
	}

	@Override
	public User findByUserName(String userName) {
		return userDao.findByUserName(userName);
	}

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	@Override
	public void save(User user) {
		userDao.save(user);
	}

	@Override
	public void update(User user){
		userDao.update(user);
	}

	@Override
	public void delete(long userId) {
		userDao.deleteByPrimaryKey(userId);
	}

}
