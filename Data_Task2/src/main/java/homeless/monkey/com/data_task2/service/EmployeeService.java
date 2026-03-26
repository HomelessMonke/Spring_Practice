package homeless.monkey.com.data_task2.service;

import homeless.monkey.com.data_task2.dto.EmployeeDto;
import homeless.monkey.com.data_task2.entity.DepartmentEntity;
import homeless.monkey.com.data_task2.entity.EmployeeEntity;
import homeless.monkey.com.data_task2.exception.EmployeeAlreadyExistsException;
import homeless.monkey.com.data_task2.exception.EmployeeNotFoundException;
import homeless.monkey.com.data_task2.mapper.EmployeeMapper;
import homeless.monkey.com.data_task2.projection.EmployeeProjection;
import homeless.monkey.com.data_task2.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;
    private final EmployeeMapper mapper;

    @Transactional
    public EmployeeDto create(EmployeeDto dto) {

        if (employeeRepository.existsByFirstNameAndLastName(dto.getFirstName(), dto.getLastName())) {
            throw new EmployeeAlreadyExistsException(dto.getFirstName(), dto.getLastName());
        }

        DepartmentEntity department = departmentService.getById(dto.getDepartmentId());

        EmployeeEntity employeeEntity = mapper.toEntity(dto);
        employeeEntity.setDepartment(department);

        return mapper.toDto(employeeRepository.save(employeeEntity));
    }

    public List<EmployeeProjection> getAll() {
        return employeeRepository.findAllBy();
    }

    public EmployeeProjection getById(Long id) {
        return employeeRepository.findProjectedById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @Transactional
    public EmployeeDto update(Long id, EmployeeDto dto){
        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        DepartmentEntity newDepartment = departmentService.getById(dto.getDepartmentId());
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setPosition(dto.getPosition());
        employee.setSalary(dto.getSalary());
        employee.setDepartment(newDepartment);

        return mapper.toDto(employeeRepository.save(employee));
    }

    @Transactional
    public void delete(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }
        employeeRepository.deleteById(id);
    }
}
