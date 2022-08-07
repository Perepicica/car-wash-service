package ru.perepichka.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.perepichka.exception.IdNotFoundException;
import ru.perepichka.exception.NoDataInDatabaseException;
import ru.perepichka.service.dto.GetServiceDTO;

@RequiredArgsConstructor
@Transactional
@Service
public class WashServiceServiceImpl implements WashServiceService {

    private final WashServiceRepository washServiceRepository;

    @Override
    public Page<GetServiceDTO> gelAllServices(Pageable pageable) {
        Page<GetServiceDTO> services = washServiceRepository
                .findAll(pageable)
                .map(WashService::getAsGetServiceDTO);

        if (services.isEmpty()) {
            throw new NoDataInDatabaseException();
        }
        return services;
    }

    @Override
    public GetServiceDTO createService(WashService service) {
        return washServiceRepository.save(service).getAsGetServiceDTO();
    }

    @Override
    public GetServiceDTO updateService(String id, Integer newDiscount) {
        return washServiceRepository.findById(id).map(service -> {
            service.setDiscount(newDiscount);
            return washServiceRepository.save(service).getAsGetServiceDTO();
        }).orElseThrow(() -> new IdNotFoundException("Service not found, id: " + id));
    }
}
