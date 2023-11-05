package com.example.notes.user.service;

import com.example.notes.exception.ExistsException;
import com.example.notes.exception.NotFoundException;
import com.example.notes.user.dto.UserDto;
import com.example.notes.user.mapper.UserMapper;
import com.example.notes.user.model.User;
import com.example.notes.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getName())) {
            throw new ExistsException(
                    String.format("Пользователь с почтой " + userDto.getEmail() + " уже существует."));
        }
        User user = userRepository.save(userMapper.toUser(userDto));
        return userMapper.toUserDto(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID [" + userId + "] не найден."));
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAllUsers(List<Long> ids, Pageable pageable) {
        return ids == null ? userMapper.toUserDtoList(userRepository.findAll(pageable).getContent()) :
                userMapper.toUserDtoList(userRepository.findAllByIdIn(ids, pageable).getContent());
    }
}