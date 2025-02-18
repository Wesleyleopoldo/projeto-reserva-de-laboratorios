package com.labssyntech.labsSyntech.utils;

import java.util.Optional;
import java.util.UUID;

import com.labssyntech.labsSyntech.config.ConfigService;
import com.labssyntech.labsSyntech.exception.AccessDeniedException;
import com.labssyntech.labsSyntech.exception.NotFoundException;
import com.labssyntech.labsSyntech.models.User;
import com.labssyntech.labsSyntech.repository.UserRepository;

public class UtilsUser {

    private UtilsUser(){
        throw new UnsupportedOperationException("Essa classe é utilitária e não pode ser instanciada!!");
    }
        

    static boolean validationUser(ConfigService configService, UUID userId) {

        boolean isAdmin = isAdmin(configService, userId);
        
        if(!isAdmin) {
            throw new AccessDeniedException("Você não tem privilegios de administrador para executar essa ação..." + isAdmin);
        }

        return isAdmin;
    }

    static boolean isPresidentOrAdmin(ConfigService configService, UserRepository userRepository, UUID userId) {
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()) {
            throw new NotFoundException("Usuário não existe!!!");
        }

        boolean isAdmin = isAdmin(configService, userId);

        if(!isAdmin && !user.get().isPresident()) {
            throw new AccessDeniedException("Você não tem privilégios de administrador para executar essa ação!!!");
        }

        return isAdmin;
    }

    static boolean isAdmin(ConfigService configService, UUID userId) {
        String userIdString = userId.toString();
        String adminId = configService.getUserId();
        return adminId.equals(userIdString) ? true : false;
    }
}
