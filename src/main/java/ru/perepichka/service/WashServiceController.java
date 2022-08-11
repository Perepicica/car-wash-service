package ru.perepichka.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.perepichka.service.dto.DiscountUpdateServiceDto;
import ru.perepichka.service.dto.GetServiceDto;
import ru.perepichka.service.dto.PostServiceDto;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/services")
public class WashServiceController {

    private final WashServiceServiceImpl serviceServiceImpl;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public Page<GetServiceDto> getAllServices(Pageable pageable) {
        return serviceServiceImpl.gelAllServices(pageable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public GetServiceDto createService(@RequestBody @Valid PostServiceDto serviceDTO) {
        return serviceServiceImpl.createService(serviceDTO.getAsService());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public GetServiceDto updateServiceDiscount(@PathVariable(name = "id") String id,
                                               @RequestBody @Valid DiscountUpdateServiceDto discountDTO) {
        return serviceServiceImpl.updateService(id, discountDTO.getDiscount());
    }

}
