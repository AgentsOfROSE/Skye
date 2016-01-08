package umlParser;

public class Student implements Person {
	private int age = 21;

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
