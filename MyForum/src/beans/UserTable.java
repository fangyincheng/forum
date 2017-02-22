package beans;

import java.util.Set;

/**
 * UserTable entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class UserTable implements java.io.Serializable {

	// Fields
	private Integer id;
	private String username;
	private String password;
	private Integer power;
	private String pic;

	// Constructors

	public UserTable() {
	}

	public UserTable(String username, String password, Integer power, String pic) {
		this.username = username;
		this.password = password;
		this.power = power;
		this.pic = pic;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getPower() {
		return this.power;
	}

	public void setPower(Integer power) {
		this.power = power;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

}