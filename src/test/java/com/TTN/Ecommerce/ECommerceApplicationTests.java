package com.TTN.Ecommerce;

import com.TTN.Ecommerce.Entities.Address;
import com.TTN.Ecommerce.Entities.Customer;
import com.TTN.Ecommerce.Entities.Seller;
import com.TTN.Ecommerce.Entities.User;

import com.TTN.Ecommerce.Repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
class ECommerceApplicationTests {

	@Autowired
	UserRepository userRepository;

//	@Autowired
//	AddressRepository addressRepository;
	@Test
	void contextLoads() {
	}


    @Test
	void createTest(){
		Customer newCustomer=new Customer();
		newCustomer.setEmail("tarunsingh021@gmail.com");
		newCustomer.setFirstName("Monkey");
		newCustomer.setMiddleName("D");
		newCustomer.setLastName("luffy");
		newCustomer.setContact("1234567890");

		Seller newSeller=new Seller();
		newSeller.setEmail("seller@420");
		newSeller.setGst("13");
		newSeller.setFirstName("leloch");
		newSeller.setMiddleName("d");
		newSeller.setLastName("britannia");
		newSeller.setCompanyName("ttn");
		newSeller.setCompanyContact("1234567891");

//		Address sellerAddress=new Address();
//		sellerAddress.setAddress_id(1);
//		sellerAddress.setAddressLine("greater noida");
//		sellerAddress.setLabel("home");
//		sellerAddress.setCity("noida");
//		sellerAddress.setCountry("india");
//		sellerAddress.setZipCode(201306);
//
//		newSeller.setAddress(sellerAddress);

//		addressRepository.save(sellerAddress);
		userRepository.save(newSeller);
		userRepository.save(newCustomer);



	}

}
