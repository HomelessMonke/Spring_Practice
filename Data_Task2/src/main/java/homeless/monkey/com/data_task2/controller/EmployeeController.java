package homeless.monkey.com.data_task2.controller;

import homeless.monkey.com.data_task2.dto.EmployeeDto;
import homeless.monkey.com.data_task2.projection.EmployeeProjection;
import homeless.monkey.com.data_task2.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeDto> create(@Valid @RequestBody EmployeeDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeProjection>> getAll() {
        return ResponseEntity.ok(employeeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeProjection> getById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> update(@PathVariable Long id, @Valid @RequestBody EmployeeDto dto) {
        return ResponseEntity.ok(employeeService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
