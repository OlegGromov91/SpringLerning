package home.localhost.server.ServerRepo;

import home.localhost.server.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepo extends JpaRepository<Server, Long> {

  Server findServerByIpAddress(String ipAddress);


}
