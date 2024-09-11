package com.example.taskmanager.repositories;

import com.example.taskmanager.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project save(Project project);
    Optional<Project> findById(Long id);
    List<Project> findAll();
}
