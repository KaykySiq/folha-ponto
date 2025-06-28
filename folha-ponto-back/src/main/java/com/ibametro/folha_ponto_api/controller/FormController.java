package com.ibametro.folha_ponto_api.controller;

import com.ibametro.folha_ponto_api.services.FormService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.InputStream;

@RestController
@RequestMapping("/excel")
public class FormController {

    private final FormService formService;

    public FormController(FormService formService) {
        this.formService = formService;
    }

    @PostMapping("/editar")
    public ResponseEntity<String> editarArquivoExcel(
            @RequestParam("file") MultipartFile file,
            @RequestParam("nome") String nome,
            @RequestParam("matricula") String matricula,
            @RequestParam("mes") String mes,
            @RequestParam("ano") int ano
    ) {
        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(inputStream);

            File tempFile = File.createTempFile("folha_editada_", ".xls");
            formService.saveChanges(tempFile, workbook, nome, matricula, mes, ano);

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(tempFile);
            }

            return ResponseEntity.ok("Arquivo editado e aberto com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erro ao processar o arquivo: " + e.getMessage());
        }
    }
}
