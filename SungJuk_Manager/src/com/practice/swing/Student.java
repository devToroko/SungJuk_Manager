package com.practice.swing;

//학생의 데이터를 옮기는 클래스입니다.
public final class Student {
	
	private String id;
	private String name;
	private int grade;
	private int language;
	private int english;
	private int math;
	private int total;
	private double avg;

	public Student() {}

	public Student(String id, String name, int grade, int language, int english, int math, int total, double avg) {
		super();
		this.id = id;
		this.name = name;
		this.grade = grade;
		this.language = language;
		this.english = english;
		this.math = math;
		this.total = total;
		this.avg = avg;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public int getLanguage() {
		return language;
	}
	public void setLanguage(int language) {
		this.language = language;
	}
	public int getEnglish() {
		return english;
	}
	public void setEnglish(int english) {
		this.english = english;
	}
	public int getMath() {
		return math;
	}
	public void setMath(int math) {
		this.math = math;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public double getAvg() {
		return avg;
	}
	public void setAvg(double avg) {
		this.avg = avg;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", grade=" + grade + ", language=" + language + ", english="
				+ english + ", math=" + math + "]";
	}

	
	
	
}
