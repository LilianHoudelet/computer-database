package com.excilys.cdb.mapper;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.persistence.RoleEntity;
import com.excilys.cdb.model.Role;

@Component
public class RoleMapper {

	public RoleEntity mapToRole(Role role) {
		return new RoleEntity(role.getId(), role.getRole());
	}

	public Role mapToRole(RoleEntity roleEntity) {
		return new Role(roleEntity.getId(), roleEntity.getRole());
	}

}
