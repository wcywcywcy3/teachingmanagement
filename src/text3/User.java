package text3;

/**
 * 学生实体
 * @author DELL
 *
 */
public class User {

	private String authority;
	private String studentid;
	private String password;
	private String studentname;
	private String classnum;
	public User() {
		super();
	}

	public User(String studentid, String password,String authority) {
		super();
		this.studentid = studentid;
		this.password = password;
		this.authority = authority;
	}
	public String getauthority() {
		return authority;
	}
	public void setauthority(String authority) {
		this.authority = authority;
	}
	public String getname() {
		return studentname;
	}
	public void setname(String studentname) {
		this.studentname = studentname;
	}
	public String getstudentid() {
		return studentid;
	}
	public void setstudentid(String studentid) {
		this.studentid = studentid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setclassnum(String classnum) {
		this.classnum = classnum;
	}
	public String getclassnum() {
		return classnum;
	}
	
}
