package com.ecommerce.app.service.sales;

import com.ecommerce.app.dto.sales.SalesDTO;
import com.ecommerce.app.model.sales.Sales;
import com.ecommerce.app.repository.saleStatus.SaleStatusRepository;
import com.ecommerce.app.repository.sales.SalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesService {

    private final SalesRepository salesRepository;
    private final SaleStatusRepository saleStatusRepository;

    public List<SalesDTO> getAllSales(){
        return salesRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public SalesDTO getSaleById(Long id){
        Optional<Sales> sale = salesRepository.findById(id);

        return sale.map(this::convertToDTO).orElseThrow(() -> new RuntimeException("Venda não encontrada!"));
    }

    public SalesDTO createSale(SalesDTO salesDTO){
        Sales sales = new Sales();
        sales.setTotal(salesDTO.getTotal());

        salesRepository.save(sales);

        return convertToDTO(sales);
    }

    public SalesDTO updateSale(Long id, SalesDTO salesDTO) {
        Sales sales = salesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada!"));

        sales.setTotal(salesDTO.getTotal());
        salesRepository.save(sales);

        return convertToDTO(sales);
    }

    public void deleteSale(Long id) {
        Sales sales = salesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada!"));

        salesRepository.deleteById(id);
    }

    private SalesDTO convertToDTO(Sales sales){
        SalesDTO salesDTO = new SalesDTO();
        salesDTO.setId(sales.getId());
        salesDTO.setTotal(sales.getTotal());

        return salesDTO;

    }

}
