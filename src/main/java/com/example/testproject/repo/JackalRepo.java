package com.example.testproject.repo;

import com.example.testproject.entity.Jackal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface JackalRepo extends JpaRepository<Jackal, UUID> {
    List<Jackal> findAllByUserId(UUID user_id);
}
