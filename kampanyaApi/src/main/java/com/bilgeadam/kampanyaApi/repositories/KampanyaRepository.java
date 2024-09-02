package com.bilgeadam.kampanyaApi.repositories;

import com.bilgeadam.kampanyaApi.entities.Kampanya;
import com.bilgeadam.kampanyaApi.entities.Kategori;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KampanyaRepository extends JpaRepository<Kampanya, Long> {
    List<Kampanya> findByBaslikAndDetayAndKategori(String baslik, String detay, Kategori kategori);
    Optional<Kampanya> findFirstByBaslikAndDetayAndKategori(String baslik, String detay, Kategori kategori);
}
