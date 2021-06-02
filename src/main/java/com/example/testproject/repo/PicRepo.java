package com.example.testproject.repo;

import com.example.testproject.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface PicRepo extends JpaRepository<Picture, UUID> {
    Picture findByJackalJackalId(UUID jackalId);
}
