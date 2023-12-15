package com.app.mega.service.mybatis;

import com.app.mega.dto.request.InstitutionRequest;
import com.app.mega.dto.request.LocationRequest;
import com.app.mega.dto.response.LocationResponse;
import com.app.mega.mapper.InstitutionMapper;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class InstitutionService {
  private final InstitutionMapper institutionMapper;

  public InstitutionService(InstitutionMapper institutionMapper) {
    this.institutionMapper = institutionMapper;
  }

  public Long saveInstitution(InstitutionRequest institutionRequest) {
    return institutionMapper.saveInstitution(institutionRequest);
  }

  public LocationResponse findInstitutionLocationByEmail(String email) {
    return institutionMapper.findInstitutionLocationByEmail(email);
  }

  public void updateInstitutionLocationByEmail(String email, LocationRequest locationRequest) {
    institutionMapper.updateInstitutionLocationByEmail(email, locationRequest);
  }
}
