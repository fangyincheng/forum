package beans;

import java.util.Set;

/**
 * Section entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class Section implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private Integer host;
	private String hostName;

	// Constructors

	/** default constructor */
	public Section() {
	}

	/** full constructor */
	public Section(String name, Integer host) {
		this.name = name;
		this.host = host;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getHost() {
		return this.host;
	}

	public void setHost(Integer host) {
		this.host = host;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

}