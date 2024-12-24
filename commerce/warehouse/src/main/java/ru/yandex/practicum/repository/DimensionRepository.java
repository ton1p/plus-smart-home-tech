package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.entity.Dimension;

@Repository
public interface DimensionRepository extends JpaRepository<Dimension, Long> {
}
