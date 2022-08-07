package ru.perepichka.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.perepichka.service.dto.GetServiceDTO;

public interface WashServiceService {
    Page<GetServiceDTO> gelAllServices(Pageable pageable);

    GetServiceDTO createService(WashService service);

    GetServiceDTO updateService(String id, Integer newDiscount);
}
