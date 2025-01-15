package com.labssyntech.labsSyntech.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.labssyntech.labsSyntech.models.Organization;
import com.labssyntech.labsSyntech.models.User;
import com.labssyntech.labsSyntech.repository.OrganizationRepository;
import com.labssyntech.labsSyntech.repository.UserRepository;
import com.labssyntech.labsSyntech.utils.UtilTools;

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

        String organizationName = configService.getOrganizationName();
        Optional<Organization> findOrganization = organizationRepository.findByOrganizationName(organizationName);
        Organization myOrganization;
        String userName = configService.getUserName();
        String userEmail = configService.getUserEmail();
        String password = configService.getPassword();

        if(findOrganization.isEmpty()){
            myOrganization = new Organization(organizationName);
            organizationRepository.save(myOrganization);
            System.out.println("Organização Criada com Sucesso!!!");
        } else {
            System.out.println("Organização já criada UUID:" + findOrganization.get().getOrganizationId());
            myOrganization = findOrganization.get();
        }

        Optional<User> findUser = userRepository.findByEmail(userEmail);

        if(findUser.isEmpty()){
            User newUser = new User(
                userName,
                userEmail,
                bCryptPasswordEncoder.encode(password),
             true,
                myOrganization
            );
            userRepository.save(newUser);
            System.out.println("Usuário criado com sucesso UUID: " + newUser.getUserId());
            UtilTools.updateEnviroments(newUser.getUserId().toString(), myOrganization.getOrganizationId().toString());
        } else {
            System.out.println("Usuário já existe!!! UUID: " + configService.getUserId());
        }


    }
}
