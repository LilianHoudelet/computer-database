package com.excilys.cdb.mapper;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.persistence.UserEntity;
import com.excilys.cdb.model.User;

@Component
public class MapperUser {
	public UserEntity mapFromModelToDTOPersistance(User user) {
		return new UserEntity(user.getId(), user.getAuthority(), user.getUsername(), user.getPassword(),
				user.isEnabled());
	}
}
