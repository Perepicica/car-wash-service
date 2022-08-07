package ru.perepichka.box;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.perepichka.box.dto.GetBoxDTO;
import ru.perepichka.box.dto.PostPutBoxDTO;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/boxes")
public class BoxController {

    private final BoxServiceImpl boxServiceImpl;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<GetBoxDTO> getAllBoxes(Pageable pageable) {
        return boxServiceImpl.getAllBoxes(pageable);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public GetBoxDTO getBoxById(@PathVariable(name = "id") String id) {
        return boxServiceImpl.getBox(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public GetBoxDTO createBox(@RequestBody @Valid PostPutBoxDTO boxDTO) {
        return boxServiceImpl.createBox(boxDTO.getAsBox());
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public GetBoxDTO updateBox(@PathVariable(name = "id") String id,
                               @RequestBody @Valid PostPutBoxDTO boxDTO) {
        return boxServiceImpl.updateBox(id, boxDTO.getAsBox());
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteBox(@PathVariable(name = "id") String id) {
        boxServiceImpl.deleteBox(id);
    }
}
