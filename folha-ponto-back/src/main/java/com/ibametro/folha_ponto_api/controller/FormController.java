package com.ibametro.folha_ponto_api.controller;

import com.ibametro.folha_ponto_api.services.FormService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<Resource> editarArquivoExcel(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("employeeId") String employeeId,
            @RequestParam("month") String month,
            @RequestParam("year") int year
    ) {
        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(inputStream);

            File tempFile = File.createTempFile("FOLHA_PONTO", ".xls");
            formService.saveChanges(tempFile, workbook, name, employeeId, month, year);

            Resource resource = new FileSystemResource(tempFile);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=folha_editada.xls")
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
