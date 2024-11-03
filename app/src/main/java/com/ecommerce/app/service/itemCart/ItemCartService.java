package com.ecommerce.app.service.itemCart;

import com.ecommerce.app.dto.itemCart.ItemCartDTO;
import com.ecommerce.app.dto.product.ProductDetailsDTO;
import com.ecommerce.app.dto.user.UserDTO;
import com.ecommerce.app.model.itemCart.ItemCart;
import com.ecommerce.app.model.product.Product;
import com.ecommerce.app.model.user.User;
import com.ecommerce.app.repository.itemCart.ItemCartRepository;
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

        // Converter ProductDTO para Product
        Product product = convertToProductEntity(itemCartDTO.getProductDetailsDTO());
        itemCart.setProduct(product);
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

        Product product = convertToProductEntity(itemCartDTO.getProductDetailsDTO());
        itemCart.setProduct(product);

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
                .userDTO(UserDTO.builder()
                        .id(itemCart.getUser().getId())
                        .build())
                .build();
    }

    private ProductDetailsDTO convertToProductDTO(Product product) {
        return ProductDetailsDTO.builder()
                .id(product.getId())
                .nome(product.getNome())
                .preco(product.getPreco().toString())
                .categoria(product.getCategoria())
                .nota(product.getNota())
                .cor(product.getCor())
                .estoque(product.getEstoque())  // Converte estoque para String
                .build();
    }


    private Product convertToProductEntity(ProductDetailsDTO productDetailsDTO) {
        Product product = new Product();
        product.setId(productDetailsDTO.getId());
        product.setNome(productDetailsDTO.getNome());
        product.setPreco(Double.parseDouble(productDetailsDTO.getPreco()));
        product.setCategoria(productDetailsDTO.getCategoria());
        product.setNota(productDetailsDTO.getNota());
        product.setCor(productDetailsDTO.getCor());
        product.setEstoque(productDetailsDTO.getEstoque());  // Converte para int
        return product;
    }


}
