package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.entity.Warehouse;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    Optional<Warehouse> findByProductId(String productId);

    List<Warehouse> findByProductIdIn(Set<String> productIds);
}
