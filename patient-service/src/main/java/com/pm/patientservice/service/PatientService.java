package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDto;
import com.pm.patientservice.dto.PatientResponseDto;
import com.pm.patientservice.entity.Patient;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.grpc.BillingServiceGrpcClient;
import com.pm.patientservice.kafka.KafkaProducer;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.repository.PatientRepositoty;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {
    private final PatientRepositoty patientRepositoty;

    private final BillingServiceGrpcClient billingServiceGrpcClient;

    private final KafkaProducer kafkaProducer;

    public PatientService(PatientRepositoty patientRepositoty,
                          BillingServiceGrpcClient billingServiceGrpcClient,
                          KafkaProducer kafkaProducer

    ) {
        this.patientRepositoty = patientRepositoty;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.kafkaProducer = kafkaProducer;
    }

    public List<PatientResponseDto> getAllPatientList() {
        List<Patient> patients = patientRepositoty.findAll();

        List<PatientResponseDto> patientResponseDtos = patients.stream().map(PatientMapper::toDto).toList();

        return patientResponseDtos;
    }

    public PatientResponseDto createPatient(PatientRequestDto patientRequestDto) {

        if (patientRepositoty.existsByEmail(patientRequestDto.getEmail())) {
            throw new EmailAlreadyExistsException("Patient with this email already exists! "
                    + patientRequestDto.getEmail());
        }

        Patient newPatient = patientRepositoty.save(PatientMapper.toModel(patientRequestDto));

        billingServiceGrpcClient.createBillingAccount(
                newPatient.getId().toString(),
                newPatient.getName().toString(),
                newPatient.getEmail().toString());

        kafkaProducer.sendEvent(newPatient);

        return PatientMapper.toDto(newPatient);
    }

    public PatientResponseDto updatePatient( UUID id, PatientRequestDto patientRequestDto){
        Patient patient = patientRepositoty.findById(id)
                .orElseThrow( () ->  new PatientNotFoundException("Patient not found"));

        if(patientRepositoty.existsByEmailAndIdNot(patientRequestDto.getEmail(), id)){
            throw new EmailAlreadyExistsException("Patient with this email already exists! "
                    + patientRequestDto.getEmail());
        }

        patient.setName(patientRequestDto.getName());
        patient.setEmail(patientRequestDto.getEmail());
        patient.setAddress(patientRequestDto.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDto.getDateOfBirth()));

        patientRepositoty.save(patient);

        return PatientMapper.toDto(patient);

    }

    public void deletePatient(UUID id){
        Patient patient = patientRepositoty.findById(id)
                .orElseThrow( () ->  new PatientNotFoundException("Patient not found"));

        patientRepositoty.deleteById(patient.getId());
    }

}
