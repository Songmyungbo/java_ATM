package client;

public class Client {
	private int clientNo;
	private String id;
	private String pw;
	private String name;
	int getClientNo() {
		return clientNo;
	}
	void setClientNo(int clientNo) {
		this.clientNo = clientNo;
	}
	String getId() {
		return id;
	}
	void setId(String id) {
		this.id = id;
	}
	String getPw() {
		return pw;
	}
	void setPw(String pw) {
		this.pw = pw;
	}
	String getName() {
		return name;
	}
	void setName(String name) {
		this.name = name;
	}
	public Client(int clientNo, String id, String pw, String name) {
		this.clientNo = clientNo;
		this.id = id;
		this.pw = pw;
		this.name = name;
	}
	@Override
	public String toString() {
		return clientNo + " " + id + " " + pw + " " + name;
	}
	
}
