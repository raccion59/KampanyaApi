package com.bilgeadam.kampanyaApi.dto;

public class KampanyaDurumIstatistikDTO {
    private int aktifSayisi;
    private int deaktifSayisi;
    private int onayBekleyenSayisi;
    private int mukerrerSayisi;

    // Getters and Setters
    public int getAktifSayisi() {
        return aktifSayisi;
    }

    public void setAktifSayisi(int aktifSayisi) {
        this.aktifSayisi = aktifSayisi;
    }

    public int getDeaktifSayisi() {
        return deaktifSayisi;
    }

    public void setDeaktifSayisi(int deaktifSayisi) {
        this.deaktifSayisi = deaktifSayisi;
    }

    public int getOnayBekleyenSayisi() {
        return onayBekleyenSayisi;
    }

    public void setOnayBekleyenSayisi(int onayBekleyenSayisi) {
        this.onayBekleyenSayisi = onayBekleyenSayisi;
    }

    public int getMukerrerSayisi() {
        return mukerrerSayisi;
    }

    public void setMukerrerSayisi(int mukerrerSayisi) {
        this.mukerrerSayisi = mukerrerSayisi;
    }
}
