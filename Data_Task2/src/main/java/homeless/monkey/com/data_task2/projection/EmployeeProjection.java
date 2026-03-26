package homeless.monkey.com.data_task2.projection;

import org.springframework.beans.factory.annotation.Value;

public interface EmployeeProjection {

    String getFirstName();
    String getLastName();
    String getPosition();

    default String getFullName(){
        return getFirstName() + " " + getLastName();
    }

    DepartmentProjection getDepartment();

    default String getDepartmentName(){
        return getDepartment().getName();
    }
}

