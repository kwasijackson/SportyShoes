package com.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bean.Orders;
import com.bean.Login;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer>{
	List<Orders> findByLogin(Login login);
	List<Orders> findByOrderplacedBetween(Date start, Date end);

}
