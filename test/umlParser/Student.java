package umlParser;

import java.util.ArrayList;
import java.util.Collections;

public class Student implements Person {
	private int age = 21;
	public String name = "John Doe";
	protected String gender = "Male";
	double height = 5.5;

	public Student() {
	}

	@Override
	public void age(int numYears) {
		if(numYears < 0){
			ageDown(Math.abs(numYears));
		} else {
			ageUp(numYears);
		}
	}
	
	public void random(){
		ArrayList<String> a = new ArrayList<String>();
		Collections.shuffle(a);
		numFriends(a);
	}
	
	public static int numFriends(ArrayList<String> friends){
		return friends.size();
	}
	
	private void makeFriend(){
		Student friend = new Student();
		friend.jumpUpAndDown();
	}
	
	public void hatesLife(){
		Student me = new RoseHulmanStudent();
		me.makeFriend();
		me.sleep();
	}
	
	public void cries(){
		Student me = new RoseHulmanStudent();
		System.out.println("Sounds of sadness");
		me.hatesLife();
	}
	
	private void ageUp(int numYears){
		this.age += numYears;
	}
	
	private void ageDown(int numYears){
		this.age -= numYears;
	}

	@Override
	public int getAge() {
		return this.age;
	}

	@Override
	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public void jumpUpAndDown() {
		System.out.println("Boing! Boing! Boing!");
	}
	
	void laugh(){
		System.out.println("HAHAHA");
	}
	
	protected void sleep(){
		System.out.println("ZZZZZZZZZZZZ");
	}

}
