package xyz.dreamcoder.repository;

import org.springframework.data.repository.CrudRepository;
import xyz.dreamcoder.model.Setting;

public interface SettingRepository extends CrudRepository<Setting, Long> {

    Setting findByKey(String key);
}