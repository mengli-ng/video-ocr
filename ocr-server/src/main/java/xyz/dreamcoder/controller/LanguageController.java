package xyz.dreamcoder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.dreamcoder.model.Language;
import xyz.dreamcoder.repository.LanguageRepository;

import java.util.List;

@RestController
public class LanguageController {

    private final LanguageRepository languageRepository;

    @Autowired
    public LanguageController(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @GetMapping("/languages")
    public List<Language> getLanguages() {
        return languageRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
    }
}
