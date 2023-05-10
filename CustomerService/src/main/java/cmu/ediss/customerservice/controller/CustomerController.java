package cmu.ediss.customerservice.controller;


import cmu.ediss.customerservice.entity.Customer;
import cmu.ediss.customerservice.service.CustomerEventProducer;
import cmu.ediss.customerservice.service.CustomerService;
import cmu.ediss.customerservice.untils.CommonResponse;
import cmu.ediss.customerservice.untils.Validation;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

/**
 * @author Wenyu Chen
 * @version 1.0.0
 * @ClassName CustomerController.java
 * @andrewID wenyuc2
 * @Description TODO
 */
@Slf4j
@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private CustomerEventProducer customerEventProducer;

//    public CustomerController(CustomerService customerService) {
//        this.customerService=customerService;
//        customerService.deleteAll();
//    }

    @GetMapping({"/{id}"})
    public ResponseEntity<?> getCustomer(@PathVariable int id) {
        log.info("getById:  "+id);
        Customer result = customerService.getByUId(id);
        return result == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> retrieveCustomerByUserId(@RequestParam @NotNull @Email String userId) {
        userId = userId.replace("%40", "@");
        if (!Validation.isValidEmail(userId)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info(userId);
        Customer customer = customerService.getByUserId(userId);
        return customer == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createCustomer(@RequestBody @Valid Customer customer, BindingResult bindingResult) {

        if (bindingResult.hasErrors() || !Validation.isStateValid(customer.getState())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        customer.setUserId( customer.getUserId().replace("%40", "@"));
        if (customerService.getByUserId(customer.getUserId()) != null) {
            return new ResponseEntity<>(new CommonResponse("This user ID already exists in the system."), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        log.info(String.valueOf(customer));
        boolean result = customerService.createCustomer(customer);

        customerEventProducer.sendCustomerRegisteredEvent(customer);

        String baseUrl = String.format("%s://%s:%d", request.getScheme(), request.getServerName(), request.getServerPort());
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(URI.create(baseUrl + "/customers/" + customer.getId()));
        return result ? new ResponseEntity<>(customer,HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<?> validationError(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ TooManyResultsException.class})
    public ResponseEntity<?> getOneError(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }


}
