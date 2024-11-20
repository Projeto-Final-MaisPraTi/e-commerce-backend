package com.ecommerce.app.service.itemCart;

import com.ecommerce.app.dto.itemCart.ItemCartDTO;
import com.ecommerce.app.dto.itemCart.ItemCartDetailsDTO;
import com.ecommerce.app.dto.product.ProductDTO;
import com.ecommerce.app.dto.product.ProductDetailsDTO;
import com.ecommerce.app.dto.user.UserDTO;
import com.ecommerce.app.infra.security.CustomUserDetails;
import com.ecommerce.app.model.itemCart.ItemCart;
import com.ecommerce.app.model.product.Product;
import com.ecommerce.app.model.user.User;
import com.ecommerce.app.repository.itemCart.ItemCartRepository;
import com.ecommerce.app.repository.product.ProductRepository;
import com.ecommerce.app.repository.user.UserRepository;
import com.ecommerce.app.utils.CurrencyUtils;
import com.ecommerce.app.utils.UserContextUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Optional;
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

    public List<ItemCartDetailsDTO> getAllCartItemsByUser() {

        Optional<Integer> idUser = UserContextUtils.getAuthenticatedUserId();
        if (idUser.isEmpty()) {
            throw new RuntimeException("Usuario não autenticado");
        }
        List<ItemCart> itemCarts = itemCartRepository.findByUserId(idUser.get());
        return itemCarts.stream().map(item ->
                        new ItemCartDetailsDTO(
                                item.getId(),
                                new ProductDTO(
                                        item.getProduct(),
                                        item.getProduct().getImages().get(0).getImagem()),
                                item.getQuantidade(),
                                (item.getProduct().getPreco() * item.getQuantidade()),
                                item.getProduct().getDiscount())
                ).collect(Collectors.toList());
    }

    public ItemCartDTO getCartItemById(Integer id) {
        ItemCart itemCart = itemCartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item de carrinho não encontrado"));
        return convertToDTO(itemCart);
    }

    @Transactional
    public ItemCartDTO addItemToCart(ItemCartDTO itemCartDTO) {

        Optional<Integer> userId = UserContextUtils.getAuthenticatedUserId();
        if(userId.isEmpty()) {
            throw new RuntimeException("Usuario não autenticado");
        }

        ItemCart itemCart = new ItemCart();

        // Buscar e validar o produto
        Product product = productRepository.findById(itemCartDTO.getProductDetailsDTO().getId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        itemCart.setProduct(product);

        itemCart.setQuantidade(itemCartDTO.getQuantidade());

        // Vincular o item ao usuário


        User user = userRepository.findById(userId.get())
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
                        .username(itemCart.getUser().getUsername())
                        .email(itemCart.getUser().getEmail())
                        .roles(itemCart.getUser().getRoles())
                        .build())
                .build();
    }

    private ProductDetailsDTO convertToProductDTO(Product product) {
        return ProductDetailsDTO.builder()
                .id(product.getId())
                .name(product.getNome())
                .price(CurrencyUtils.formatValue(product.getPreco()))
                .category(product.getCategoria())
                .rating(product.getNota())
                .color(product.getCor())
                .stock(product.getEstoque())
                .build();
    }

    @Transactional
    public ItemCartDetailsDTO updateQuantity(Integer id, Integer quantity) {
        ItemCart itemCart = itemCartRepository.getReferenceById(id);
        itemCart.setQuantidade(quantity);
        itemCartRepository.save(itemCart);
        return new ItemCartDetailsDTO(
                itemCart.getId(),
                new ProductDTO(
                        itemCart.getProduct(),
                        itemCart.getProduct().getImages().get(0).getImagem()),
                itemCart.getQuantidade(),
                (itemCart.getProduct().getPreco() * itemCart.getQuantidade()),
                itemCart.getProduct().getDiscount());
    }


//    private Product convertToProductEntity(ProductDetailsDTO productDetailsDTO) {
//        Product product = new Product();
//        product.setId(productDetailsDTO.getId());
//        product.setNome(productDetailsDTO.getName());
//        if (productDetailsDTO.getPrice() != null) {
//            product.setPreco(productDetailsDTO.getPrice());
//        }
//        product.setCategoria(productDetailsDTO.getCategory());
//        product.setNota(productDetailsDTO.getRating());
//        product.setCor(productDetailsDTO.getColor());
//        product.setEstoque(productDetailsDTO.getStock());
//        return product;
//    }
}