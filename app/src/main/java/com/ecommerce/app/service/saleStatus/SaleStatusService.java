package com.ecommerce.app.service.saleStatus;

import com.ecommerce.app.dto.saleStatus.SaleStatusDTO;
import com.ecommerce.app.model.saleStatus.SaleStatus;
import com.ecommerce.app.repository.saleStatus.SaleStatusRepository;
import com.ecommerce.app.infra.enums.TypeSaleStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleStatusService {

    private final SaleStatusRepository saleStatusRepository;

    public List<SaleStatusDTO> getAllSalesStatus(){
        return saleStatusRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public SaleStatusDTO getSaleStatusById(Long id){
        Optional<SaleStatus> saleStatus = saleStatusRepository.findById(id);

        return saleStatus.map(this::convertToDTO).orElseThrow(() -> new RuntimeException("Status de venda não encontrado!"));
    }

    public SaleStatusDTO createSaleStatus(SaleStatusDTO salesDTO){
        SaleStatus saleStatus = new SaleStatus();
        saleStatus.setEstado(TypeSaleStatus.PENDENTE);

        saleStatusRepository.save(saleStatus);

        return convertToDTO(saleStatus);
    }

    public SaleStatusDTO updateSaleStatus(Long id, SaleStatusDTO saleStatusDTO) {
        SaleStatus saleStatus = saleStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Status de venda não encontrado!"));

        saleStatus.setEstado(TypeSaleStatus.ENVIANDO);
        saleStatusRepository.save(saleStatus);

        return convertToDTO(saleStatus);
    }

    public void deleteSaleStatus(Long id) {
        SaleStatus saleStatus = saleStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Status de venda não encontrado!"));

        saleStatus.setEstado(TypeSaleStatus.CANCELADO);
        saleStatusRepository.save(saleStatus);

        saleStatusRepository.deleteById(id);
    }

    private SaleStatusDTO convertToDTO(SaleStatus saleStatus){
        SaleStatusDTO saleStatusDTO = new SaleStatusDTO();
        saleStatusDTO.setId(saleStatus.getId());
        saleStatusDTO.setEstado(saleStatus.getEstado().toString());

        return saleStatusDTO;

    }
    
}
