package com.driver.model.response;


public class OrderDetailsResponse {

	private String orderId;
	private float cost;
	private String items[];
	private String userId;
	private boolean status;

	public OrderDetailsResponse(String orderId, float cost, String[] items, String userId, boolean status) {
		this.orderId = orderId;
		this.cost = cost;
		this.items = items;
		this.userId = userId;
		this.status = status;
	}

	public OrderDetailsResponse() {
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public String[] getItems() {
		return items;
	}

	public void setItems(String[] items) {
		this.items = items;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
