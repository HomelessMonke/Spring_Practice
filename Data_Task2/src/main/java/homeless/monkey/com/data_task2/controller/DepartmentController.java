package homeless.monkey.com.data_task2.controller;

import homeless.monkey.com.data_task2.dto.DepartmentDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import homeless.monkey.com.data_task2.service.DepartmentService;

import java.util.List;

@RestController
@RequestMapping("api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<Long> create(@Valid @RequestBody DepartmentDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<String>> getAll() {
        return ResponseEntity.ok(departmentService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getById(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getNameById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody DepartmentDto dto) {
        departmentService.update(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        departmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
