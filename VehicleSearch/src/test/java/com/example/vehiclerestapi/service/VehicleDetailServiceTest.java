package com.example.vehiclerestapi.service;

import com.example.vehiclerestapi.dao.VehicleMarketPriceDAO;
import com.example.vehiclerestapi.dto.VehicleDetails;
import com.example.vehiclerestapi.dto.VehicleDetailsDTO;
import com.example.vehiclerestapi.entity.VehicleMarketPrice;
import com.example.vehiclerestapi.service.impl.VehicleDetailServiceImpl;
import com.example.vehiclerestapi.dto.ClientVehicleDetail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VehicleDetailServiceTest {
    VehicleDetails vehicleDetails;
    VehicleDetailsDTO vehicleDetailsDTO;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private VehicleMarketPriceService vehicleMarketPriceService;

    @Mock
    private VehicleMarketPriceDAO vehicleMarketPriceDAO;


    @InjectMocks
    private VehicleDetailServiceImpl vehicleDetailService;

    @Test
    public void VehicleDetailsService_getAllClientVehicleDetails_ReturnClientVehicleDetailsIsNotNullandSizeOfClientVehicleDetail() {
        List<VehicleDetails> listOfVehicleDetails = List.of(
                new VehicleDetails(1, 2019, "Volswagen CC", "Arteon", "SEL", "Liftback", 25559, 25000, 2.5, "Frankfurt, Germany", "Paul", "(+49)163 555 1584"),
                new VehicleDetails(2, 2020, "Volswagen CC", "Arteon", "SEL", "Hatchback", 30000, 26354, 2.7, "Rostock, Germany", "Leo", "(+49)163 666 1584"));
        VehicleDetailsDTO expectedVehicleDetailsDTO = new VehicleDetailsDTO(listOfVehicleDetails);

        when(restTemplate.getForObject(anyString(), Mockito.eq(VehicleDetailsDTO.class)))
                .thenReturn(expectedVehicleDetailsDTO);
        when(vehicleMarketPriceService.getVehicleMarketPriceByBrandAndModelAndTrimAndYear(any(), any(), any(), anyInt()))
                .thenReturn(new VehicleMarketPrice(1, 2019, "Volswagen CC", "Arteon", "SEL", 37000));

        List<ClientVehicleDetail> result = vehicleDetailService.getAllClientVehicleDetails();

        verify(restTemplate, times(1)).getForObject(anyString(), eq(VehicleDetailsDTO.class));
        verify(vehicleMarketPriceService, times(1)).getVehicleMarketPriceByBrandAndModelAndTrimAndYear(any(), any(), any(), anyInt());

        assertEquals(1, result.size());
    }

}
