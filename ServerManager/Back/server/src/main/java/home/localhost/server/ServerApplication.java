package home.localhost.server;

import home.localhost.server.ServerRepo.ServerRepo;
import home.localhost.server.enumiration.Status;
import home.localhost.server.model.Server;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServerApplication.class, args);
  }


  @Bean
  CommandLineRunner runner(ServerRepo serverRepo) {
    return args ->
    {
      serverRepo.save(new Server(null, "192.168.1.2", "Windows", "162 GB", "You PC",
          "http://localhost:8080/server/other/serverPick/2.png",
          Status.SERVER_UP));
      serverRepo.save(new Server(null, "192.168.1.1", "Ubuntu", "16 GB", "Personal PC",
          "http://localhost:8080/server/other/serverPick/1.png",
          Status.SERVER_UP));
      serverRepo.save(new Server(null, "192.168.1.3", "Pie", "32 GB", "Nanobox",
          "http://localhost:8080/server/other/serverPick/3.png",
          Status.SERVER_UP));

			serverRepo.save(new Server(null, "192.168.1.4", "Selenium", "10 GB", "DELLTinyClient",
					"http://localhost:8080/server/other/serverPick/4.png",
					Status.SERVER_UP));
    };
  }

}
