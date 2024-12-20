package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.cart.CartDto;
import ru.yandex.practicum.dto.cart.ChangeProductQuantityRequest;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.entity.Cart;
import ru.yandex.practicum.entity.CartState;
import ru.yandex.practicum.entity.ProductQuantity;
import ru.yandex.practicum.operation.WarehouseOperations;
import ru.yandex.practicum.repository.CartRepository;
import ru.yandex.practicum.repository.ProductQuantityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductQuantityRepository productQuantityRepository;
    private final WarehouseOperations warehouseOperations;

    private Cart getCart(String username) {
        Optional<Cart> optionalCart = cartRepository.findByUsername(username);
        if (optionalCart.isPresent()) {
            return optionalCart.get();
        }
        Cart cart = Cart.builder()
                .username(username)
                .productQuantity(new ArrayList<>())
                .state(CartState.ACTIVE)
                .build();
        return cartRepository.save(cart);
    }

    @Override
    public CartDto getCartByUsername(String username) {
        return toCartDto(getCart(username));
    }

    @Override
    public CartDto addItemsToCart(String username, Map<String, Integer> products) {
        Cart cart = getCart(username);
        Map<String, Integer> mapFromCart = listProductQuantityToMap(cart.getProductQuantity());
        mapFromCart.putAll(products);
        cart.setProductQuantity(mapProductQuantityToList(mapFromCart));
        productQuantityRepository.saveAll(cart.getProductQuantity());
        CartDto cartDto = toCartDto(cartRepository.save(cart));
        warehouseOperations.checkCart(cartDto);
        return cartDto;
    }

    @Override
    public void deactivateCart(String username) {
        Cart cart = getCart(username);
        cart.setState(CartState.DEACTIVATED);
        cartRepository.save(cart);
    }

    @Override
    public CartDto removeItems(String username, List<String> products) {
        Cart cart = getCart(username);
        Map<String, Integer> mapFromCart = listProductQuantityToMap(cart.getProductQuantity());
        for (String productId : products) {
            mapFromCart.remove(productId);
        }
        cart.setProductQuantity(cart.getProductQuantity()
                .stream()
                .filter(i -> mapFromCart.containsKey(i.getProductId())).toList());

        productQuantityRepository.saveAll(cart.getProductQuantity());
        return toCartDto(cartRepository.save(cart));
    }

    @Override
    public CartDto changeQuantity(String username, ChangeProductQuantityRequest changeProductQuantityRequest) {
        Cart cart = getCart(username);
        List<ProductQuantity> productQuantity = cart.getProductQuantity();
        for (ProductQuantity productQuantityItem : productQuantity) {
            if (productQuantityItem.getProductId().equals(changeProductQuantityRequest.getProductId())) {
                productQuantityItem.setQuantity(changeProductQuantityRequest.getNewQuantity());
            }
        }
        cart.setProductQuantity(productQuantity);
        productQuantityRepository.saveAll(cart.getProductQuantity());
        return toCartDto(cartRepository.save(cart));
    }

    @Override
    public BookedProductsDto bookingProducts(String username) {
        Cart cart = getCart(username);
        CartDto cartDto = toCartDto(cart);
        return warehouseOperations.checkCart(cartDto);
    }

    private Map<String, Integer> listProductQuantityToMap(final List<ProductQuantity> productQuantities) {
        return productQuantities
                .stream()
                .collect(Collectors.toMap(ProductQuantity::getProductId, ProductQuantity::getQuantity));
    }

    private List<ProductQuantity> mapProductQuantityToList(final Map<String, Integer> productQuantityMap) {
        List<ProductQuantity> productQuantities = new ArrayList<>();
        productQuantityMap.forEach((productId, quantity) -> {
            ProductQuantity productQuantity = ProductQuantity.builder()
                    .productId(productId)
                    .quantity(quantity)
                    .build();
            productQuantities.add(productQuantity);
        });
        return productQuantities;
    }

    private CartDto toCartDto(Cart cart) {
        return CartDto.builder()
                .shoppingCartId(cart.getShoppingCartId())
                .products(listProductQuantityToMap(cart.getProductQuantity()))
                .build();
    }
}
