package com.practice.swing;

//SQL을 통해서 삽입,삭제,갱신 에 대한 결과를 저장하기 위한 클래스입니다.
//저장되는 정보: SQL 성공여부, 에러코드, 에러메시지, 테이블의 변화 유무, 테이블이 변했다면 몇 개가 변했는지 알려주는 정수
public class ResultInfo {
	
	boolean success;	//예외가 발생하지 않고 무사히 SQL을 수행이 되면 success = true, 예외가 발생하면 false다
	int errorCode;		//Sql 작업중 예외가 발생하면 해당 에러의 에러 코드를 받는다.
	String errorMessage;//Sql 작업중 예외가 발생하면 해당 에러의 메시지를 받는다.
	boolean isChanged;	//Sql 작업 후에 테이블에 갱신,삽입,삭제된  row의 갯수가 0이면 false, 1개 이상이면 true이다.
	int changedRow;		//바뀐  row의 갯수다.
	
	public ResultInfo() {}
	
	//성공적으로 되었을 때는 굳이 errorCode와 errorMessage를 작성하지 않아도 되어서 아래와 같이 생성자를 만들었습니다.
	public ResultInfo(boolean success, int changedRow) {
		this.success = success;
		this.changedRow = changedRow;
		isChanged = changedRow > 0 ? true : false;
	}
	

	//삽입,삭제,갱신 중에 문제가 생겨서 예외가 발생하면 그와 관련된 정보들을 한번에 저장하기 위한 것입니다.
	public ResultInfo(boolean success, int errorCode, String errorMessage) {
		this.success = success;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public boolean isChanged() {
		return isChanged;
	}
	
	public int getChangedRow() {
		return changedRow;
	}

	public void setChangedRow(int changedRow) {
		this.changedRow = changedRow;
		isChanged = changedRow > 0 ? true : false;
	}

	@Override
	public String toString() {
		String reason = errorCode >=0 ? "SQL success : "+(success? "success\n" : "fail\n" ) : "INPUT PATTERN FAIL\n";
		return reason + (success? "" : "ERROR_CODE("+errorCode+") -> "+errorMessage )
				+"table changed : "+(isChanged? changedRow+" row has changed" : "no change");
	}
	
	
	
}
