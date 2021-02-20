package com.urlapp.app.logic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.urlapp.app.utils.DomainConstants;
//creating URL entity
@Entity
@Table(name="url")
public class URLShortener implements DomainConstants{
	@Id
    @Column(name = "id")
    private Long id;
	
	@Column(name = "created_on", updatable = false)
    @Type(type = COMMON_DATE_TYPE)
	private DateTime createdOn = new DateTime();
	
	@Column(name = "original_url")
	private String originalURL;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(DateTime createdOn) {
		this.createdOn = createdOn;
	}

	public String getOriginalURL() {
		return originalURL;
	}

	public void setOriginalURL(String originalURL) {
		this.originalURL = originalURL;
	}

}
