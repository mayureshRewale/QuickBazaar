package com.qb.Dao.Entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "qb_user_details")
public class UserDetails {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "qud_id")
    private long id;

    @CreatedDate
    @Column(name = "qud_created_date")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "qud_updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "qud_is_active")
    private Boolean isActive=true;

	@Column(name = "qud_first_name")
	private String firstName;

	@Column(name = "qud_middle_name")
	private String middleName;

	@Column(name = "qud_last_name")
	private String lastName;

	@Column(name = "qa_username", unique=true)
	private String userName;

	@Column(name = "qa_password")
	private String password;

	@Column(name = "qa_mobile", unique=true)
	private String mobile;

	@Column(name = "qa_otp_verification")
	private Boolean otpVerification = true;

	@Column(name = "qa_email")
	private String email;

	@Column(name = "qa_last_login")
	private LocalDateTime lastlogin;

	@Column(name = "qa_mpin")
	private String mpin;

	@Column(name = "qa_roles")
	private String roles;

}
