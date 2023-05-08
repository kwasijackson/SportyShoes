package com.bean;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;
@Entity
public class Orders {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int orderid;
@DateTimeFormat(pattern = "YYYY-mm-dd") // mysql default date format. 
private LocalDate orderplaced;
//private Integer productid;		//FK 
//private String emailid;				//FK
@ManyToMany  
private List<Product> product;

@ManyToOne
private Login login;

private String fullname;
private String address;
private String contactNumber;
private String alternateContactNumber;
private int quantity;
private double orderAmount;
public int getOrderid() {
	return orderid;
}
public void setOrderid(int orderid) {
	this.orderid = orderid;
}
public LocalDate getOrderplaced() {
	return orderplaced;
}
public void setOrderplaced(LocalDate orderplaced) {
	this.orderplaced = orderplaced;
}
public List<Product> getProduct() {
	return product;
}
public void setProduct(List<Product> product) {
	this.product = product;
}
public Login getLogin() {
	return login;
}
public void setLogin(Login login) {
	this.login = login;
}
public String getFullname() {
	return fullname;
}
public void setFullname(String fullname) {
	this.fullname = fullname;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getContactNumber() {
	return contactNumber;
}
public void setContactNumber(String contactNumber) {
	this.contactNumber = contactNumber;
}
public String getAlternateContactNumber() {
	return alternateContactNumber;
}
public void setAlternateContactNumber(String alternateContactNumber) {
	this.alternateContactNumber = alternateContactNumber;
}
public int getQuantity() {
	return quantity;
}
public void setQuantity(int quantity) {
	this.quantity = quantity;
}

public double getOrderAmount() {
	return orderAmount;
}
public void setOrderAmount(double orderAmount) {
	this.orderAmount = orderAmount;
}
public Orders() {
	super();
	// TODO Auto-generated constructor stub
}
@Override
public String toString() {
	return "Orders [orderid=" + orderid + ", orderplaced=" + orderplaced + ", product=" + product + ", login=" + login
			+ ", fullname=" + fullname + ", address=" + address + ", contactNumber=" + contactNumber
			+ ", alternateContactNumber=" + alternateContactNumber + ", quantity=" + quantity + ", orderAmount="
			+ orderAmount + "]";
}



}
