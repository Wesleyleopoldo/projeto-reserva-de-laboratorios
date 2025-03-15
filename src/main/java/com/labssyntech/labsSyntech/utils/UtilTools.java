package com.labssyntech.labsSyntech.utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UtilTools {
    
    private UtilTools() {
        throw new UnsupportedOperationException("Essa classe é utilitária e não pode ser instanciada!!");
    }

    public static void updateEnviroments(String userId, String organizationId) {
        String filePath = ".env"; // Caminho para o arquivo...
        String newOrganizationId = organizationId; // Novo id da organização para teste...
        String newUserId = userId; // Novo id do usuário para teste...

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath)); // Lendo todo o arquivo e adicionando todo o conteúdo numa lista...
            List<String> updatedLines = new ArrayList<>(); // Criando um novo arraylist para concatenar as novas atualizações...

            for(String line : lines) {
                if(line.startsWith("ORGANIZATION_ID=")){
                    updatedLines.add("ORGANIZATION_ID=" + newOrganizationId);
                } else if(line.startsWith("USER_ID=")) {
                    updatedLines.add("USER_ID=" + newUserId);
                } else {
                    updatedLines.add(line);
                }
            }

            Files.write(Paths.get(filePath), updatedLines);

            System.out.println("Atualizado com sucesso!!!");
        } catch (Exception e) {
            System.err.println("Algo deu errado... " + e.getMessage());
        }
    }
}
