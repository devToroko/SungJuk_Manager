package com.practice.swing;

import java.util.regex.Pattern;

//JTextField의 내용이 제대로 입력되었는지 체크해주는 클래스입니다. 정규식을 사용해서 체크합니다.
public class Check {
	
	
	public static String[] insertParameter(String id,String name,String grade,String language,String english,String math) {
		
		//insertParameter 메서드의 역할
		//1. id의 형식이  A1~Z99999 로 표현되었는지 (정규식 사용)
		//2. language,english,math 를 Integer.parseInt 를 하면 예외없이 잘 일어나는지, 일어나면 어떻게 처리할지
		
		if("".equals(id) || "".equals(name) || "".equals(grade)) { throw new TextPatternError("데이터 입력시에는 id, 이름, 학년은 필수 입력란입니다.");}
		
		 //국어,영어,수학 점수를 차례대로 넣을 것이다.
		
		//공백은 0점으로 간주한다.
		if("".equals(language)) {language = "0";}
		if("".equals(english)) {english = "0";}
		if("".equals(math)) {math = "0";}
		
		if(!(Pattern.matches("(^[A-Z]{1}[0-9]{1,5}$)", id))) {throw new TextPatternError("id의 첫자는 A~Z만 입력되고 나머지 1~5자리는 정수로만 이루어집니다.\nex) A001\n");}

		if(!(Pattern.matches("(^[가-힣]{1,6}$)", name))) { throw new TextPatternError("이름은 한글로 1~6글자만 됩니다.");}
		
		if(!(Pattern.matches("(^[1-3]{1}$)", grade))) { throw new TextPatternError("학년은 1~3의 정수만 입력할 수 있습니다.");}
		
		if(Pattern.matches("(^[0-9]{1,3}$)", language) && Pattern.matches("(^[0-9]{1,3}$)", english) && Pattern.matches("(^[0-9]{1,3}$)", math)) {
		
			int l = Integer.parseInt(language);
			int e = Integer.parseInt(english);
			int m = Integer.parseInt(math);
			
			if (!(l >= 0 && l <= 100) || !(e >= 0 && e <= 100) || !(m >= 0 && m <= 100)) {
				throw new TextPatternError("점수는 0과 100사이의 정수만 입력이 가능합니다.");
			} 
			
		} else {
			throw new TextPatternError("점수는 양의 정수만 입력이 가능합니다.");
		}
		
		return new String[] {language,english,math};
	}
	
	public static void updateParamter(String id,String language,String english,String math) {
		
		if("".equals(id) || "".equals(language)  || "".equals(english)  || "".equals(math) ) { throw new TextPatternError("데이터 수정 시에는 id 및 (국어,영어,수학) 점수는 필수 입력란입니다.");}
		
		if(!(Pattern.matches("(^[A-Z]{1}[0-9]{1,5}$)", id))) {throw new TextPatternError("id의 첫자는 A~Z만 입력되고 나머지 1~5자리는 정수로만 이루어집니다.\nex) A001\n");}
		
		if(Pattern.matches("(^[0-9]{1,3}$)", language) && Pattern.matches("(^[0-9]{1,3}$)", english) && Pattern.matches("(^[0-9]{1,3}$)", math)) {
			
			int l = Integer.parseInt(language);
			int e = Integer.parseInt(english);
			int m = Integer.parseInt(math);
			
			if (!(l >= 0 && l <= 100) || !(e >= 0 && e <= 100) || !(m >= 0 && m <= 100)) {
				throw new TextPatternError("점수는 0과 100사이의 정수만 입력이 가능합니다.");
			} 
			
		} else {
			throw new TextPatternError("점수는 양의 정수만 입력이 가능합니다.");
		}
		
	}
	
	public static void deleteParameter(String id) {
		if("".equals(id)) { throw new TextPatternError("데이터 삭제 시에는 id는 필수 입력란입니다.");}
		if(!(Pattern.matches("(^[A-Z]{1}[0-9]{1,5}$)", id))) {throw new TextPatternError("id의 첫자는 A~Z만 입력되고 나머지 1~5자리는 정수로만 이루어집니다.\nex) A001\n");}
	}
	
	//오직 Check 클래스에서만 쓰기 위해서 내부 클래스로 정의 했다.
	public static class TextPatternError extends RuntimeException {

		private static final long serialVersionUID = 1089230970960377084L;

		public TextPatternError(String message) {
			super(message);
		}
		
		public int getErrorCode() {
			return -1;//오라클에서 사용하지 않는 음수의 에러 코드다.
		}
		
	}
	
	public static void main(String[] args) {
		try {
			//System.out.println(Check.insertParameter("A0002", "호로로로로로", "1", "100", "100", "100"));;
//			throw new TextPatternError("원하는 메시지");
//			insertParameter("A0", "박상언", "2", "", "", "");
			updateParamter("A001", "1", "2", "1");
			System.out.println("성공");
			
		} catch (TextPatternError e) {
			System.out.println("실패");
			System.out.println(e.getMessage());
			System.out.println(e.getErrorCode());
		}

	}//main
}
