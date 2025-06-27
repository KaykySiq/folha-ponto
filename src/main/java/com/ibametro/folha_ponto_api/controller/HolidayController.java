package com.ibametro.folha_ponto_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibametro.folha_ponto_api.services.HolidayService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/feriados")
public class HolidayController {
    private final HolidayService holidayService;

    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @GetMapping()
    public ResponseEntity<List<Integer>> getFeriados(@RequestParam int ano, @RequestParam String mes) {
        List<Integer> feriados = holidayService.getHolidays(ano, mes);
        return ResponseEntity.ok(feriados);
    }
    
    
}
