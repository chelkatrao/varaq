package org.chelkatrao.varaq.service.base;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CrudService<DTO> {
    DTO create(DTO d);

    List<DTO> list();

    ResponseEntity<?> deleteById(Long id);

    ResponseEntity<?> edit(DTO d);
}
