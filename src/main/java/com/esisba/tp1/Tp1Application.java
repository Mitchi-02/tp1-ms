package com.esisba.tp1;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.esisba.tp1.entity.Configuration;
import com.esisba.tp1.entity.Server;
import com.esisba.tp1.entity.User;
import com.esisba.tp1.entity.Vm;
import com.esisba.tp1.repository.ServerRepository;
import com.esisba.tp1.repository.UserRepository;
import com.esisba.tp1.repository.VmRepository;

@SpringBootApplication
public class Tp1Application implements CommandLineRunner{

	@Autowired
	private UserRepository userRepository;
	@Autowired 
	private ServerRepository serverRepository;
	@Autowired
	private VmRepository vmRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(Tp1Application.class, args);
	}

	private void insert() {
		Configuration config1 = new Configuration("m2", "8", "HDD");
		Configuration config2 = new Configuration("m1", "16", "SSD");
		Configuration config3 = new Configuration("ryzen", "2", "HDD");
		Configuration config4 = new Configuration("intel", "4", "SSD");

		Server s1 = new Server(null, "Server 1", "USA", null, null, config1);
		Server s2 = new Server(null, "Server 2", "FR", null, null, config2);
		serverRepository.save(s1);
		serverRepository.save(s2);
		Collection<Server> servers = serverRepository.findAll();
		servers.forEach(s -> System.out.println(s.getName()));

		User user = new User(null, "ilyas", "i.benhammadi@esi-sba.dz", null);
		userRepository.save(user);

		Vm vm1 = new Vm(null, user, s1, s2, config3);
		Vm vm2 = new Vm(null, user, s2, s1, config4);

		vmRepository.save(vm1);
		vmRepository.save(vm2);
	}

	private void testEmail(String end) {
		Collection<User> users = userRepository.findByEmailEndingWith(end);
		System.out.println("----------------");
		users.forEach(s -> System.out.println(s.getEmail()));
		System.out.println("----------------");
	}

	private void testVmCount(Long id) {
		Long count = userRepository.getVmCount(id);
		System.out.println("----------------");
		System.out.println(count);
		System.out.println("----------------");
	}

	@Override
	public void run(String... args) throws Exception {
		insert();
		testEmail("esi.dz");
		testVmCount(1L);
	}
}
