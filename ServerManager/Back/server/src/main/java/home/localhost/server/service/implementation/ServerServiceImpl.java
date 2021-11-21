package home.localhost.server.service.implementation;

import static home.localhost.server.enumiration.Status.SERVER_UP;

import home.localhost.server.ServerRepo.ServerRepo;
import home.localhost.server.enumiration.Status;
import home.localhost.server.model.Server;
import home.localhost.server.service.ServerService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;


import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {

  private final ServerRepo serverRepo;


  @Override
  public Server create(Server server) {
    log.info("Saving new server: {}", server.getName());
    server.setImageUrl(setServerImageURL());
    return serverRepo.save(server);
  }

  @Override
  public Server ping(String ipAddress) throws IOException {
    log.info("Pinging server IP: {}", ipAddress);
    Server server = serverRepo.findServerByIpAddress(ipAddress);
    InetAddress address = InetAddress.getByName(ipAddress);
    server.setStatus(address.isReachable(10000) ? SERVER_UP : Status.SERVER_DOWN);
    serverRepo.save(server);
    return server;
  }

  @Override
  public Collection<Server> list(int limit) {
    log.info("Fetching all servers");
    return serverRepo.findAll(PageRequest.of(0, limit)).toList();
  }

  @Override
  public Server get(Long id) {
    log.info("Fetching server by id: {}", id);
    return serverRepo.findById(id).get();
  }

  @Override
  public Server update(Server server) {
    log.info("Updating server: {}", server);
    return serverRepo.save(server);
  }

  @Override
  public Boolean delete(Long id) {
    log.info("Deleting server by ID: {}", id);
    serverRepo.deleteById(id);

    return Boolean.TRUE;
  }


  public Server updateServerNameByHostNameWithOnline(Long id) throws IOException {
    Server serverFromRepo = serverRepo.findById(id).orElseThrow();
    InetAddress address = InetAddress.getByName(serverFromRepo.getIpAddress());
    if (address.isReachable(10000)) {
      serverFromRepo.setName(InetAddress.getLocalHost().getHostName());
      serverFromRepo.setStatus(SERVER_UP);
      return serverFromRepo;
    }
    throw new Error("Server is not reachable");
  }


  private String setServerImageURL() {

    String[] imageNames = {"1.png", "2.png", "3.png", "4.png",};
    return ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("/other/serverPick/" + imageNames[new Random().nextInt(4)]).toUriString();
  }
}
