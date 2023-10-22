package com.example.notes.user.service;

import com.example.notes.user.dto.UserDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    void deleteUser(Long userId);

    List<UserDto> findAllUsers(List<Long> ids, Pageable pageable);
}
