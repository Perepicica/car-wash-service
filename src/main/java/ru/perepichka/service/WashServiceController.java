package ru.perepichka.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.perepichka.service.dto.DiscountUpdateServiceDTO;
import ru.perepichka.service.dto.GetServiceDTO;
import ru.perepichka.service.dto.PostServiceDTO;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/services")
public class WashServiceController {

    private final WashServiceServiceImpl serviceServiceImpl;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<GetServiceDTO> getAllServices(Pageable pageable) {
        return serviceServiceImpl.gelAllServices(pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public GetServiceDTO createService(@RequestBody @Valid PostServiceDTO serviceDTO) {
        return serviceServiceImpl.createService(serviceDTO.getAsService());
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public GetServiceDTO updateServiceDiscount(@PathVariable(name = "id") String id,
                                       @RequestBody @Valid DiscountUpdateServiceDTO discountDTO) {
        return serviceServiceImpl.updateService(id, discountDTO.getDiscount());
    }

}
