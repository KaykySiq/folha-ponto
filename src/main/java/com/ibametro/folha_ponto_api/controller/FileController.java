package com.ibametro.folha_ponto_api.controller;


import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ibametro.folha_ponto_api.services.FileService;

@RestController
@RequestMapping("/arquivo")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            Workbook workbook = fileService.loadWorkbook(file);
            return ResponseEntity.ok().body("Arquivo carregado com sucesso: " + workbook.getSheetName(0));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao carregar o arquivo: " + e.getMessage());
        }
    }

}
