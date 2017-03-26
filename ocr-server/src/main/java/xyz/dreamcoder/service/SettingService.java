package xyz.dreamcoder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.dreamcoder.model.Setting;
import xyz.dreamcoder.repository.SettingRepository;

@Service
public class SettingService {

    private final SettingRepository settingRepository;

    @Autowired
    public SettingService(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    public String getSetting(String key) {

        Setting setting = settingRepository.findByKey(key);
        return setting == null ? null : setting.getValue();
    }

    public void setSetting(String key, String value) {

        Setting setting = settingRepository.findByKey(key);
        if (setting == null) {
            setting = new Setting();
            setting.setKey(key);
        }

        setting.setValue(value);
        settingRepository.save(setting);
    }
}