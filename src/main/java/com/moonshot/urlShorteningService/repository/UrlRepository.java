package com.moonshot.urlShorteningService.repository;

import com.moonshot.urlShorteningService.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

    Optional<Url> findByShortUrlLink(String ShortUrlLink);
}
