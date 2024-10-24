package co.ricardodelgado.spring_boot_reactor_test;

import co.ricardodelgado.spring_boot_reactor_test.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

import java.util.List;

@SpringBootApplication
public class SpringBootReactorTestApplication implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(SpringBootReactorTestApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorTestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		//Create an observable

		//From a List
		Flux<String> fromList = Flux.fromIterable(List.of(args));

		//From an array
		Flux<String> fromString = Flux.fromArray(args);

		//From some data
		Flux<User> names = Flux.just("Richie", "Lilly", "Isa", "Gandalf", "Arya", "Zelda")
				.map(name -> new User(name, null))
				.doOnNext(user -> {
					if(user == null){
						throw new RuntimeException("The name can't be empty");
					}
					System.out.println(user);
				});

		names.subscribe(
				user -> LOG.info(user.name()),
				error -> LOG.error(error.getMessage()),
				() -> LOG.info("Finished with success!")
		);

	}
}
