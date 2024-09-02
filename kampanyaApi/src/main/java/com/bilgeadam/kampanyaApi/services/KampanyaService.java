package com.bilgeadam.kampanyaApi.services;

import com.bilgeadam.kampanyaApi.dto.KampanyaDTO;
import com.bilgeadam.kampanyaApi.dto.KampanyaGirisDTO;
import com.bilgeadam.kampanyaApi.dto.KampanyaDurumDegistirmeDTO;
import com.bilgeadam.kampanyaApi.entities.Kampanya;
import com.bilgeadam.kampanyaApi.repositories.KampanyaRepository;
import com.bilgeadam.kampanyaApi.entities.Kategori;
import com.bilgeadam.kampanyaApi.entities.KampanyaDurumu;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class KampanyaService {

    @Autowired
    private KampanyaRepository kampanyaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<KampanyaDTO> getAllKampanyalar() {
        List<Kampanya> kampanyalar = kampanyaRepository.findAll();
        return kampanyalar.stream()
                .map(kampanya -> modelMapper.map(kampanya, KampanyaDTO.class))
                .collect(Collectors.toList());
    }

    public void kaydet(KampanyaGirisDTO kampanyaGirisDTO) {
        Kampanya kampanya = modelMapper.map(kampanyaGirisDTO, Kampanya.class);

        // Aynı kategori, başlık ve açıklamaya sahip mevcut kampanyayı bul
        Optional<Kampanya> mevcutKampanya = kampanyaRepository.findFirstByBaslikAndDetayAndKategori(
                kampanya.getBaslik(), kampanya.getDetay(), kampanya.getKategori());

        if (mevcutKampanya.isPresent()) {
            // Mevcut kampanyanın durumunu "Mükerrer" olarak güncelle
            Kampanya mevcut = mevcutKampanya.get();
            mevcut.setDurum(KampanyaDurumu.MUKERRER);
            kampanyaRepository.save(mevcut);
        } else {
            // Yeni kampanya için durum belirle ve kaydet
            if (kampanya.getKategori() == Kategori.BE || kampanya.getKategori() == Kategori.OE || kampanya.getKategori() == Kategori.DIGER) {
                kampanya.setDurum(KampanyaDurumu.ONAY_BEKLIYOR);
            } else {
                kampanya.setDurum(KampanyaDurumu.AKTIF);
            }
            kampanyaRepository.save(kampanya);
        }
    }

    @Transactional
    public void durumGuncelle(KampanyaDurumDegistirmeDTO kampanyaDurumDegistirmeDTO) {
        // Kampanyayı bulamazsak direkt olarak bir exception fırlatıyoruz.
        Kampanya kampanya = kampanyaRepository.findById(kampanyaDurumDegistirmeDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Kampanya bulunamadı: " + kampanyaDurumDegistirmeDTO.getId()));

        // Mükerrer olan kampanyaların güncellenmesini engelliyoruz
        if (kampanya.getDurum() == KampanyaDurumu.MUKERRER) {
            throw new IllegalStateException("Mükerrer kampanyaların durumu güncellenemez.");
        }

        // Durum güncelleme kuralları
        if (kampanyaDurumDegistirmeDTO.getDurum() == KampanyaDurumu.AKTIF && kampanya.getDurum() == KampanyaDurumu.ONAY_BEKLIYOR) {
            kampanya.setDurum(kampanyaDurumDegistirmeDTO.getDurum());
        } else if (kampanyaDurumDegistirmeDTO.getDurum() == KampanyaDurumu.DEAKTIF &&
                (kampanya.getDurum() == KampanyaDurumu.AKTIF || kampanya.getDurum() == KampanyaDurumu.ONAY_BEKLIYOR)) {
            kampanya.setDurum(kampanyaDurumDegistirmeDTO.getDurum());
        } else if (kampanya.getDurum() == KampanyaDurumu.DEAKTIF && kampanyaDurumDegistirmeDTO.getDurum() == KampanyaDurumu.AKTIF) {
            throw new IllegalStateException("Deaktif hale getirilmiş bir kampanya tekrar Aktif hale getirilemez.");
        }

        // Kampanyayı güncelleme işlemi
        kampanyaRepository.save(kampanya);
    }

    public Map<String, Integer> istatistikleriGetir() {
        List<Kampanya> kampanyalar = kampanyaRepository.findAll();
        Map<String, Integer> istatistik = new HashMap<>();

        istatistik.put("Aktif", (int) kampanyalar.stream().filter(k -> k.getDurum() == KampanyaDurumu.AKTIF).count());
        istatistik.put("Deaktif", (int) kampanyalar.stream().filter(k -> k.getDurum() == KampanyaDurumu.DEAKTIF).count());
        istatistik.put("Onay Bekliyor", (int) kampanyalar.stream().filter(k -> k.getDurum() == KampanyaDurumu.ONAY_BEKLIYOR).count());
        istatistik.put("Mükerrer", (int) kampanyalar.stream().filter(k -> k.getDurum() == KampanyaDurumu.MUKERRER).count());

        return istatistik;
    }
}
