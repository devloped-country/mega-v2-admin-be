package com.app.mega.service.jpa;

import com.app.mega.dto.request.LocationRequest;
import com.app.mega.dto.response.LocationResponse;
import com.app.mega.entity.Institution;
import com.app.mega.entity.User;
import com.app.mega.repository.UserRepository;
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
}
