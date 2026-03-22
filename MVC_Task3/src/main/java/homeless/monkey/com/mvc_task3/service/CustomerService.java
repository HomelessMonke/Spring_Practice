package homeless.monkey.com.mvc_task3.service;

import homeless.monkey.com.mvc_task3.entity.Customer;
import homeless.monkey.com.mvc_task3.exception.CustomerNotFoundException;
import homeless.monkey.com.mvc_task3.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomer(Long id){
        return customerRepository.findById(id)
                .orElseThrow(()-> new CustomerNotFoundException("Customer not found with id: " + id));
    }
}
