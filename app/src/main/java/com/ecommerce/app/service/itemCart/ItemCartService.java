package com.ecommerce.app.service.itemCart;

import com.ecommerce.app.dto.itemCart.ItemCartDTO;
import com.ecommerce.app.dto.product.ProductDTO;
import com.ecommerce.app.dto.user.UserDTO;
import com.ecommerce.app.model.itemCart.ItemCart;
import com.ecommerce.app.model.product.Product;
import com.ecommerce.app.model.user.User;
import com.ecommerce.app.repository.itemCart.ItemCartRepository;
import com.ecommerce.app.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemCartService {

    private final ItemCartRepository itemCartRepository;
    private final UserRepository userRepository;

    public List<ItemCartDTO> getAllCartItems() {
        return itemCartRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ItemCartDTO getCartItemById(Long id) {
        ItemCart itemCart = itemCartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item de carrinho não encontrado"));
        return convertToDTO(itemCart);
    }

    // Adicionar item ao carrinho
    public ItemCartDTO addItemToCart(ItemCartDTO itemCartDTO) {
        ItemCart itemCart = new ItemCart();

        // Converter ProductDTO para Product
        Product product = convertToProductEntity(itemCartDTO.getProductDTO());
        itemCart.setProduct(product);
        itemCart.setQuantidade(itemCartDTO.getQuantidade());

        // Vincular o item ao usuário
        User user = userRepository.findById(itemCartDTO.getUserDTO().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        itemCart.setUser(user);

        itemCartRepository.save(itemCart);
        return convertToDTO(itemCart);
    }

    public ItemCartDTO updateItemCart(Long id, ItemCartDTO itemCartDTO) {
        ItemCart itemCart = itemCartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item de carrinho não encontrado!"));

        Product product = convertToProductEntity(itemCartDTO.getProductDTO());
        itemCart.setProduct(product);

        itemCart.setQuantidade(itemCartDTO.getQuantidade());
        itemCartRepository.save(itemCart);

        return convertToDTO(itemCart);
    }

    public void deleteItemCart(Long id) {
        itemCartRepository.deleteById(id);
    }

    private ItemCartDTO convertToDTO(ItemCart itemCart) {
        return ItemCartDTO.builder()
                .id(itemCart.getId())
                .productDTO(convertToProductDTO(itemCart.getProduct())) // Converte Product para ProductDTO
                .quantidade(itemCart.getQuantidade())
                .userDTO(UserDTO.builder()
                        .id(itemCart.getUser().getId())
                        .build())
                .build();
    }

    private ProductDTO convertToProductDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .nome(product.getNome())
                .preco(product.getPreco())
                .categoria(product.getCategoria())
                .nota(product.getNota())
                .cor(product.getCor())
                .estoque(String.valueOf(product.getEstoque()))  // Converte estoque para String
                .build();
    }


    private Product convertToProductEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setNome(productDTO.getNome());
        product.setPreco(productDTO.getPreco());
        product.setCategoria(productDTO.getCategoria());
        product.setNota(productDTO.getNota());
        product.setCor(productDTO.getCor());
        product.setEstoque(Integer.parseInt(productDTO.getEstoque()));  // Converte para int
        return product;
    }


}
