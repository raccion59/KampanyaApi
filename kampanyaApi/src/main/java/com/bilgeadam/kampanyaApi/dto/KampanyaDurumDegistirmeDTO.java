package com.bilgeadam.kampanyaApi.dto;

import com.bilgeadam.kampanyaApi.entities.KampanyaDurumu;

public class KampanyaDurumDegistirmeDTO {
    private Long id;
    private KampanyaDurumu durum;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public KampanyaDurumu getDurum() {
        return durum;
    }

    public void setDurum(KampanyaDurumu durum) {
        this.durum = durum;
    }
}
