package com.example.testproject.repo;

import com.example.testproject.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PicRepo extends JpaRepository<Picture, UUID> {
}
