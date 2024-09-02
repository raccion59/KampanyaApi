package com.bilgeadam.kampanyaApi.services;

import com.bilgeadam.kampanyaApi.dto.KampanyaDurumDegistirmeDTO;
import com.bilgeadam.kampanyaApi.entities.Kampanya;
import com.bilgeadam.kampanyaApi.entities.KampanyaDurumu;
import com.bilgeadam.kampanyaApi.repositories.KampanyaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class KampanyaServiceTest {

    @Mock
    private KampanyaRepository kampanyaRepository;

    @InjectMocks
    private KampanyaService kampanyaService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIstatistikleriGetir() {
        when(kampanyaRepository.findAll()).thenReturn(Collections.emptyList());

        Map<String, Integer> istatistik = kampanyaService.istatistikleriGetir();

        assertNotNull(istatistik);
        assertEquals(0, istatistik.get("Aktif"));
        assertEquals(0, istatistik.get("Deaktif"));
        assertEquals(0, istatistik.get("Onay Bekliyor"));
        assertEquals(0, istatistik.get("Mükerrer"));
    }

    @Test
    public void testDurumGuncelle_AktifToDeaktif() {
        Kampanya kampanya = new Kampanya();
        kampanya.setId(1L);
        kampanya.setDurum(KampanyaDurumu.AKTIF);

        KampanyaDurumDegistirmeDTO dto = new KampanyaDurumDegistirmeDTO();
        dto.setId(1L);
        dto.setDurum(KampanyaDurumu.DEAKTIF);

        when(kampanyaRepository.findById(1L)).thenReturn(Optional.of(kampanya));

        kampanyaService.durumGuncelle(dto);

        assertEquals(KampanyaDurumu.DEAKTIF, kampanya.getDurum());
        verify(kampanyaRepository).save(kampanya);
    }

    @Test
    public void testDurumGuncelle_MukerrerThrowsException() {
        Kampanya kampanya = new Kampanya();
        kampanya.setId(1L);
        kampanya.setDurum(KampanyaDurumu.MUKERRER);

        KampanyaDurumDegistirmeDTO dto = new KampanyaDurumDegistirmeDTO();
        dto.setId(1L);
        dto.setDurum(KampanyaDurumu.AKTIF);

        when(kampanyaRepository.findById(1L)).thenReturn(Optional.of(kampanya));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> kampanyaService.durumGuncelle(dto));
        assertEquals("Mükerrer kampanyaların durumu güncellenemez.", exception.getMessage());
    }

    @Test
    public void testDurumGuncelle_DeaktifToAktifThrowsException() {
        Kampanya kampanya = new Kampanya();
        kampanya.setId(1L);
        kampanya.setDurum(KampanyaDurumu.DEAKTIF);

        KampanyaDurumDegistirmeDTO dto = new KampanyaDurumDegistirmeDTO();
        dto.setId(1L);
        dto.setDurum(KampanyaDurumu.AKTIF);

        when(kampanyaRepository.findById(1L)).thenReturn(Optional.of(kampanya));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> kampanyaService.durumGuncelle(dto));
        assertEquals("Deaktif hale getirilmiş bir kampanya tekrar Aktif hale getirilemez.", exception.getMessage());
    }
}