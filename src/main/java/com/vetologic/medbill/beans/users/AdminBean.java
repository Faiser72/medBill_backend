package com.vetologic.medbill.beans.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

/**
 * @author Manjunath : 20-Jan-2021
 *
 */
@Entity
@Data
@Table(name = "admin", uniqueConstraints = @UniqueConstraint(columnNames = { "username", "email_id" }))
public class AdminBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "admin_id")
	private int adminId;

	@Column(name = "deletion_flag")
	private int deletionFlag;

	@Column(name = "username")
	private String username;
	
	@Column(name = "email_id")
	private String emailId;

	@Column(name = "password")
	private String password;

	@Column(name = "display_name")
	private String displayName;

	@Column(name = "created_date")
	private String createdDate;

	@Column(name = "updated_date")
	private String updatedDate;
	
	@Column(name = "role")
	private String role;
}
