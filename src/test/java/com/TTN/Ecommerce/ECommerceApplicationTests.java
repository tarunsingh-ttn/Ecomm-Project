package com.TTN.Ecommerce;

import com.TTN.Ecommerce.Entities.Address;
import com.TTN.Ecommerce.Entities.Customer;
import com.TTN.Ecommerce.Entities.Seller;
import com.TTN.Ecommerce.Entities.User;

import com.TTN.Ecommerce.Repositories.CustomerRepository;
import com.TTN.Ecommerce.Repositories.SellerRepository;
import com.TTN.Ecommerce.Repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
class ECommerceApplicationTests {

	@Autowired
	UserRepository userRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	SellerRepository sellerRepository;

//	@Autowired
//	AddressRepository addressRepository;
	@Test
	void contextLoads() {
	}


    @Test
	void createTest(){

		Customer cust1=new Customer();
		cust1.setContact("1234567890");

		User user1=new User();
		user1.setEmail("tarunsingh021@gmail.com");
		user1.setFirstName("Monkey");
		user1.setMiddleName("D");
		user1.setLastName("luffy");
		cust1.setUser(user1);





//		User user2=new User();
//		Seller newSeller=new Seller();
//		user2.setEmail("seller@420");
//		newSeller.setGst("13");
//		user2.setFirstName("leloch");
//		user2.setMiddleName("d");
//		user2.setLastName("britannia");
//		newSeller.setCompanyName("ttn");
//		newSeller.setCompanyContact("1234567891");

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
//		userRepository.save(newSeller);
		customerRepository.save(cust1);
		userRepository.save(user1);

//		userRepository.save(user2);



	}

	@Test
	void createSellerTest(){

		Seller newSeller=new Seller();

		newSeller.setGst("13");
		newSeller.setCompanyName("ttn");
		newSeller.setCompanyContact("1234567891");

		User user2=new User();
		user2.setEmail("seller@420");
		user2.setFirstName("leloch");
		user2.setMiddleName("d");
		user2.setLastName("britannia");

		newSeller.setUser(user2);

		sellerRepository.save(newSeller);
		userRepository.save(user2);


	}


}
