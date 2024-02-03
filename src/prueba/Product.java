package prueba;

import java.util.List;

public class Product {
	
	private int id;
	private String name;
	private double cost;
	private List<Integer> clientId;
	
	public Product() {
	}
	
	public List<Integer> getClientId() {
		return clientId;
	}
	public void setClientId(List<Integer> clientId) {
		this.clientId = clientId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}

}