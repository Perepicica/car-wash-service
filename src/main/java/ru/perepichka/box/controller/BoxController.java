package ru.perepichka.box.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.perepichka.box.Box;
import ru.perepichka.box.BoxServiceImpl;
import ru.perepichka.box.controller.dto.GetBoxDTO;
import ru.perepichka.box.controller.dto.PostPutBoxDTO;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/boxes")
public class BoxController {

    private final BoxServiceImpl boxServiceImpl;

    @GetMapping
    public ResponseEntity<List<GetBoxDTO>> getAllBoxes() {
        List<GetBoxDTO> boxes = boxServiceImpl.getAllBoxes()
                .stream()
                .map(Box::getAsGetBoxDTO)
                .toList();
        if (boxes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(boxes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetBoxDTO> getBoxById(@PathVariable(name = "id") String id) {
        Optional<Box> box = boxServiceImpl.getBox(id);
        if (box.isPresent()) {
            GetBoxDTO boxResponse = box.get().getAsGetBoxDTO();
            return new ResponseEntity<>(boxResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<GetBoxDTO> createBox(@RequestBody @Valid PostPutBoxDTO boxDTO) {
        try {
            Box boxRequest = boxDTO.getAsBox();
            Box box = boxServiceImpl.createBox(boxRequest);
            GetBoxDTO boxResponse = box.getAsGetBoxDTO();
            return new ResponseEntity<>(boxResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetBoxDTO> updateBox(@PathVariable(name = "id") String id,
                                               @RequestBody @Valid PostPutBoxDTO boxDTO) {
        Box boxRequest = boxDTO.getAsBox();
        Box box = boxServiceImpl.updateBox(id, boxRequest);
        GetBoxDTO boxResponse = box.getAsGetBoxDTO();
        return new ResponseEntity<>(boxResponse, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBox(@PathVariable(name = "id") String id) {
        try {
            boxServiceImpl.deleteBox(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
