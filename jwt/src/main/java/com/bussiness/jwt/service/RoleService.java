package com.bussiness.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bussiness.jwt.dao.RoleDao;
import com.bussiness.jwt.entity.Role;

@Service
public class RoleService {

	@Autowired
	private RoleDao roleDao;
	
	public Role createNewRole(Role role) {
		return roleDao.save(role);
	}
}
