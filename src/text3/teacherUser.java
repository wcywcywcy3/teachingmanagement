package text3;

/**
 * 老师实体
 * @author DELL
 *
 */
public class teacherUser {

	private String authority;
	private String teacherid;
	private String password;
	private String name;
	private String department;
	
	public teacherUser() {
		super();
	}

	public teacherUser(String teacherid, String password,String authority) {
		super();
		this.teacherid = teacherid;
		this.password = password;
		this.authority = authority;
	}
	public String getauthority() {
		return authority;
	}
	public void setauthority(String authority) {
		this.authority = authority;
	}
	public String getteacherid() {
		return teacherid;
	}
	public void setteacherid(String teacherid) {
		this.teacherid = teacherid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getname() {
		return name;
	}
	public void setname(String name) {
		this.name = name;
	}
	public String getdepartment() {
		return department;
	}
	public void setdepartment(String department) {
		this.department = department;
	}
}
