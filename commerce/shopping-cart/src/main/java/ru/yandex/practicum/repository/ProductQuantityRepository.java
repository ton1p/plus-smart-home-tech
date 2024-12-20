package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.entity.ProductQuantity;

@Repository
public interface ProductQuantityRepository extends JpaRepository<ProductQuantity, Long> {
}
