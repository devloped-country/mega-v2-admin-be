package com.app.mega.mapper;

import com.app.mega.dto.request.InstitutionRequest;
import com.app.mega.dto.request.LocationRequest;
import com.app.mega.dto.response.LocationResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InstitutionMapper {
  Long saveInstitution(InstitutionRequest institutionRequest);
  LocationResponse findInstitutionLocationByEmail(@Param("email") String email);
  void updateInstitutionLocationByEmail(@Param("email") String email, @Param("locationRequest") LocationRequest locationRequest);
}
