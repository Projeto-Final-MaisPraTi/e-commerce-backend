package com.ecommerce.app.service.salesItems;

import com.ecommerce.app.dto.product.ProductDTO;
import com.ecommerce.app.dto.salesItems.SalesItemsDTO;
import com.ecommerce.app.model.product.Product;
import com.ecommerce.app.model.salesItems.SalesItems;
import com.ecommerce.app.repository.salesItems.SalesItemsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesItemsService {

    private final SalesItemsRepository salesItemsRepository;

    public List<SalesItemsDTO> getAllSalesItems(){
        return salesItemsRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public SalesItemsDTO getSalesItemById(Long id){
        Optional<SalesItems> saleItem = salesItemsRepository.findById(id);

        return saleItem.map(this::convertToDTO).orElseThrow(() -> new RuntimeException("Item de venda não encontrado!"));
    }

    public SalesItemsDTO createSalesItem(SalesItemsDTO salesItemsDTO, Product product){
        SalesItems salesItems = new SalesItems();
        salesItems.setProduct(product);
        salesItems.setQuantidade(salesItemsDTO.getQuantidade());
        salesItems.setPreco(salesItemsDTO.getPreco());

        salesItemsRepository.save(salesItems);

        return convertToDTO(salesItems);
    }

    public SalesItemsDTO updateSalesItem(Long id, SalesItemsDTO salesItemsDTO, Product product) {
        SalesItems salesItems = salesItemsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item de venda não encontrado!"));

        salesItems.setProduct(product);
        salesItems.setQuantidade(salesItemsDTO.getQuantidade());
        salesItems.setPreco(salesItemsDTO.getPreco());

        salesItemsRepository.save(salesItems);

        return convertToDTO(salesItems);
    }

    public void deleteSalesItem(Long id) {
        SalesItems salesItems = salesItemsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item de venda não encontrado!"));

        salesItemsRepository.deleteById(id);
    }

    private SalesItemsDTO convertToDTO(SalesItems salesItems){
        SalesItemsDTO salesItemsDTO = new SalesItemsDTO();
        salesItems.setQuantidade(salesItemsDTO.getQuantidade());
        salesItems.setPreco(salesItemsDTO.getPreco());

        return salesItemsDTO;

    }

}
