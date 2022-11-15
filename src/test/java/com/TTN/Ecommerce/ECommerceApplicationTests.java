package com.TTN.Ecommerce;

import com.TTN.Ecommerce.Entities.*;

import com.TTN.Ecommerce.Repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class ECommerceApplicationTests {

	@Autowired
	UserRepository userRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	SellerRepository sellerRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	AddressRepository addressRepository;
	@Test
	void contextLoads() {
	}


    @Test
	void createTest(){

		Customer cust1=new Customer();
		cust1.setContact("1234567890");

		User user1=new User();
		user1.setEmail("tarun");
		user1.setFirstName("Monkey");
		user1.setMiddleName("D");
		user1.setLastName("luffy");
		user1.setPassword("1234");
		Role role = roleRepository.findById(3).get();
		user1.setRole(role);
		cust1.setUser(user1);

		customerRepository.save(cust1);

		Set<Address> addresses= new HashSet<>();
		Address homeAddress= new Address();
		homeAddress.setAddress_id(156);
		homeAddress.setAddressLine("laxmi nagar");
		homeAddress.setCountry("India");
		homeAddress.setZipCode(201306);
		homeAddress.setState("home sate");
		homeAddress.setCity("gaur city");
		homeAddress.setCustomer(cust1);
		homeAddress.setLabel("home");
		addresses.add(homeAddress);
		addressRepository.save(homeAddress);




		Address officeAddress=new Address();
		officeAddress.setCity("office city");
		officeAddress.setCountry("dubai");
		officeAddress.setLabel("office");
		officeAddress.setZipCode(110086);
		officeAddress.setState("habibi");
		officeAddress.setAddress_id(157);
		officeAddress.setAddressLine("hhsh");
		officeAddress.setCustomer(cust1);
		addressRepository.save(officeAddress);

		addresses.add(officeAddress);


		cust1.setAddresses(addresses);








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
		user2.setPassword("4321");
		user2.setEmail("seller");
		user2.setFirstName("leloch");
		user2.setMiddleName("d");
		user2.setLastName("britannia");

		newSeller.setUser(user2);


		Address sellerAddress=new Address();
		sellerAddress.setAddress_id(1);
		sellerAddress.setAddressLine("greater noida");
		sellerAddress.setLabel("home");
		sellerAddress.setCity("noida");
		sellerAddress.setCountry("india");
		sellerAddress.setZipCode(201306);
		sellerAddress.setSeller(newSeller);

		addressRepository.save(sellerAddress);
		sellerRepository.save(newSeller);
		userRepository.save(user2);



	}

	@Test
	void createSellerAddress(){


	}


}
