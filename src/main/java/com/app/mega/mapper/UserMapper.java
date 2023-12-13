package com.app.mega.mapper;

import com.app.mega.dto.request.UserRequest;
import com.app.mega.dto.response.UserResponse;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
  UserResponse findUserById(Long id);
  UserResponse findUserByEmail(String email);
  List<UserResponse> findAllUser();
  void saveUser(UserRequest userRequest);
  void updateUser(@Param("id") Long id, @Param("userRequest") UserRequest userRequest);
  void deleteUser(Long id);
}
