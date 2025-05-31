package com.telconova.telconovaf2.application.service.security;

import com.telconova.telconovaf2.domain.entities.*;
import com.telconova.telconovaf2.domain.repository.*;
import com.telconova.telconovaf2.infrastructure.security.JwtUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final SupervisorRepositoryI supervisorRepository;
    private final TechnicianRepositoryI technicianRepository;
    private final SpecialtyRepositoryI specialtyRepository;
    private final ZoneRepositoryI zoneRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthService(UserRepository userRepository,
                       SupervisorRepositoryI supervisorRepository,
                       TechnicianRepositoryI technicianRepository,
                       SpecialtyRepositoryI specialtyRepository,
                       ZoneRepositoryI zoneRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.supervisorRepository = supervisorRepository;
        this.technicianRepository = technicianRepository;
        this.specialtyRepository = specialtyRepository;
        this.zoneRepository = zoneRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public String login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }
        return jwtUtils.generateToken(user);
    }

    public void register(String email, String name, String rawPassword, String role, Integer specialtyId, Integer zoneId) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(Role.valueOf(role.toUpperCase()));
        userRepository.save(user);

        // Buscar las relaciones
        Specialty specialty = specialtyRepository.findById(specialtyId)
                .orElseThrow(() -> new RuntimeException("Specialty no encontrada"));
        Zone zone = zoneRepository.findById(zoneId)
                .orElseThrow(() -> new RuntimeException("Zone no encontrada"));

        if (role.equalsIgnoreCase("SUPERVISOR")) {
            Supervisor supervisor = new Supervisor();
            supervisor.setEmail(email);
            supervisor.setName(name);
            supervisor.setPassword(passwordEncoder.encode(rawPassword));
            supervisor.setSpecialty(specialty);
            supervisor.setZone(zone);
            supervisorRepository.save(supervisor);

        } else if (role.equalsIgnoreCase("TECHNICIAN")) {
            Technician technician = new Technician();
            technician.setEmail(email);
            technician.setName(name);
            technician.setPassword(passwordEncoder.encode(rawPassword));
            technician.setSpecialty(specialty);
            technician.setZone(zone);
            technicianRepository.save(technician);
        }
    }



}

