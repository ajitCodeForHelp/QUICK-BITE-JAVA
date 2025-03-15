package com.quickBite.primary.service;

import com.quickBite.configuration.SpringBeanContext;
import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.dto.AuthDto;
import com.quickBite.primary.dto.CustomerDto;
import com.quickBite.primary.mapper.CustomerMapper;
import com.quickBite.primary.pojo.Customer;
import com.quickBite.primary.pojo.enums.RoleEnum;
import com.quickBite.primary.pojo.enums.VerificationTypeEnum;
import com.quickBite.security.JwtUserDetailsService;
import com.quickBite.utils.TextUtils;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomerService extends _BaseService {

    @Autowired
    private OneTimePasswordService oneTimePasswordService;

    public void validateLoginWithOtp(AuthDto.CustomerOtpLogin login) throws BadRequestException {
        boolean otpVerify = oneTimePasswordService.verifyOtp(login.getMobile(), login.getOtp(), VerificationTypeEnum.Login);
        if (!otpVerify) {
            throw new BadRequestException("Otp Verification Failed");
        }
        Customer customer = customerRepository.findFirstByIsdCodeAndMobile(login.getIsdCode(), login.getMobile());
        if (customer == null) {
            // Register This Customer
            customer = registerCustomer(login.getIsdCode(), login.getMobile());
        }
        if(customer == null){
            throw new BadRequestException("Invalid User Request");
        }
    }

    public void forgotPassword(AuthDto.ForgotPassword passwordDto) throws BadRequestException {
        boolean otpVerify = oneTimePasswordService.verifyOtp(passwordDto.getMobile(), passwordDto.getOtp(), VerificationTypeEnum.ForgotPassword);
        if (!otpVerify) {
            throw new BadRequestException("Password Reset Otp Verification Failed");
        }
        Customer customer = customerRepository.findFirstByIsdCodeAndMobile(passwordDto.getIsdCode(), passwordDto.getMobile());
        if (customer == null) {
            // Register This Customer
            customer = registerCustomer(passwordDto.getIsdCode(), passwordDto.getMobile());
        }
        if(customer == null){
            throw new BadRequestException("Invalid User Request");
        }
        customer.setPwdText(passwordDto.getNewPassword());
        customer.setPwdSecure(TextUtils.getEncodedPassword(customer.getPwdText()));
        customerRepository.save(customer);
    }

    private Customer registerCustomer(String isdCode , String mobile){
        Customer customer = new Customer();
        customer.setIsdCode(isdCode);
        customer.setMobile(mobile);
        customer.setUsername(mobile);
        customer.setUserType(RoleEnum.ROLE_CUSTOMER);
        customer.setUniqueKey(TextUtils.getUniqueKey());
        return customerRepository.save(customer);
    }


    public void updateProfile(CustomerDto.UpdateCustomer updateDto) throws BadRequestException {
        Customer customer = (Customer) SpringBeanContext.getBean(JwtUserDetailsService.class).getLoggedInUser();
        customer = CustomerMapper.MAPPER.mapToPojo(customer, updateDto);
        customerRepository.save(customer);
    }

    public CustomerDto.DetailCustomer get() throws BadRequestException {
        Customer customer = (Customer) SpringBeanContext.getBean(JwtUserDetailsService.class).getLoggedInUser();
        return CustomerMapper.MAPPER.mapToDetailDto(customer);
    }

    private Customer findById(ObjectId id) throws BadRequestException {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            throw new BadRequestException("Customer Not Exist.");
        }
        return customer;
    }


    //############################################### Admin Side Api ################################

    public List<CustomerDto.DetailCustomer> listData(String data) {
        // Data >  Active | Inactive | All
        List<Customer> list = null;
        if (data.equals("Active")) {
            list = customerRepository.findByActive(true);
        } else if (data.equals("Inactive")) {
            list = customerRepository.findByActive(false);
        } else {
            list = customerRepository.findAll();
        }
        return list.stream()
                .map(customer -> CustomerMapper.MAPPER.mapToDetailDto(customer))
                .collect(Collectors.toList());
    }

    public void activate(String id) throws BadRequestException {
        Customer customer = findById(new ObjectId(id));
        customer.setActive(true);
        customer.setModifiedAt(LocalDateTime.now());
        customerRepository.save(customer);
    }

    public void inactivate(String id) throws BadRequestException {
        Customer customer = findById(new ObjectId(id));
        customer.setActive(false);
        customer.setModifiedAt(LocalDateTime.now());
        customerRepository.save(customer);
    }

}
