package com.excilys.cdb.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.persistence.UserEntity;
import com.excilys.cdb.model.User;

@Component
public class MapperUser {

	private final RoleMapper roleMapper;
	
	public MapperUser(RoleMapper roleMapper) {
		this.roleMapper = roleMapper;
	}

	public UserEntity mapToUser(User user) {
		UserEntity userEntity = null;
		if (user.getRole() != null) {
			userEntity = new UserEntity(user.getId(), roleMapper.mapToRole(user.getRole()), user.getUsername(),
					user.getPassword(), user.isEnabled());
		}
		return userEntity;
	}

	public User mapToUser(UserEntity userEntity) {
		return new User(userEntity.getId(), roleMapper.mapToRole(userEntity.getRoleEntity()), userEntity.getUsername(),
				userEntity.getPassword(), userEntity.isEnabled());
	}

	public List<User> mapToUser(List<UserEntity> userEntity) {
		return userEntity.stream().map(c -> mapToUser(c)).collect(Collectors.toList());
	}

}
