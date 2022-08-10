package ru.perepichka.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<GetServiceDto> getAllServices(Pageable pageable) {
        return serviceServiceImpl.gelAllServices(pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public GetServiceDto createService(@RequestBody @Valid PostServiceDto serviceDTO) {
        return serviceServiceImpl.createService(serviceDTO.getAsService());
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public GetServiceDto updateServiceDiscount(@PathVariable(name = "id") String id,
                                               @RequestBody @Valid DiscountUpdateServiceDto discountDTO) {
        return serviceServiceImpl.updateService(id, discountDTO.getDiscount());
    }

}
