package ru.perepichka.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.perepichka.service.dto.GetServiceDto;

public interface WashServiceService {
    Page<GetServiceDto> gelAllServices(Pageable pageable);

    GetServiceDto createService(WashService service);

    GetServiceDto updateService(String id, Integer newDiscount);
}
