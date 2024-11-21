package com.ecommerce.app.service.salesItems;

import com.ecommerce.app.dto.itemCart.ItemCartDetailsDTO;
import com.ecommerce.app.dto.product.ProductDetailsDTO;
import com.ecommerce.app.dto.sales.SaleDTO;
import com.ecommerce.app.dto.sales.SalesDTO;
import com.ecommerce.app.dto.salesItems.SalesItemsDTO;
import com.ecommerce.app.model.product.Product;
import com.ecommerce.app.model.sales.Sales;
import com.ecommerce.app.model.salesItems.SalesItems;
import com.ecommerce.app.repository.itemCart.ItemCartRepository;
import com.ecommerce.app.repository.product.ProductRepository;
import com.ecommerce.app.repository.sales.SalesRepository;
import com.ecommerce.app.repository.salesItems.SalesItemsRepository;
import com.ecommerce.app.utils.CurrencyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesItemsService {

    private final SalesItemsRepository salesItemsRepository;

    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ItemCartRepository itemCartRepository;

    public List<SalesItemsDTO> getAllSalesItems(){
        return salesItemsRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public SalesItemsDTO getSalesItemById(Integer id){
        Optional<SalesItems> saleItem = salesItemsRepository.findById(id);
        return saleItem.map(this::convertToDTO).orElseThrow(() -> new RuntimeException("Item de venda não encontrado!"));
    }

    public Page<Object[]> getBestSallers(Pageable pageable) {
        Page<Object[]> bestSallers = salesItemsRepository.getBestSallers(pageable);
        if (bestSallers.isEmpty()) {
            return null;
        }
        return bestSallers;
    }

    public List<SalesItemsDTO> createSalesItem(SaleDTO sales){

        Sales sale = salesRepository.findById(sales.getIdSale()).get();
        List<SalesItems> salesItemsList = sales.getItemsCart().stream().map(item-> {
            SalesItems salesItems = new SalesItems();
            salesItems.setSales(sale); // Associar venda
            Product product = productRepository.getReferenceById(item.getProductDTO().id());
            salesItems.setProduct(product);
            salesItems.setQuantidade(item.getQuantity());
            salesItems.setPreco(item.getPreco());
            return salesItems;
        }).collect(Collectors.toList());

        salesItemsRepository.saveAll(salesItemsList);
        return salesItemsList.stream().map(item -> convertToDTO(item)).toList();
    }

    @Transactional
    public void deleteItensInCart(List<ItemCartDetailsDTO> salesItems) {
        List<Integer> ids = salesItems.stream()
                .map(ItemCartDetailsDTO::getId)
                .collect(Collectors.toList());
        itemCartRepository.deleteAllByIdIn(ids);
    }

    public SalesItemsDTO updateSalesItem(Integer id, SalesItemsDTO salesItemsDTO, Sales sales, Product product) {
        SalesItems salesItems = salesItemsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item de venda não encontrado!"));
        salesItems.setSales(sales); // Associar venda
        salesItems.setProduct(product);
        salesItems.setQuantidade(salesItemsDTO.getQuantidade());
        salesItems.setPreco(salesItemsDTO.getPreco());
        salesItemsRepository.save(salesItems);
        return convertToDTO(salesItems);
    }

    public void deleteSalesItem(Integer id) {
        SalesItems salesItems = salesItemsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item de venda não encontrado!"));
        salesItemsRepository.deleteById(id);
    }

    public List<SalesItemsDTO> getUserPurchaseHistory(Integer userId) {
        List<SalesItems> salesItemsList = salesItemsRepository.findByUserId(userId);
        return salesItemsList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private SalesItemsDTO convertToDTO(SalesItems salesItems) {
        SalesItemsDTO salesItemsDTO = new SalesItemsDTO();
        salesItemsDTO.setId(salesItems.getId());
        salesItemsDTO.setProductDetailsDTO(convertToProductDTO(salesItems.getProduct()));
        salesItemsDTO.setQuantidade(salesItems.getQuantidade());
        salesItemsDTO.setPreco(salesItems.getPreco());
        return salesItemsDTO;
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
}