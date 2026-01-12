package com.pm.patientservice.mapper;

import com.pm.patientservice.dto.PatientRequestDto;
import com.pm.patientservice.dto.PatientResponseDto;
import com.pm.patientservice.entity.Patient;

import java.time.LocalDate;

public class PatientMapper {

     public static PatientResponseDto toDto(Patient patient){
        PatientResponseDto patientResponseDto1 = new PatientResponseDto();

        patientResponseDto1.setId(patient.getId().toString());
        patientResponseDto1.setName(patient.getName());
        patientResponseDto1.setEmail(patient.getEmail());
        patientResponseDto1.setAddress(patient.getAddress());
        patientResponseDto1.setDateOfBirth(patient.getDateOfBirth().toString());
        return patientResponseDto1;
    }

    public static Patient toModel(PatientRequestDto patientRequestDto){
         Patient patient = new Patient();

         patient.setName(patientRequestDto.getName());
         patient.setEmail(patientRequestDto.getEmail());
         patient.setAddress(patientRequestDto.getAddress());
         patient.setDateOfBirth(LocalDate.parse(patientRequestDto.getDateOfBirth()));
         patient.setRegisteredDate(LocalDate.parse(patientRequestDto.getDateOfBirth()));
         return patient;
    }
}
