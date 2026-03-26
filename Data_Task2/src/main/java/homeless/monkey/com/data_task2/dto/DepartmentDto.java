package homeless.monkey.com.data_task2.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentDto {
   @NotBlank
   String name;
}
