package com.labssyntech.labsSyntech.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.labssyntech.labsSyntech.dto.LoginDTO;
import com.labssyntech.labsSyntech.dto.UserDTO;
import com.labssyntech.labsSyntech.exception.InternalErrorException;
import com.labssyntech.labsSyntech.exception.InvalidCredentialsException;
import com.labssyntech.labsSyntech.exception.NotFoundException;
import com.labssyntech.labsSyntech.exception.ResourceAlredyExistsException;
import com.labssyntech.labsSyntech.helper.HelperOrganization;
import com.labssyntech.labsSyntech.models.Organization;
import com.labssyntech.labsSyntech.models.User;
// import com.labssyntech.labsSyntech.repository.OrganizationRepository;
import com.labssyntech.labsSyntech.repository.UserRepository;
import com.labssyntech.labsSyntech.requests.LoginRequest;
import com.labssyntech.labsSyntech.requests.PresidentSignupRequest;
import com.labssyntech.labsSyntech.requests.UserSignupRequest;

@Service
public class UserServices {
    
    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private UserRepository userRepository;

    // @Autowired
    // private OrganizationRepository organizationRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private HelperOrganization helperOrganization;
    
    public LoginDTO loginService(LoginRequest loginRequest) {

        Optional<User> user = userRepository.findByEmail(loginRequest.email());

        if(user.isEmpty() || !isLoginCorrect(loginRequest, user.get().getPassword(), bCryptPasswordEncoder)) {
            throw new InvalidCredentialsException("E-mail ou senha inválido!");
        }

        Instant now = Instant.now();
        Long expiresAt = 800L;

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

    @Transactional
    public UserDTO signupPresidentService(PresidentSignupRequest signupRequest) {
        
        Optional<User> findUser = userRepository.findByEmail(signupRequest.email());
        Organization organization = null;

        if(findUser.isPresent()) {
            throw new ResourceAlredyExistsException("Usuário já cadastrado");
        }

        User newUser = new User(
                signupRequest.userName(),
                signupRequest.email(),
                bCryptPasswordEncoder.encode(signupRequest.password()),
                signupRequest.isPresident(),
                organization
            );

        userRepository.save(newUser);

        UserDTO userDTO = new UserDTO(newUser.getUserId(), signupRequest.password(), newUser.getEmail(), newUser.getPassword(), newUser.isPresident(), newUser.getOrganization());

        return userDTO;
    }

    public UserDTO signupUserService(UserSignupRequest signupRequest, UUID organizationId) {
        Optional<User> findUser = userRepository.findByEmail(signupRequest.email());
        Organization organization = null;

        if(findUser.isPresent()) {
            throw new ResourceAlredyExistsException("Usuário já cadastrado");
        }

        organization = helperOrganization.findOrganizationById(organizationId, "Organização fornecida não existe");

        User newUser = new User(
            signupRequest.userName(),
            signupRequest.email(),
            bCryptPasswordEncoder.encode(signupRequest.password()),
            signupRequest.isPresident(),
            organization
        );

        userRepository.save(newUser);

        UserDTO userDTO = new UserDTO(newUser.getUserId(), signupRequest.password(), newUser.getEmail(), newUser.getPassword(), newUser.isPresident(), newUser.getOrganization());

        return userDTO;
    }

    public List<UserDTO> getAllUsersServices() {
        List<User> usersListOptional = userRepository.findAll();

        if(usersListOptional.isEmpty()) {
            throw new NotFoundException("Não há usuários cadastrados...");
        }

        List<UserDTO> userDTOList = usersListOptional.stream().map(
            user -> createDTO(user)
        ).toList();

        return userDTOList;
    }

    public UserDTO updateUserOrganization(UUID userId, Organization organization) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado...");
        }
        User user = userOptional.get();
        user.setOrganization(organization);
        userRepository.save(user);

        UserDTO userDTO = createDTO(user);

        return userDTO;
    }

    public UserDTO updateIsAdmin(UUID userId, boolean isAdmin) {
        Optional<User> userOptional = userRepository.findById(userId);

        if(userOptional.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado...");
        }

        User user = userOptional.get();
        user.setPresident(isAdmin);
        userRepository.save(user);

        UserDTO userDTO = createDTO(user);

        return userDTO;
    }

    public UserDTO destroyUser(UUID userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        
        if(userOptional.isEmpty()) {
            throw new NotFoundException("ID errado ou usuário não existe...");
        }

        User user = userOptional.get();
        UserDTO userDTO = createDTO(user);

        try {
            userRepository.delete(user);
            return userDTO;
        } catch (Exception e) {
            throw new InternalErrorException("Serviço indisponível temporariamente...");
        }
    }

    private boolean isLoginCorrect(LoginRequest loginRequest, String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(loginRequest.password(), password);
    }

    private UserDTO createDTO(User user) {
        return new UserDTO(
            user.getUserId(), 
            user.getUserName(), 
            user.getEmail(), 
            user.getPassword(), 
            user.isPresident(), 
            user.getOrganization()
        );
    }
}