package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import client.Client;
import client.ClientDAO;

public class Utils {

	private Utils() {};
	private static Utils instence;
	private Scanner sc = new Scanner(System.in);
	
	public static Utils getInstence() {
		if (instence == null)
			instence = new Utils();
		return instence;
	}
	private final String CUR_PATH = System.getProperty("user.dir") + "\\src\\" + Utils.class.getPackageName()+ "\\";
	/** 메뉴 선택 입력 */
	public int getMenuValue(String msg, int start, int end) {
		int num = 0;
		while (true) {
			try {
				System.out.printf(msg, start, end);
				num = sc.nextInt();
				if (num < start || num > end) {
					System.out.println("메뉴선택 범위 오류");
					continue;
				}
				return num;
			}catch(InputMismatchException e) {
				System.out.println("숫자만 입력하세요!");
				sc.nextLine();
				continue;
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/** 메인 이름출력*/
	public void printAtmName() {
		System.out.println("=======더조은뱅크=======");
		return;
	}
	public String getInputValue(String msg) {
		System.out.print(msg);
		String id = sc.next();
		sc.nextLine();
		return id;
	}
	public String getAccountNum() {
		System.out.print("계좌번호를 입력하세요 입력 : ");
		String accNum = sc.next();
		sc.nextLine();
		String accPattern = "^\\d{4}-\\d{4}-\\d{4}";
		Pattern pattern = Pattern.compile(accPattern);
		Matcher matcher = pattern.matcher(accNum);
		if(matcher.matches()) {
			return accNum;
		}else {
			return null;
		}
	}

	public int getMoneyValue() {
		int money = 0;
		while(true) {
		try {
			System.out.print("금액을 입력 : ");
			money = sc.nextInt();
			if(money < 100) {
				System.out.println("100원 이상 금액을 입력하세요!");
				continue;
			}
			return money;
		}catch(InputMismatchException e) {
			System.out.println("숫자만 입력하세요!");
			sc.nextLine();
			continue;
		}catch(Exception e) {
			e.printStackTrace();
			}
		}
	}
	/** 저장하기 */
	public void saveDataToaFile(String fileName, String data) {
		String filePath = CUR_PATH + fileName;
		 try(FileWriter fw = new FileWriter(filePath)){
			 fw.write(data);
			 System.out.println(filePath + "저장성공!");
		 }catch(Exception e){
			 System.out.println(filePath + "저장실패!");
			 e.printStackTrace();
		 }
	}
	/** 저장한 데이터 불러오기 */
	public String loadDataFile(String fileName) {
		String filePath = CUR_PATH + fileName;
		String result = "";
		try(FileReader fd = new FileReader(filePath); BufferedReader br = new BufferedReader(fd)){
			while(true) {
				String data = br.readLine();
				if(data == null) {
					break;
				}
				result += data+"\n";
			}
			return result;
		} catch (FileNotFoundException e) {
			System.out.println("파일이 없습니다");
		} catch (IOException e) {
			System.out.println("파일 읽기 실패");
		}
		return null;
	}
}
