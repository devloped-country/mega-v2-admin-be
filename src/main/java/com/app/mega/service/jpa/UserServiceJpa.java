package com.app.mega.service.jpa;

import com.app.mega.dto.request.LocationRequest;
import com.app.mega.dto.response.LocationResponse;
import com.app.mega.dto.response.UserInfoResponse;
import com.app.mega.dto.response.UserResponse;
import com.app.mega.entity.Institution;
import com.app.mega.entity.User;
import com.app.mega.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceJpa {

  private final UserRepository userRepository;

  public Boolean findInstitutionIdByEmail(LocationRequest locationRequest) {
    User user = userRepository.findByEmail(locationRequest.getEmail());

    if (user != null) {
      Institution institution = user.getInstitution();

      if (institution != null) {
        Float latitude = institution.getLatitude();
        Float longitude = institution.getLongitude();

        return ((latitude - 0.001) < locationRequest.getLatitude() && locationRequest.getLatitude() < (
            latitude + 0.001)) && ((longitude - 0.001) < locationRequest.getLongitude()
            && locationRequest.getLongitude() < (longitude + 0.001));
      }
    }

    return false;
  }

  public UserInfoResponse findUserInfoByCourseId(Long id) {
    User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    String name = user.getName();
    String course = user.getCourse().getName();

    return new UserInfoResponse(name, course);
  }

  public List<UserResponse> findUsersByCourseId(Long id) {
    List<User> userList = userRepository.findAllByCourseId(id);
    return userList.stream().map(user -> new UserResponse(user.getId(), user.getName(), user.getEmail(),
        user.getPhone(), user.getIsIdentified())).toList();
  }
}
