package com.qb.Dao.Entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "qb_roles")
public class Roles {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "qr_id")
    private long id;

    @CreatedDate
    @Column(name = "qr_created_date")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "qr_update_date")
    private LocalDateTime updatedDate;

    @Column(name = "qr_is_active")
    private Boolean isActive=true;
	
	@Column(name = "qr_name")
    private String name;

    @Column(name = "qr_description")
    private String description;

}
