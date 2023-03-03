package com.driver.io.entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity(name = "foods")
public class FoodEntity{
	@Id
	@GeneratedValue
	private long id;

	@Column(nullable = false)
	private String foodId;

	@Column(nullable = false)
	private String foodName;

	@Column(nullable = false)
	private float foodPrice;
	
	@Column(nullable = false)
	private String foodCategory;

	@ManyToOne
	@JoinColumn
	OrderEntity orderEntity;

	public FoodEntity() {
	}

	public OrderEntity getOrderEntity() {
		return orderEntity;
	}

	public void setOrderEntity(OrderEntity orderEntity) {
		this.orderEntity = orderEntity;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFoodId() {
		return foodId;
	}

	public void setFoodId(String foodId) {
		this.foodId = foodId;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public float getFoodPrice() {
		return foodPrice;
	}

	public void setFoodPrice(float foodPrice) {
		this.foodPrice = foodPrice;
	}

	public String getFoodCategory() {
		return foodCategory;
	}

	public void setFoodCategory(String foodCategory) {
		this.foodCategory = foodCategory;
	}
}
