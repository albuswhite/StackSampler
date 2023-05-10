package cmu.ediss.customerservice.service;




import cmu.ediss.customerservice.entity.Customer;
import cmu.ediss.customerservice.mapper.TCustomerMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Wenyu Chen
 * @version 1.0.0
 * @ClassName CustomerService.java
 * @andrewID wenyuc2
 * @Description TODO
 */
@Service
public class CustomerService extends ServiceImpl<TCustomerMapper, Customer> {


    public boolean createCustomer(Customer customer) {
        return saveOrUpdate(customer);
    }

    public Customer getByUserId(String userId) {
        LambdaQueryWrapper<Customer> wrapper =
                new LambdaQueryWrapper<Customer>().eq(Customer::getUserId, userId);
        return getOne(wrapper);
    }

    public Customer getByUId(int Id) {
        LambdaQueryWrapper<Customer> wrapper =
                new LambdaQueryWrapper<Customer>().eq(Customer::getId, Id);
        return getOne(wrapper);
    }

    public void deleteAll() {
        LambdaQueryWrapper<Customer> wrapper =
                new LambdaQueryWrapper<Customer>();
        remove(wrapper);
    }


}
