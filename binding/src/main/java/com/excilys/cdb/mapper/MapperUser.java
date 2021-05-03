package com.excilys.cdb.mapper;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.persistence.UserDTOPersistence;
import com.excilys.cdb.model.User;

@Component
public class MapperUser {
	public UserDTOPersistence mapFromModelToDTOPersistance(User user) {
		return new UserDTOPersistence(user.getId(), user.getAuthority(), user.getUsername(), user.getPassword(),
				user.isEnabled());
	}
}
