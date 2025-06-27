package com.ibametro.folha_ponto_api.services;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
    public Workbook loadWorkbook(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Arquivo não pode ser nulo ou vazio.");
        }

        try (InputStream inputStream = file.getInputStream()) {
            return WorkbookFactory.create(inputStream);
        } catch (IOException e) {
            throw new IOException("Erro ao carregar o arquivo: " + e.getMessage(), e);
        }
    }
}
