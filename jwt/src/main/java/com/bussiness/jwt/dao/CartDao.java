package com.bussiness.jwt.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bussiness.jwt.entity.Cart;
import com.bussiness.jwt.entity.User;

@Repository
public interface CartDao extends CrudRepository<Cart,Integer> {
	
	public List<Cart>findByUser(User user);
}
