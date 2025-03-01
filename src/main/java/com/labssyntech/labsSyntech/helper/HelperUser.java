package com.labssyntech.labsSyntech.helper;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.labssyntech.labsSyntech.config.ConfigService;
import com.labssyntech.labsSyntech.exception.AccessDeniedException;
import com.labssyntech.labsSyntech.exception.NotFoundException;
import com.labssyntech.labsSyntech.models.Organization;
import com.labssyntech.labsSyntech.models.User;
import com.labssyntech.labsSyntech.repository.UserRepository;

@Component
public class HelperUser {
        
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfigService configService;

    public boolean isAdminSyntech(UUID userId) {

        boolean isAdmin = isSuperUser(userId);
        
        if(!isAdmin) {
            throw new AccessDeniedException("Você não tem privilegios de administrador para executar essa ação..." + isAdmin);
        }

        return isAdmin;
    }

    public boolean isPresidentOrAdmin(UUID userId, UUID organizationId) {
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()) {
            throw new NotFoundException("Usuário não existe!!!");
        }

        boolean isAdmin = isSuperUser(userId);

        if(!isAdmin && !user.get().isPresident()) {
            throw new AccessDeniedException("Você não tem privilégios de administrador para executar essa ação!!!");
        } else if(!isAdmin && !user.get().getOrganization().getOrganizationId().equals(organizationId)) {
            throw new AccessDeniedException("Permissão negada: você não pertence a essa empresa...");
        }

        boolean access = isAdmin || user.get().isPresident() ? true : false;

        return access;
    }

    public void setIsPresident(UUID userId, boolean isPresident) {
        Optional<User> userOptional = userRepository.findById(userId);

        if(userOptional.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado...");
        }

        User user = userOptional.get();
        user.setPresident(isPresident);
        userRepository.save(user);
    }

    public void setOrganization(UUID userId, Organization organization) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado...");
        }
        User user = userOptional.get();
        user.setOrganization(organization);
        userRepository.save(user);
    }

    public boolean isSuperUser(UUID userId) {
        String userIdString = userId.toString();
        String adminId = configService.getUserId();
        boolean access = adminId.equals(userIdString) ? true : false;
        return access;
    }

    public boolean verifyUserAndOrganization(UUID userId, UUID organizationId, String userEmail) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<User> userByEmailOptional = userRepository.findByEmail(userEmail);

        if(userOptional.isEmpty() || userByEmailOptional.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado ou não existe...");
        }

        boolean isAdmin = isSuperUser(userId);

        User user = userOptional.get();
        User userByEmail = userByEmailOptional.get();
        if(!isAdmin && !user.isPresident() && !user.getUserId().equals(userByEmail.getUserId())){
            throw new AccessDeniedException("Você não tem privilégios de administrador para executar essa ação!!!");
        } else if(!isAdmin && !userByEmail.getOrganization().getOrganizationId().equals(organizationId)) {
            throw new AccessDeniedException("Permissão negada: você não pertence a essa empresa...");
        }

        boolean access = isAdmin || user.isPresident() || user.getUserId().equals(userByEmail.getUserId()) ? true : false;

        return access;
    }
}