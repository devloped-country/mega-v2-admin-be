package com.app.mega.mapper;

import com.app.mega.dto.request.InstitutionRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InstitutionMapper {
  Long saveInstitution(InstitutionRequest institutionRequest);
}
