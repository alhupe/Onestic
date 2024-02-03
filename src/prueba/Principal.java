package prueba;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

public class Principal {

	private List<Order> orderList = new ArrayList<>();
	private List<Customer> customerList = new ArrayList<>();
	private List<Product> productList = new ArrayList<>();

	public static void main(String[] args) throws IOException, ParserConfigurationException {
		Principal principal = new Principal();
		principal.readFiles("orders.csv");
		principal.readFiles("customers.csv");
		principal.readFiles("products.csv");

		principal.createOrderTotalCost(principal.productList, principal.orderList);
		principal.createCustomersInterested(principal.productList, principal.customerList, principal.orderList);
		principal.createCustomerRanking(principal.customerList, principal.orderList, principal.productList);
		
		System.out.println("Archivos creados");
	}

	public void readFiles(String file) throws NumberFormatException, IOException {
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		String line;

		br.readLine();
		while ((line = br.readLine()) != null) {
			if (file.equals("orders.csv")) {
				createOrderList(line);
			} else if (file.equals("customers.csv")) {
				createCustomerList(line);
			} else if (file.equals("products.csv")) {
				createProductList(line);
			}
		}

		br.close();
	}

	public void createOrderList(String orderLine) {
		Order order = new Order();
		String[] parts = orderLine.split(",");
		order.setId(Integer.parseInt(parts[0]));
		order.setCustomerId(Integer.parseInt(parts[1]));
		String[] productsIds = parts[2].split(" ");
		List<Integer> productIdList = new ArrayList<>();
		for (String productId : productsIds) {
			productIdList.add(Integer.parseInt(productId));
		}
		order.setProductIds(productIdList);

		orderList.add(order);
	}

	public void createCustomerList(String customerLine) {
		Customer customer = new Customer();
		String[] parts = customerLine.split(",");
		customer.setId(Integer.parseInt(parts[0]));
		customer.setFirstName(parts[1]);
		customer.setLastName(parts[2]);

		customerList.add(customer);
	}

	public void createProductList(String productLine) {
		Product product = new Product();
		String[] parts = productLine.split(",");
		product.setId(Integer.parseInt(parts[0]));
		product.setName(parts[1]);
		product.setCost(Double.parseDouble(parts[2]));

		productList.add(product);
	}

	public void createOrderTotalCost(List<Product> productsList, List<Order> ordersList)
			throws IOException, ParserConfigurationException {

		for (Order order : ordersList) {
			double totalCost = calculateTotalCost(order, productsList);
			order.setTotalCost(totalCost);
		}

		FileWriter fw = new FileWriter("order_prices.csv");
		BufferedWriter writer = new BufferedWriter(fw);
		writer.write("id,euros");
		writer.newLine();
		for (Order singleOrder : orderList) {
			String idAndTotal = singleOrder.getId() + "," + singleOrder.getTotalCost();
			writer.write(idAndTotal);
			writer.newLine();
		}
		System.out.println("Creado order_prices.csv");

		writer.close();
		fw.close();
	}

	private void createCustomersInterested(List<Product> productsList, List<Customer> customersList, List<Order> ordersList) throws IOException {

		for (Product prod : productsList) {
			List<Integer> customersIdList = new ArrayList<>();
			int productId = prod.getId();
			for (Order ord : ordersList) {
				List<Integer> idProductsList = ord.getProductIds();
				if(idProductsList.contains(productId)) {
					int clientId = ord.getCustomerId();
					if(!customersIdList.contains(clientId)) {
						customersIdList.add(clientId);
					}
				}
			}
			prod.setClientId(customersIdList);
		}
		
		FileWriter fw = new FileWriter("product_customers.csv");
		BufferedWriter writer = new BufferedWriter(fw);
		writer.write("id,customer_ids");
		writer.newLine();
		for (Product singleProduct : productList) {
			String idAndCustomers = singleProduct.getId() + ",";
			for (int customer : singleProduct.getClientId()) {
				idAndCustomers += customer + " ";
			}
			writer.write(idAndCustomers);
			writer.newLine();
		}
		System.out.println("Creado product_customers.csv");

		writer.close();
		fw.close();
	}
	
	private double calculateTotalCost(Order order, List<Product> prodList) {
		Double totalCost = 0.0;
		List<Integer> prodsIds = order.getProductIds();
		for (int prod : prodsIds) {
			for (Product product : prodList) {
				if (prod == product.getId()) {
					totalCost += product.getCost();
				}
			}
		}
		return totalCost;
	}
	
	private void createCustomerRanking(List<Customer> customersList, List<Order> ordersList, List<Product> productsList) throws IOException {
		
		for(Customer customer : customersList) {
			double totalSpent = 0.0;
			int customerId = customer.getId();
			for (Order ord : ordersList) {
				if(ord.getCustomerId() == (customerId)) {
					totalSpent += calculateTotalCost(ord, productList);
				}
			}
			customer.setTotalEuros(totalSpent);
		}
		
		List<Customer> customerSortByEuros = new ArrayList<>(customerList);
		customerSortByEuros.sort((customer1, customer2)->Double.compare(customer2.getTotalEuros(), customer1.getTotalEuros()));
		
		FileWriter fw = new FileWriter("customer_ranking.csv");
		BufferedWriter writer = new BufferedWriter(fw);
		writer.write("id,fistname,lastname,total_euros");
		writer.newLine();
		for(Customer customer : customerSortByEuros) {
			String customerInfo = customer.getId() + "," + customer.getFirstName() + "," + customer.getLastName() + "," + customer.getTotalEuros();
			writer.write(customerInfo);
			writer.newLine();
		}
		System.out.println("Creado customer_ranking.csv");
		writer.close();
		fw.close();
	}

}
