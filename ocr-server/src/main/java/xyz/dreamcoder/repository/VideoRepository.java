package xyz.dreamcoder.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import xyz.dreamcoder.model.Video;

public interface VideoRepository extends CrudRepository<Video, Long>,
        PagingAndSortingRepository<Video, Long>,
        JpaSpecificationExecutor<Video> {

    Video findByName(String name);
}