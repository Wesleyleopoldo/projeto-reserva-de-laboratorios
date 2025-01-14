package com.labssyntech.labsSyntech.config;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.labssyntech.labsSyntech.models.Organization;
import com.labssyntech.labsSyntech.models.User;
import com.labssyntech.labsSyntech.repository.OrganizationRepository;
import com.labssyntech.labsSyntech.repository.UserRepository;

@Configuration
public class UserAdminConfig implements CommandLineRunner{
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ConfigService configService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        String userName = configService.getUserName();
        String organizationId = configService.getId();
        String userEmail = configService.getUserEmail();
        String userPassword = configService.getPassword();
        
        Optional<User> userAdmin = userRepository.findByUserName(userName);

        UUID myOrganizationUuid = UUID.fromString(organizationId);

        Optional<Organization> findMyOrganization = organizationRepository.findById(myOrganizationUuid);

        Organization myOrganization = new Organization(myOrganizationUuid, findMyOrganization.get().getOrganizationName());

        userAdmin.ifPresentOrElse(
            user -> {
                System.out.println("Usuário administrador já existe UUID:" + user.getUserId());
            },
            () -> {
                User user = new User();
                user.setUserName(userName);
                user.setEmail(userEmail);
                user.setPassword(bCryptPasswordEncoder.encode(userPassword));
                user.setPresident(true);
                user.setOrganization(myOrganization);

                userRepository.save(user);
            }
        );
    }
}
