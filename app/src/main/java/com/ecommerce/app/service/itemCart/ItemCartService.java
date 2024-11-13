package com.ecommerce.app.service.itemCart;

import com.ecommerce.app.dto.itemCart.ItemCartDTO;
import com.ecommerce.app.dto.product.ProductDetailsDTO;
import com.ecommerce.app.dto.user.UserDTO;
import com.ecommerce.app.model.itemCart.ItemCart;
import com.ecommerce.app.model.product.Product;
import com.ecommerce.app.model.user.User;
import com.ecommerce.app.repository.itemCart.ItemCartRepository;
import com.ecommerce.app.repository.product.ProductRepository;
import com.ecommerce.app.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemCartService {

    private final ItemCartRepository itemCartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public List<ItemCartDTO> getAllCartItems() {
        return itemCartRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ItemCartDTO getCartItemById(Integer id) {
        ItemCart itemCart = itemCartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item de carrinho não encontrado"));
        return convertToDTO(itemCart);
    }

    // Adicionar item ao carrinho
    public ItemCartDTO addItemToCart(ItemCartDTO itemCartDTO) {
        ItemCart itemCart = new ItemCart();

        // Buscar e validar o produto
        Product product = productRepository.findById(itemCartDTO.getProductDetailsDTO().getId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        itemCart.setProduct(product);

        // Validar e definir quantidade
        itemCart.setQuantidade(itemCartDTO.getQuantidade());

        // Vincular o item ao usuário
        User user = userRepository.findById(itemCartDTO.getUserDTO().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        itemCart.setUser(user);

        itemCartRepository.save(itemCart);
        return convertToDTO(itemCart);
    }

    public ItemCartDTO updateItemCart(Integer id, ItemCartDTO itemCartDTO) {
        ItemCart itemCart = itemCartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item de carrinho não encontrado!"));

        // Buscar e validar o produto
        Product product = productRepository.findById(itemCartDTO.getProductDetailsDTO().getId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        itemCart.setProduct(product);

        // Validar e definir quantidade
        itemCart.setQuantidade(itemCartDTO.getQuantidade());

        itemCartRepository.save(itemCart);
        return convertToDTO(itemCart);
    }

    public void deleteItemCart(Integer id) {
        itemCartRepository.deleteById(id);
    }

    private ItemCartDTO convertToDTO(ItemCart itemCart) {
        return ItemCartDTO.builder()
                .id(itemCart.getId())
                .productDetailsDTO(convertToProductDTO(itemCart.getProduct())) // Converte Product para ProductDTO
                .quantidade(itemCart.getQuantidade())
                .preco(itemCart.getProduct().getPreco() * itemCart.getQuantidade()) // Calcula o preço total
                .userDTO(UserDTO.builder()
                        .id(itemCart.getUser().getId())
                        .build())
                .build();
    }

    private ProductDetailsDTO convertToProductDTO(Product product) {
        return ProductDetailsDTO.builder()
                .id(product.getId())
                .name(product.getNome())
                .price(product.getPreco()) // Certifica-se de que é Double
                .categoria(product.getCategoria())
                .rating(product.getNota())
                .color(product.getCor())
                .estoque(product.getEstoque())
                .build();
    }
}