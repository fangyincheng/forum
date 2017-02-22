package beans;

import java.util.Date;

/**
 * Card entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class Card implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer nameId;
	private String contents;
	private Date date;
	private String title;
	private Integer sectionId;
	private Integer replyId;
	private Integer isTop;
	private String topDate;
	private String name;

	// Constructors

	/** default constructor */
	public Card() {
	}

	/** full constructor */
	public Card(String contents, Date date, String title,
			Integer replyId, Integer isTop, String topDate) {
		this.nameId = nameId;
		this.contents = contents;
		this.date = date;
		this.title = title;
		this.sectionId = sectionId;
		this.replyId = replyId;
		this.isTop = isTop;
		this.topDate = topDate;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNameId() {
		return this.nameId;
	}

	public void setNameId(Integer nameId) {
		this.nameId = nameId;
	}

	public String getContents() {
		return this.contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getSectionId() {
		return this.sectionId;
	}

	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}

	public Integer getReplyId() {
		return this.replyId;
	}

	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}

	public Integer getIsTop() {
		return this.isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	public String getTopDate() {
		return this.topDate;
	}

	public void setTopDate(String topDate) {
		this.topDate = topDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}