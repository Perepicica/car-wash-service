package ru.perepichka.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.perepichka.exception.IdNotFoundException;
import ru.perepichka.service.dto.GetServiceDto;

@RequiredArgsConstructor
@Transactional
@Service
public class WashServiceServiceImpl implements WashServiceService {

    private static final String SERVICE_NOT_FOUND_EXC = "Service not found";

    private final WashServiceRepository washServiceRepository;

    @Override
    public Page<GetServiceDto> gelAllServices(Pageable pageable) {
        return washServiceRepository
                .findAll(pageable)
                .map(WashService::getAsGetServiceDto);
    }

    @Override
    public GetServiceDto createService(WashService service) {
        return washServiceRepository.save(service).getAsGetServiceDto();
    }

    @Override
    public GetServiceDto updateService(String id, Integer newDiscount) {
        return washServiceRepository.findById(id).map(service -> {
            service.setDiscount(newDiscount);
            return washServiceRepository.save(service).getAsGetServiceDto();
        }).orElseThrow(() -> new IdNotFoundException(SERVICE_NOT_FOUND_EXC));
    }
}
