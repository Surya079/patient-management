package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientRequestDto;
import com.pm.patientservice.dto.PatientResponseDto;
import com.pm.patientservice.dto.validator.PatientRequestValidationGroup;
import com.pm.patientservice.service.PatientService;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDto>> getPatientsList() {
        List<PatientResponseDto> patientResponseDtos = patientService.getAllPatientList();

        return ResponseEntity.ok().body(patientResponseDtos);
    }

    @PostMapping("/create")
    public ResponseEntity<PatientResponseDto> createNewPatient(
            @Validated({Default.class, PatientRequestValidationGroup.class}) @RequestBody PatientRequestDto patientRequestDto)
    {

        PatientResponseDto patientResponseDto1 = patientService.createPatient(patientRequestDto);
        return ResponseEntity.ok().body(patientResponseDto1);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<PatientResponseDto> updatePatientById(
             @PathVariable UUID id,
           @Validated({Default.class}) @RequestBody PatientRequestDto patientRequestDto
            ){
        PatientResponseDto patientResponseDto = patientService.updatePatient(id, patientRequestDto);

        return ResponseEntity.ok().body(patientResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity

}
