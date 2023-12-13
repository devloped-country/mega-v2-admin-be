package com.app.mega.service.mybatis;

import com.app.mega.dto.request.InstitutionRequest;
import com.app.mega.mapper.InstitutionMapper;
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
}
