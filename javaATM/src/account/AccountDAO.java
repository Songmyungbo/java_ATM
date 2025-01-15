package account;

import java.util.ArrayList;

import client.Client;
import utils.Utils;

public class AccountDAO {
	ArrayList<Account> accList;

	public void init() {
		accList = new ArrayList<Account>();
	}

	public boolean accountDuple(String clientId, String accountNum) {
		if (accList.size() == 0) {
			return true;
		} else {
			int idx = -1;
			for (int i = 0; i < accList.size(); i++) {
				if (clientId.equals(accList.get(i).getClientId()) && accountNum.equals(accList.get(i).getAccNumber())) {
					idx = i;
				}
			}
			if (idx != -1) {
				return false;
			}
			return true;
		}
	}

	public void addAccount(String clientId, String accountNum, int clientNo) {
		accList.add(new Account(clientNo, clientId, accountNum, 0));
		System.out.println(accList.get(accList.size() - 1));

	}

	public boolean accountSizeCheck(String clientId) {
		int size = 3;
		int cnt = 0;
		for (int i = 0; i < accList.size(); i++) {
			if (clientId.equals(accList.get(i).getClientId())) {
				cnt++;
			}
		}
		if (cnt != size) {
			return true;
		}
		return false;
	}

	public void delAccountNum(String clientId) {
		System.out.println("[계좌번호삭제]");
		String accountNum = Utils.getInstence().getAccountNum();
		if (accountNum != null) {
			int idx = findAccountNum(clientId, accountNum);
			if (idx != -1) {
				accList.remove(idx);
				System.out.println("계좌를 삭제했습니다");
			} else {
				System.out.println("계좌를 찾지 못했습니다");
				return;
			}
		} else {
			System.out.println("계좌를 잘못 입력했습니다");
			return;
		}
	}

	private int findAccountNum(String clientId, String accountNum) {
		int idx = -1;
		for (int i = 0; i < accList.size(); i++) {
			if (clientId.equals(accList.get(i).getClientId()) && accountNum.equals(accList.get(i).getAccNumber())) {
				idx = i;
				break;
			}
		}
		if (idx != -1) {
			return idx;
		}
		return -1;
	}

	public void addMoney(String clientId) {
		System.out.println("[입금]");
		String accountNum = Utils.getInstence().getAccountNum();
		if (accountNum != null) {
			int idx = findAccountNum(clientId, accountNum);
			if (idx != -1) {
				int money = Utils.getInstence().getMoneyValue();
				accList.get(idx).setMoney(money);
				System.out.println("입금 완료!");
			} else {
				System.out.println("계좌가 존재하지 않습니다");
				return;
			}
		} else {
			System.out.println("계좌를 잘못 입력했습니다");
			return;
		}
	}

	public void transfer(String clientId) {
		System.out.println("[본인 계좌]");
		String accountNum = Utils.getInstence().getAccountNum();
		if (accountNum != null) {
			int idx = findAccountNum(clientId, accountNum);
			if (idx == -1) {
				System.out.println("본인 계좌만 선택 가능합니다.");
			} else {
				System.out.println("[상대 계좌]");
				String targetAcc = Utils.getInstence().getAccountNum();
				if (targetAcc != null) {
					if (accDuple(accountNum, targetAcc))return;
					int index = findTargetAccount(targetAcc);
					if (findAccountNum(index))return;
					int money = Utils.getInstence().getMoneyValue();
					if (accList.get(idx).getMoney() - money < 0) {
						System.out.println("잔액 부족");
						return;
					}
					accList.get(idx).setMoney(accList.get(idx).getMoney() - money);
					accList.get(index).setMoney(accList.get(index).getMoney() + money);
					System.out.println("이체 성공!");
				}
				System.out.println("계좌를 다시 입력!");return;
			}
		}
		System.out.println("계좌를 다시 입력!");return;
	}

	private boolean findAccountNum(int idx) {
		if (idx == -1) {
			System.out.println("계좌가 존재하지 않습니다.");
			return true;
		}
		return false;
	}

	private boolean accDuple(String accountNum, String targetAcc) {
		if (targetAcc.equals(accountNum)) {
			System.out.println("동일계좌 이체 불가능");
			return true;
		}
		return false;
	}

	private int findTargetAccount(String targetAcc) {
		int idx = -1;
		for (int i = 0; i < accList.size(); i++) {
			if (targetAcc.equals(accList.get(i).getAccNumber())) {
				idx = i;
			}
		}
		return idx;
	}

	public void withdrawal(String clientId) {
		System.out.println("[출금]");
		String accountNum = Utils.getInstence().getAccountNum();
		if (accountNum == null) {
			System.out.println("계좌를 다시 입력하세요!");
			return;
		} else {
			int idx = findAccountNum(clientId, accountNum);
			if (findAccountNum(idx))return;
			System.out.printf("현재 잔액은 %d원 입니다\n", accList.get(idx).getMoney());
			int money = Utils.getInstence().getMoneyValue();
			if (money > accList.get(idx).getMoney()) {
				System.out.println("잔액이 부족합니다");
				return;
			} else {
				accList.get(idx).setMoney(accList.get(idx).getMoney() - money);
				System.out.println("이체완료");
			}
		}
	}

	public void deleteAccountsByClientId(String clientId) {
		if (accList.size() == 0) {
			return;
		}
		for (int i = 0; i < accList.size(); i++) {
			if (accList.get(i).getClientId().equals(clientId)) {
				accList.remove(i);
				i--;
			}
		}
	}

	public void mypage(String clientId) {
		System.out.println("[마이페이지]");
		for (Account acc : accList) {
			if (clientId.equals(acc.getClientId())) {
				System.out.println(acc);
			}
		}
	}
	/** 저장할 데이터 가지고오기*/
	public String getaccList() {
		String data = "";
		for (int i = 0; i < accList.size(); i++) {
			data += accList.get(i).getClientNo() + "/";
			data += accList.get(i).getClientId() + "/";
			data += accList.get(i).getAccNumber() + "/";
			data += accList.get(i).getMoney() + "\n";
		}
		return data;
	}

	public void loadAtmData(String dataFile) {
		if (dataFile.isBlank()) {
			System.out.println("데이터가 존재하지 않습니다");
			return;
		}
		String temp[] = dataFile.split("\n");
		for (int i = 0; i < temp.length; i++) {
			String info[] = temp[i].split("/");
			accList.add(new Account(Integer.parseInt(info[0]), info[1], info[2], Integer.parseInt(info[3])));
		}
		System.out.println("account.txt 불러오기 완료");
	}
}
