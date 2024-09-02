package com.bilgeadam.kampanyaApi.dto;

import com.bilgeadam.kampanyaApi.entities.Kategori;
import com.bilgeadam.kampanyaApi.entities.KampanyaDurumu;

public class KampanyaDTO {
    private Long id;
    private String baslik;
    private String detay;
    private Kategori kategori;
    private KampanyaDurumu durum;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public KampanyaDurumu getDurum() {
        return durum;
    }

    public void setDurum(KampanyaDurumu durum) {
        this.durum = durum;
    }
}
