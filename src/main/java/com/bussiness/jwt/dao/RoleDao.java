package com.bussiness.jwt.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bussiness.jwt.entity.Role;

@Repository
public interface RoleDao extends CrudRepository<Role,String> {

	
}
