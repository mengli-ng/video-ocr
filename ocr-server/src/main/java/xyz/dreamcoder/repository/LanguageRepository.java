package xyz.dreamcoder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import xyz.dreamcoder.model.Language;

public interface LanguageRepository extends JpaRepository<Language, Long>,
        PagingAndSortingRepository<Language, Long> {
}