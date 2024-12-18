package ru.yandex.practicum.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.dto.product.ProductCategory;
import ru.yandex.practicum.dto.product.ProductState;
import ru.yandex.practicum.dto.product.QuantityState;

@Getter
@Setter
@Entity
@Table(name = "products", schema = "product")
public class Product {
    @Id
    @Size(max = 255)
    @Column(name = "product_id")
    private String productId;

    @Size(max = 250)
    @Column(name = "product_name", length = 250)
    private String productName;

    @Size(max = 250)
    @Column(name = "description", length = 250)
    private String description;

    @Size(max = 250)
    @Column(name = "image_src", length = 250)
    private String imageSrc;

    @Column(name = "quantity_state", length = 6)
    @Enumerated(EnumType.STRING)
    private QuantityState quantityState;

    @Column(name = "product_state", length = 10)
    @Enumerated(EnumType.STRING)
    private ProductState productState;

    @Column(name = "rating")
    private Long rating;

    @Column(name = "product_category", length = 8)
    @Enumerated(EnumType.STRING)
    private ProductCategory productCategory;

    @Column(name = "price")
    private Double price;
}
