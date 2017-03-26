package xyz.dreamcoder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import xyz.dreamcoder.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long>,
        PagingAndSortingRepository<Task, Long>,
        JpaSpecificationExecutor<Task> {
}