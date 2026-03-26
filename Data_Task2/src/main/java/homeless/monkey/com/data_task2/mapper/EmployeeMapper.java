package homeless.monkey.com.data_task2.mapper;

import homeless.monkey.com.data_task2.dto.EmployeeDto;
import homeless.monkey.com.data_task2.entity.EmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "department", ignore = true)
    EmployeeEntity toEntity(EmployeeDto dto);

    @Mapping(target = "departmentId", source = "department.id")
    EmployeeDto toDto(EmployeeEntity entity);
}
