package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bean.Cart;
import com.bean.Login;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>{
	List<Cart> findByLogin(Login login);

}
