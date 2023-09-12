package text3;

/**
 * 课程实体
 * @author DELL
 *
 */

public class curriculum {
	private int id;
	private String name;
	private String teacher_id;
	private String CreditHour;
	private String ClassTime;
	private String ClassRoom;
	public curriculum() {
		super();
	}
	public curriculum(int id, String name,String CreditHour) {
		super();
		this.id = id;
		this.name = name;
		this.CreditHour = CreditHour;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTeacher_id() {
		return teacher_id;
	}
	public void setTeacher_id(String teacher_id) {
		this.teacher_id = teacher_id;
	}
	public String getCreditHour() {
		return CreditHour;
	}
	public void setCreditHour(String creditHour) {
		CreditHour = creditHour;
	}
	public String getClassTime() {
		return ClassTime;
	}
	public void setClassTime(String classTime) {
		ClassTime = classTime;
	}
	public String getClassRoom() {
		return ClassRoom;
	}
	public void setClassRoom(String classRoom) {
		ClassRoom = classRoom;
	}
	
}
