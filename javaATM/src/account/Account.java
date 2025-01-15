package account;

public class Account {
	private int clientNo;
	private String clientId;
	private String accNumber;
	private int money;
	int getClientNo() {
		return clientNo;
	}
	void setClientNo(int clientNo) {
		this.clientNo = clientNo;
	}
	String getClientId() {
		return clientId;
	}
	void setClientId(String clientId) {
		this.clientId = clientId;
	}
	String getAccNumber() {
		return accNumber;
	}
	void setAccNumber(String accNumber) {
		this.accNumber = accNumber;
	}
	int getMoney() {
		return money;
	}
	void setMoney(int money) {
		this.money = money;
	}
	public Account(int clientNo, String clientId, String accNumber, int money) {
		this.clientNo = clientNo;
		this.clientId = clientId;
		this.accNumber = accNumber;
		this.money = money;
	}
	@Override
	public String toString() {
		return clientNo + " " + clientId + " " + accNumber + " "+ money;
	}
	
}
