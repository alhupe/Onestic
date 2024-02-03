package prueba;

import java.util.List;

public class Order {

	private int id;
	private int customerId;
	private List<Integer> productIds;
	private double totalCost;

	public Order() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public List<Integer> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<Integer> productIdList) {
		this.productIds = productIdList;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

}