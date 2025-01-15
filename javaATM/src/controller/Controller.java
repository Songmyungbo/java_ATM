package controller;

import account.AccountDAO;
import client.ClientDAO;
import utils.Utils;

public class Controller {
	private AccountDAO accDAO;
	private ClientDAO clientDAO;
	
	private void init() {
		accDAO = new AccountDAO();
		clientDAO = new ClientDAO();
		accDAO.init();
		clientDAO.init();
	}
	/** 처음 메뉴 출력*/
	private void printFirstMenu() {
		System.out.println("[1]관리자\n[2]사용자\n[0]종료");
		return;
	}
	/** 사용자 메뉴*/
	private void ClientMenu() {
		while(true) {
			Utils.getInstence().printAtmName();
			printClientFirstMenu();
			int sel = Utils.getInstence().getMenuValue("메뉴 선택[%d-%d] 입력 : ",0,2);
			if(sel == 0) {
				break;
			}else if(sel == 1) {
				clientDAO.join();
			}else if(sel == 2) {
				succecssLoginMenu(clientDAO.successfulLogin());
			}
		}
	}
	private void succecssLoginMenu(String clientId) {
		if(clientId == null) {
			System.out.println("로그인 실패!");
			return;
		}
		while(true) {
			Utils.getInstence().printAtmName();
			logintPrintMenu();
			int sel = Utils.getInstence().getMenuValue("메뉴 선택[%d-%d] 입력 : ",0,7);
			if(sel == 0) {
				break;
			}else if(sel == 1) {
				clientDAO.getAccountNum(clientId,accDAO);
			}else if(sel == 2) {
				accDAO.delAccountNum(clientId);
			}else if(sel == 3) {
				accDAO.addMoney(clientId);
			}else if(sel == 4) {
				accDAO.withdrawal(clientId);
			}else if(sel == 5) {
				accDAO.transfer(clientId);
			}else if(sel == 6) {
				clientDAO.delClient(clientId,accDAO);
				break;
			}else if(sel == 7) {
				accDAO.mypage(clientId);
			}
		}
	}
	private void logintPrintMenu() {
		System.out.println("[1] 계좌추가\n[2] 계좌삭제\n[3] 입금\n[4] 출금\n[5] 이체\n[6] 탈퇴\n[7]마이페이지 [0]로그아웃");
		return;
	}
	private void printClientFirstMenu() {
		System.out.println("[1] 회원가입\n[2] 로그인\n[0] 뒤로가기");
		return;
	}
	/** 관리자 메뉴*/
	private void accountMenu() {
		while(true) {
		Utils.getInstence().printAtmName();
		printAccountFirstMenu();
		int sel = Utils.getInstence().getMenuValue("메뉴 선택[%d-%d] 입력 : ",0,5);
		if(sel == 0) {
			break;
		}else if(sel == 1) {
			clientDAO.printClient();
		}else if(sel == 2) {
			clientDAO.reClient();
		}else if(sel == 3) {
			clientDAO.removeClient(accDAO);
		}else if(sel == 4) {
			Utils.getInstence().saveDataToaFile("client.txt",clientDAO.getclientList());
			Utils.getInstence().saveDataToaFile("account.txt",accDAO.getaccList());
		}else if(sel == 5) {
			clientDAO.loadAtmData(Utils.getInstence().loadDataFile("client.txt"));
			accDAO.loadAtmData(Utils.getInstence().loadDataFile("account.txt"));
		}
	}
}
	/** 관리자 메뉴 출력*/
	private void printAccountFirstMenu() {
		System.out.println("[1] 회원목록\n[2] 회원수정\n[3] 회원삭제\n[4] 데이터 저장\n[5] 데이터 불러오기\n[0] 뒤로가기");
		return;
	}
	
	public void run() {
		init();
		Utils.getInstence();
		while(true) {
			Utils.getInstence().printAtmName();
			printFirstMenu();
			int sel = Utils.getInstence().getMenuValue("메뉴 선택[%d-%d] 입력 : ",0,3);
			if(sel == 1) {
				accountMenu();
			}else if(sel == 2) {
				ClientMenu();
			}else if(sel == 0) {
				System.out.println("종료합니다");
				break;
			}
		}
	}
}
