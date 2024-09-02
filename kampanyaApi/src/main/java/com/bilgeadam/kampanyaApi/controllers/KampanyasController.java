package com.bilgeadam.kampanyaApi.controllers;

import com.bilgeadam.kampanyaApi.dto.KampanyaDTO;
import com.bilgeadam.kampanyaApi.dto.KampanyaGirisDTO;
import com.bilgeadam.kampanyaApi.dto.KampanyaDurumDegistirmeDTO;
import com.bilgeadam.kampanyaApi.dto.KampanyaDurumIstatistikDTO;
import com.bilgeadam.kampanyaApi.services.KampanyaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1.0/kampanya")
public class KampanyasController {

    @Autowired
    private KampanyaService kampanyaService;

    @PostMapping("/create")
    public void createKampanya(@Valid @RequestBody KampanyaGirisDTO kampanyaGirisDTO) {
        kampanyaService.kaydet(kampanyaGirisDTO);
    }

    @PostMapping("/updateState")
    public void updateKampanyaState(@RequestBody KampanyaDurumDegistirmeDTO kampanyaDurumDegistirmeDTO) {
        kampanyaService.durumGuncelle(kampanyaDurumDegistirmeDTO);
    }

    @GetMapping("/dashboard/classifieds/statistics")
    public Map<String, Integer> getStatistics() {
        return kampanyaService.istatistikleriGetir();
    }

    @GetMapping("/all")
    public List<KampanyaDTO> getAllKampanyalar() {
        return kampanyaService.getAllKampanyalar();
    }
}
