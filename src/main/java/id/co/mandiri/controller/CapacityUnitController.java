package id.co.mandiri.controller;

import com.maryanto.dimas.plugins.web.commons.ui.datatables.DataTablesRequest;
import com.maryanto.dimas.plugins.web.commons.ui.datatables.DataTablesResponse;

import id.co.mandiri.dto.CapacityUnitDTO;
import id.co.mandiri.dto.CapacityUnitMapperRequestNew;
import id.co.mandiri.dto.CapacityUnitMapperRequestUpdate;
import id.co.mandiri.entity.CapacityUnit;
import id.co.mandiri.service.CapacityUnitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/master/capacity-unit")
public class CapacityUnitController {

    @Autowired
    private CapacityUnitService service;

    @PostMapping("/datatables")
    public DataTablesResponse<CapacityUnit> datatables(
            @RequestParam(required = false, value = "draw", defaultValue = "0") Long draw,
            @RequestParam(required = false, value = "start", defaultValue = "0") Long start,
            @RequestParam(required = false, value = "length", defaultValue = "10") Long length,
            @RequestParam(required = false, value = "order[0][column]", defaultValue = "0") Long iSortCol0,
            @RequestParam(required = false, value = "order[0][dir]", defaultValue = "asc") String sSortDir0,
            @RequestBody(required = false) CapacityUnit params) {

        if (params == null) params = new CapacityUnit();
        log.info("draw: {}, start: {}, length: {}, type: {}", draw, start, length, params);
        return service.datatables(
                new DataTablesRequest(draw, length, start, sSortDir0, iSortCol0, params)
        );
    }

    @GetMapping("/list")
    public ResponseEntity<List<CapacityUnit>> list() {
        List<CapacityUnit> list = service.findAll();
        if (list.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CapacityUnit> findById(@PathVariable("id") String id) {
        CapacityUnit params = service.findId(id);
        if (params != null) {
            return new ResponseEntity<>(params, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new CapacityUnit(), HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/")
    public ResponseEntity<CapacityUnit> save(@Valid @RequestBody CapacityUnitDTO.CapacityUnitRequestNewDTO params) {
        CapacityUnit value = CapacityUnitMapperRequestNew.converter.convertToEntity(params);
        value = service.save(value);
        return new ResponseEntity<>(value, HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<CapacityUnit> update(@Valid @RequestBody CapacityUnitDTO.CapacityUnitRequestUpdateDTO params) {
        CapacityUnit value = CapacityUnitMapperRequestUpdate.converter.convertToEntity(params);
        value = service.save(value);
        return new ResponseEntity<>(value, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CapacityUnit> delete(@PathVariable("id") String id) {
        boolean deleted = service.removeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
