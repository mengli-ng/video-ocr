package xyz.dreamcoder.repository;

import org.springframework.data.repository.CrudRepository;
import xyz.dreamcoder.model.TaskResult;

public interface TaskResultRepository extends CrudRepository<TaskResult, Long> {
}