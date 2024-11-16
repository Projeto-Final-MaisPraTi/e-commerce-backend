package com.ecommerce.app.service.sales;

import com.ecommerce.app.dto.sales.SalesDTO;
import com.ecommerce.app.dto.salesItems.SalesItemsDTO;
import com.ecommerce.app.infra.enums.TypeSaleStatus;
import com.ecommerce.app.model.coupons.Coupons;
import com.ecommerce.app.model.payment.Payment;
import com.ecommerce.app.model.sales.Sales;
import com.ecommerce.app.model.salesItems.SalesItems;
import com.ecommerce.app.model.user.User;
import com.ecommerce.app.repository.sales.SalesRepository;
import com.ecommerce.app.service.coupons.CouponsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesService {

    private final SalesRepository salesRepository;

    public List<SalesDTO> getAllSales(){
        return salesRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public SalesDTO getSaleById(Integer id){
        Optional<Sales> sale = salesRepository.findById(id);
        return sale.map(this::convertToDTO).orElseThrow(() -> new RuntimeException("Venda não encontrada!"));
    }

    public SalesDTO createSale(SalesDTO salesDTO, User user, Payment payment, Coupons coupon, SalesItemsDTO salesItemsDTO){
        Sales sales = new Sales();
        sales.setTotal(salesDTO.getTotal());
        sales.setTypeSaleStatus(TypeSaleStatus.PENDENTE);
        sales.setUser(user);
        sales.setPayment(payment);

        // Associa o cupom recebido à venda, sem verificação adicional
        sales.setCoupons(coupon);
        sales.setSalesItems((List<SalesItems>) salesItemsDTO);

        salesRepository.save(sales);
        return convertToDTO(sales);
    }

    public SalesDTO updateSale(Integer id, SalesDTO salesDTO, User user, Payment payment, Coupons coupon, SalesItemsDTO salesItemsDTO) {
        Sales sales = salesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada!"));

        sales.setTotal(salesDTO.getTotal());
        sales.setTypeSaleStatus(TypeSaleStatus.ENVIANDO);
        sales.setUser(user);
        sales.setPayment(payment);

        // Associa o cupom recebido à venda, sem verificação adicional
        sales.setCoupons(coupon);
        sales.setSalesItems((List<SalesItems>) salesItemsDTO);

        salesRepository.save(sales);
        return convertToDTO(sales);
    }

    public void deleteSale(Integer id) {
        Sales sales = salesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada!"));

        sales.setTypeSaleStatus(TypeSaleStatus.CANCELADO);
        salesRepository.save(sales);
        salesRepository.deleteById(id);
    }

    private SalesDTO convertToDTO(Sales sales){
        SalesDTO salesDTO = new SalesDTO();
        salesDTO.setId(sales.getId());
        salesDTO.setTotal(sales.getTotal());
        salesDTO.setTypeSaleStatus(sales.getTypeSaleStatus());

        return salesDTO;
    }

    public List<SalesDTO> getSalesByStatus(TypeSaleStatus status) {
        return salesRepository
                .findByTypeSaleStatus(status)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void updatedSaleStatus(Integer id, TypeSaleStatus typeSaleStatus) {
        salesRepository
                .findById(id)
                .map(sale -> {
                    sale.setTypeSaleStatus(typeSaleStatus);
                    return salesRepository.save(sale);
                }).orElseThrow(
                        () -> new RuntimeException("Venda não encontrada!")
                );
    }
}