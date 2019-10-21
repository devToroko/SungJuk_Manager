package com.practice.swing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

//main문이 위치한 클래스이며, Window화면을 띄워주고, 여러가지 작업을 할 수 있습니다.
//자세한 사용법은 "사용법" 버튼을 눌러주세요.
public class Sungjuk_Manager extends JFrame  {
	private static final long serialVersionUID = -7792115079560939808L;
	
	DefaultTableModel model;
	JTable table;
	
	JButton[] buttons = {new JButton("추가"),new JButton("수정"),new JButton("삭제"),new JButton("조회"),new JButton("전체 조회"),new JButton("사용법")};
	
	JTextField textId = new JTextField(5);
	JTextField textName = new JTextField(5);
	JTextField textGrade = new JTextField(3);
	JTextField textLang = new JTextField(3);
	JTextField textEng = new JTextField(3);
	JTextField textMath = new JTextField(3);
	
	
	
	public Sungjuk_Manager(String title) {
		super(title);
		
		// 화면 구성하기
		// BorderLayout.CENTER에는 table을
		// BorderLayout.SOUTH에는 GridLayout를 넣고 위아래로 위는 입력란을, 아래는 버튼 3개 "입력","수정","삭제
		
		String[] colNames = {"학번","이름","학년","국어","영어","수학","총점","평균"}; //위에 놓을 Table의 컬럼 이름을 생성
		DefaultTableModel model = new DefaultTableModel(colNames,0);		//삽입,갱신이 가능하도록 DefaultTableModel 사용
		table= new JTable(model);									//그 model로 테이블을 생성
		table.setDefaultEditor(Object.class, null);//(행을 더블 클릭해서 수정하는 것을 방지)

		
		setPreferredSize(new Dimension(700,500));// 프레임 사이즈 결정
		Container contentPane = getContentPane();// 프레임 내부에 실제 원하는 작업이 일어나는 ContentPane 구함
		setResizable(false);
		
		contentPane.add(new JScrollPane(table),BorderLayout.CENTER);//CENTER와 SOUTH만 사용할 것임, 그중에서 CENTER에 표를 넣는다
		
		
		JPanel panel1 = new JPanel();	//입력할 JTextField를 배치한다.	
		JPanel panel2 = new JPanel();	//실제 동작을 일으킬 JButton을 배치한다.
		JPanel panel3 = new JPanel(new GridLayout(2,0));//2줄을 만들 것이며, 윗줄에는 panel1, 아랫 주에는 panel2를 넣는다.
		
		
		panel1.add(new JLabel("학번 "));
		panel1.add(textId);
		panel1.add(new JLabel("이름 "));
		panel1.add(textName);
		panel1.add(new JLabel("학년 "));
		panel1.add(textGrade);
		panel1.add(new JLabel("국어 "));
		panel1.add(textLang);
		panel1.add(new JLabel("영어 "));
		panel1.add(textEng);
		panel1.add(new JLabel("수학 "));
		panel1.add(textMath);
		
		
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].addActionListener(new BtnActionListener());
			panel2.add(buttons[i]);
			String whatButton = buttons[i].getText();
			if(whatButton.equals("추가")) {buttons[i].setToolTipText("성적을 추가하기 위해서는 국,영,수 점수를 제외한 모든 부분은 필수 입력입니다. 만약 점수를 입력 안하면 0점으로 처리합니다");}
			else if(whatButton.equals("수정")) {buttons[i].setToolTipText("성적을 수정하기 위해서는 id 및 국,영,수 점수는 필수 입력란입니다.");}
			else if(whatButton.equals("삭제")) {buttons[i].setToolTipText("성적을 삭제하기 위해서는 id가 필수 입력입니다");}
			else if(whatButton.equals("조회")) {buttons[i].setToolTipText("특정인을 조회하기 위해서는 id가 필수 입력입니다.");}
		}
		
		
		panel3.add(panel1);
		panel3.add(panel2);
		contentPane.add(panel3,BorderLayout.SOUTH);
		//기본 설정들
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		drawTable();
		setVisible(true);
		
	}
	
	private void drawTable() {	//맨 처음 데이터를 표로 보여줘야한다. 그러기 위한 함수다.
		
		//일단 JTable 상에 있는 테이블을 지운다.
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		model.setNumRows(0);	//현재 테이블에 있는 내용들을 모두 지운다.
		
		//DB에서 학생 데이터를 모아온다. (전체조회중 예외가 발생하면 students는 null을 반환한다)
		ArrayList<Student> students = CRUD_Manager.readAll();
		
		if(students == null) {
			DialogMessage("전체 조회", false, "전체 조회를 시도하는 중 예외가 발생하였습니다.", 0);
			return;
		}
		
		
		//JTable에 넣는 작업을 한다.(= 다시 JTable을 그린다)
		Object[] arr;
		for(Student student : students) {
			arr = new Object[8];
			arr[0] = student.getId();
			arr[1] = student.getName();
			arr[2] = student.getGrade();
			arr[3] = student.getLanguage();
			arr[4] = student.getEnglish();
			arr[5] = student.getMath();
			arr[6] = student.getTotal();
			arr[7] = student.getAvg();
			model.addRow(arr);
		}
		
	}
	
	//특정인에 대한 검색 결과를 JTable에 그린다.
	private void drawTable(String id) {
		//DB에서 학생 데이터를 찾는다. (전체조회중 예외가 발생하면 students는 null을 반환한다)
		Student student = CRUD_Manager.readOne(id);
				
		//예외적인 상황과 아무도 못찾았을 때는 그냥 return;
		if(student == null) {
			DialogMessage("조회",false, "특정 학생 검색 중 예외가 발생하였습니다", 0);
			return;
		} else if(student.getName().equals("Not Found")){
			DialogMessage("조회",false, "해당하는 학생 데이터가 없습니다\nid를 정확히 입력해주십쇼", 0);
			return;
		}	
		
		
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		model.setNumRows(0);	//현재 테이블에 있는 내용들을 모두 지운다.
		
		Object[] arr = new Object[8];
		arr[0] = student.getId();
		arr[1] = student.getName();
		arr[2] = student.getGrade();
		arr[3] = student.getLanguage();
		arr[4] = student.getEnglish();
		arr[5] = student.getMath();
		arr[6] = student.getTotal();
		arr[7] = student.getAvg();
		model.addRow(arr);
		
	}
	
	
	
	// 하나의 ActionListener 구현체로 3개의 버튼에 대한 행동을 결정하겠다
	private class BtnActionListener implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {
			JButton button = (JButton)event.getSource();
			String about = button.getText();
			
			String id = textId.getText();
			String name = textName.getText();
			String grade = textGrade.getText();
			String language = textLang.getText();
			String english = textEng.getText();
			String math = textMath.getText();
			
			ResultInfo ri = null;
			
			if(about.equals("추가")) {		//추가 버튼 눌렀을 떼

				ri = CRUD_Manager.insert(id, name, grade, language, english, math);
			} else if(about.equals("수정")) {//수정 버튼을 눌렀을 때
				
				ri = CRUD_Manager.update(id, language, english, math);
			} else if(about.equals("삭제")) {//삭제 버튼을 눌렀을 때
				
				ri = CRUD_Manager.delete(id);
			} else if(about.equals("조회")) {
				drawTable(id);
				return;
			} else if(about.equals("사용법")) {
				String howToUse = 
						"※사용법\n\n"
					  + "1) 추가\n"
					  + "   -입력란 중에서 id,이름,학년은 반드시 입력해야 정상적인 추가가 됩니다.\n"
					  + "   -국어,영어,수학 점수는 선택적으로 입력하셔도 됩니다. 공백은 0점으로 처리합니다\n\n"
					  + "2) 수정\n"
					  + "    수정은 점수만 됩니다. id, 국영수 점수 입력란을 채워야만 가능합니다\n\n"
					  + "3) 삭제\n"
					  + "    삭제는 id만 입력하면 됩니다.\n\n"
					  + "4) 조회\n"
					  + "    특정인을 조회하기 위해서는 id란을 입력해야만 가능합니다.\n\n"
					  + "5) 전체 조회\n"
					  + "    DB에 저장되어 있는 모든 성적 데이터를 모아서 테이블 형식으로 화면에 뿌려줍니다.\n\n"
					  + "6) 사용법\n"
					  + "    현재 프로그램의 사용법을 볼 수 있습니다.\n";
				JOptionPane.showMessageDialog(null, howToUse, about, JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			
			if(ri != null) 	DialogMessage(about, ri.success, ri.errorMessage, ri.changedRow);
			drawTable();
			
		}
	}
	
	//버튼을 눌렀을 때 상황을 보여준다.
	private static void DialogMessage(String about,boolean success,String errorMessage, int changedRow) {
		if(!success) {
			JOptionPane.showMessageDialog(null, errorMessage, "데이터 "+about+"에 실패하셨습니다", JOptionPane.WARNING_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, changedRow+"개의 행이 "+about+"되었습니다", "데이터 "+about+"을(를) 성공하셨습니다", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	
	public static void main(String[] args) {
		new Sungjuk_Manager("Sungjuk Manager");
	}
	
}



















