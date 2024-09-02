package com.bilgeadam.kampanyaApi.dto;

import com.bilgeadam.kampanyaApi.entities.Kategori;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class KampanyaGirisDTO {

    @NotNull
    @Size(min = 10, max = 50)
    private String baslik;

    @NotNull
    @Size(min = 20, max = 200)
    private String detay;

    @NotNull
    private Kategori kategori;

    // Getters and Setters
    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public String getDetay() {
        return detay;
    }

    public void setDetay(String detay) {
        this.detay = detay;
    }

    public Kategori getKategori() {
        return kategori;
    }

    public void setKategori(Kategori kategori) {
        this.kategori = kategori;
    }
}
