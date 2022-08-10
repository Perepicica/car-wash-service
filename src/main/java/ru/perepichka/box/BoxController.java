package ru.perepichka.box;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.perepichka.box.dto.GetBoxDto;
import ru.perepichka.box.dto.PostPutBoxDto;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/boxes")
public class BoxController {

    private final BoxServiceImpl boxServiceImpl;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<GetBoxDto> getAllBoxes(Pageable pageable) {
        return boxServiceImpl.getAllBoxes(pageable);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public GetBoxDto getBoxById(@PathVariable(name = "id") String id) {
        return boxServiceImpl.getBox(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public GetBoxDto createBox(@RequestBody @Valid PostPutBoxDto boxDTO) {
        return boxServiceImpl.createBox(boxDTO.getAsBox());
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public GetBoxDto updateBox(@PathVariable(name = "id") String id,
                               @RequestBody @Valid PostPutBoxDto boxDTO) {
        return boxServiceImpl.updateBox(id, boxDTO.getAsBox());
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteBox(@PathVariable(name = "id") String id) {
        boxServiceImpl.deleteBox(id);
    }
}
