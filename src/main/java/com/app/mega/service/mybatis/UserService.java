package com.app.mega.service.mybatis;

import com.app.mega.dto.request.UserRequest;
import com.app.mega.dto.response.UserResponse;
import com.app.mega.mapper.UserMapper;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserMapper userMapper;

  public UserService(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  public UserResponse findUserById(Long id) {
    return userMapper.findUserById(id);
  }
  public UserResponse findUserByEmail(String email) {
    return userMapper.findUserByEmail(email);
  }

  public UserResponse find(String email) {
    return userMapper.findUserByEmail(email);
  }

  public List<UserResponse> findAllUser() {
    return userMapper.findAllUser();
  }

  public void saveUser(UserRequest userRequest) {
    userMapper.saveUser(userRequest);
  }

  public void updateUser(Long id, UserRequest userRequest) {
    userMapper.updateUser(id, userRequest);
  }

  public void deleteUser(Long id) {
    userMapper.deleteUser(id);
  }

}