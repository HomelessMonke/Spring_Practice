package homeless.monkey.com.data_task2.service;

import homeless.monkey.com.data_task2.dto.DepartmentDto;
import homeless.monkey.com.data_task2.entity.DepartmentEntity;
import homeless.monkey.com.data_task2.exception.DepartmentAlreadyExistsException;
import homeless.monkey.com.data_task2.exception.DepartmentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import homeless.monkey.com.data_task2.repository.DepartmentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Transactional
    public Long create(DepartmentDto dto) {
        String departmentName = dto.getName();
        if(departmentRepository.existsByName(departmentName))
            throw new DepartmentAlreadyExistsException(departmentName);

        DepartmentEntity department = new DepartmentEntity();
        department.setName(departmentName);
        return departmentRepository.save(department).getId();
    }

    public String getNameById(Long id) {
        return getById(id).getName();
    }

    public List<String> getAll() {
        return departmentRepository.findAll()
                .stream()
                .map(DepartmentEntity::getName)
                .toList();
    }

    @Transactional
    public void update(Long id, DepartmentDto dto) {
        DepartmentEntity department = getById(id);
        department.setName(dto.getName());
        departmentRepository.save(department);
    }

    @Transactional
    public void delete(Long id) {
        DepartmentEntity department = getById(id);
        departmentRepository.delete(department);
    }

    public DepartmentEntity getById(Long id){
        return departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));
    }
}
