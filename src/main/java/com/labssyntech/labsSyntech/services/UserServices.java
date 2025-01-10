package com.labssyntech.labsSyntech.services;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.labssyntech.labsSyntech.dto.LoginDTO;
import com.labssyntech.labsSyntech.exception.InvalidCredentialsException;
import com.labssyntech.labsSyntech.exception.ResourceAlredyExistsException;
import com.labssyntech.labsSyntech.models.Organization;
import com.labssyntech.labsSyntech.models.User;
import com.labssyntech.labsSyntech.repository.OrganizationRepository;
import com.labssyntech.labsSyntech.repository.UserRepository;
import com.labssyntech.labsSyntech.requests.LoginRequest;
import com.labssyntech.labsSyntech.requests.SignupRequest;

@Service
public class UserServices {
    
    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private OrganizationService organizationService;
    
    public LoginDTO loginService(LoginRequest loginRequest) {

        Optional<User> user = userRepository.findByEmail(loginRequest.email());

        if(user.isEmpty() || !isLoginCorrect(loginRequest, user.get().getPassword(), bCryptPasswordEncoder)) {
            throw new InvalidCredentialsException("E-mail ou senha inválido!");
        }

        Instant now = Instant.now();
        Long expiresAt = 500L;

        JwtClaimsSet claims = JwtClaimsSet.builder()
                    .issuer("LabSyntechServer")
                    .subject(user.get().getUserId().toString())
                    .issuedAt(now)
                    .expiresAt(now.plusSeconds(expiresAt))
                    .build();


        String jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        LoginDTO loginDTO = new LoginDTO(jwtValue, expiresAt);

        return loginDTO;
    }

    public String signupService(SignupRequest signupRequest) {
        
        Optional<User> findUser = userRepository.findByEmail(signupRequest.email());
        Organization organization = null;

        if(signupRequest.organizationId() != null){
            Optional<Organization> findOrganization = organizationRepository.findById(signupRequest.organizationId());
            organization = new Organization(findOrganization.get().getOrganizationId(), findOrganization.get().getOrganizationName());
        }

        if(findUser.isPresent()) {
            throw new ResourceAlredyExistsException("Usuário já cadastrado ou Empresa não existe");
        }

        User newUser = new User(
                signupRequest.userName(),
                signupRequest.email(),
                bCryptPasswordEncoder.encode(signupRequest.password()),
                signupRequest.isPresident(),
                organization
            );

        if(signupRequest.isPresident() == true) {

            userRepository.save(newUser);
            organizationService.createOrganization(newUser.getUserId(), signupRequest.organizationName());

        }

        return "Sucesso!!!";
    }

    private boolean isLoginCorrect(LoginRequest loginRequest, String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(loginRequest.password(), password);
    }
}
