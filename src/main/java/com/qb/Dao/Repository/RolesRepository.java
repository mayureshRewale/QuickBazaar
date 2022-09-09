package com.qb.Dao.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qb.Dao.Entities.Roles;

import java.util.List;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {

	Roles findByNameAndIsActive(String string, boolean isActive);

    List<Roles> findByNameIn(List<String> roleNameList);
}
