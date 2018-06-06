package com.ss.service;

import com.ss.pojo.User;

import java.util.List;

public interface IUserService {
	public User getUserById(int userId);

	public User login(String loginName, String password);

	public User findByUserName(String userName);

	public List<User> findAll();

	public void save(User user);

	public void update(User user);

	public void delete(long userId);
}
