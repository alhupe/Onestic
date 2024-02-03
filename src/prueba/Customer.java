package prueba;

public class Customer {
	
	private int id;
	private String firstName;
	private String lastName;
	private double totalEuros;

	
	public Customer() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public double getTotalEuros() {
		return totalEuros;
	}
	public void setTotalEuros(double totalEuros) {
		this.totalEuros = totalEuros;
	}
}