package client;

import java.util.ArrayList;

import account.AccountDAO;
import utils.Utils;

public class ClientDAO {
	ArrayList<Client> clientList;

	public void init() {
		clientList = new ArrayList<Client>();
	}

	public void join() {
		String id = Utils.getInstence().getInputValue("아이디 입력 : ");
		boolean check = idCheck(id);
		if(check) {
			String pw = Utils.getInstence().getInputValue("비밀번호 입력 : ");
			String name = Utils.getInstence().getInputValue("이름 입력 : ");
			clientList.add(new Client(clientList.size()+1001,id, pw, name));
			System.out.println(clientList.get(clientList.size()-1));
			System.out.println("회원가입이 완료되었습니다.");
		}else {
			System.out.println("중복된 아이디입니다.");
			return;
		}
	}
	/** 회원목록출력*/
	public void printClient() {
		if(clientList.size() == 0) {
			System.out.println("회원 목록이 없습니다");
			return;
		}
		for(Client c : clientList) {
			System.out.println(c);
		}
	}
	private boolean idCheck(String id) {
		if(clientList.size() == 0) {
			return true;
		}else {
			int idx = -1;
			for(int i = 0; i < clientList.size(); i++) {
				if(id.equals(clientList.get(i).getId())) {
					idx = i;
					break;
				}
			}
			if(idx != -1) {
				return false;
			}
			return true;
		}
	}
	public String successfulLogin() {
		String id = Utils.getInstence().getInputValue("아이디 입력 : ");
		String pw = Utils.getInstence().getInputValue("비밀번호 입력 : ");
		for(int i = 0; i < clientList.size(); i++) {
			if(id.equals(clientList.get(i).getId())&& pw.equals(clientList.get(i).getPw())) {
				System.out.println("로그인 성공");
				return clientList.get(i).getId();
			}
		}
		return null;
	}
	public void getAccountNum(String clientId, AccountDAO accDAO) {
		String accountNum = Utils.getInstence().getAccountNum();
		if(accountNum == null) {
			System.out.println("올바르지 않은 계좌입니다 다시 입력해주세요");
			return;
		}
		boolean check = accDAO.accountDuple(clientId,accountNum);
		if(check) {
			if(accDAO.accountSizeCheck(clientId)) {
				accDAO.addAccount(clientId,accountNum,getClientNo(clientId));
				System.out.println("계좌 추가완료");
			}else {
				System.out.println("더 이상 계좌를 만들지 못 합니다");
				return;
			}
		}else {
			System.out.println("이미 존재하는 계좌번호입니다.");
			return;
		}
	}
	private int getClientNo(String clientId) {
		int num = 0;
		for(int i = 0; i < clientList.size(); i++) {
			if(clientId.equals(clientList.get(i).getId())) {
				num = clientList.get(i).getClientNo();
				break;
			}
		}
		return num;
	}
	public void delClient(String clientId, AccountDAO accDAO) {
		 accDAO.deleteAccountsByClientId(clientId);
		    for (int i = 0; i < clientList.size(); i++) {
		        if (clientList.get(i).getId().equals(clientId)) {
		            clientList.remove(i);
		            System.out.println("회원 탈퇴가 완료되었습니다.");
		            return;
		        }
		    }
		    System.out.println("해당 회원을 찾을 수 없습니다.");
		}
	/** 회원 수정*/
	public void reClient() {
		if(clientListCheck()) {
			return;
		}
		System.out.println("[회원 수정]");
		String id = Utils.getInstence().getInputValue("아이디 입력 : ");
		int setIdx = setIdCheck(id);
		if(setIdx != -1) {
			System.out.println("수정할 회원 정보를 입력");
			String pw = Utils.getInstence().getInputValue("비밀번호 입력 : ");
			if(pw.equals(clientList.get(setIdx).getPw())) {
				System.out.println("기존과 다른 비밀번호를 입력하세요");
			}else {
				String name = Utils.getInstence().getInputValue("이름 입력 : ");
				clientList.get(setIdx).setPw(pw);
				clientList.get(setIdx).setName(name);
				System.out.println("수정완료!");
				return;
			}
		}else {
			System.out.println("수정할 해당 아이디가 없습니다");
			return;
		}
	}
	private int setIdCheck(String id) {
		int idx = 0;
		for(int i = 0; i < clientList.size(); i++) {
			if(id.equals(clientList.get(i).getId())) {
				idx = i;
				return idx;
			}
		}
		return -1;
	}
	/** 회원 삭제*/
	public void removeClient(AccountDAO accDAO) {
		if(clientListCheck()) {
			return;
		}
		System.out.println("[회원삭제]");
		String id = Utils.getInstence().getInputValue("아이디 입력 : ");
		int delIdx = setIdCheck(id);
		if(delIdx != -1) {
		int sel = Utils.getInstence().getMenuValue("정말로 삭제하시겠습니까? 예 : 0, 아니오 : 1", 0, 1);
			if(sel == 0) {
				delClient(id,accDAO);
			}else {
				return;
			}
		}else {
			System.out.println("삭제 할 회원 아이디가 없습니다");
			return;
		}
	}
	private boolean clientListCheck() {
		if(clientList.size() == 0) {
			System.out.println("회원 목록이 없습니다");
			return true;
		}
		return false;
	}
	/** 저장할 데이터 가지고오기*/
	public String getclientList() {
		String data = "";
		for(int i = 0; i < clientList.size(); i++) {
			data+= clientList.get(i).getClientNo()+"/";
			data+= clientList.get(i).getId()+"/";
			data+= clientList.get(i).getPw()+"/";
			data+= clientList.get(i).getName()+"\n";
		}
		return data;
	}
	
	public void loadAtmData(String dataFile) {
		if(dataFile.isBlank()) {
			System.out.println("데이터가 존재하지 않습니다");
			return;
		}
		String temp[] = dataFile.split("\n");
		for(int i = 0; i < temp.length; i++) {
			String info[] = temp[i].split("/");
			clientList.add(new Client(Integer.parseInt(info[0]),info[1],info[2],info[3]));
		}
		System.out.println("client.txt 불러오기 완료");
	}
}
