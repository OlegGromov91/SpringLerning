package home.localhost.server.resource;


import static java.time.LocalDateTime.now;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

import home.localhost.server.enumiration.Status;
import home.localhost.server.model.Response;
import home.localhost.server.model.Server;
import home.localhost.server.service.implementation.ServerServiceImpl;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerResource {

  private final ServerServiceImpl serverService;

  @GetMapping("/list")
  public ResponseEntity<Response> getServers() {

    return ResponseEntity.ok(
        Response.builder()
            .timeStamp(now())
            .data(Map.of("servers", serverService.list(30)))
            .message("Servers retrived")
            .status(HttpStatus.OK)
            .statusCode(HttpStatus.OK.value())
            .build()
    );
  }


  @GetMapping("/ping/{ipAddress}")
  public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress)
      throws IOException {
    Server server = serverService.ping(ipAddress);
    return ResponseEntity.ok(
        Response.builder()
            .timeStamp(now())
            .data(Map.of("servers", server))
            .message(server.getStatus() == Status.SERVER_UP ? "Ping success" : "Ping failed")
            .status(HttpStatus.OK)
            .statusCode(HttpStatus.OK.value())
            .build()
    );
  }

  @PostMapping("/save")
  public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server) {

    return ResponseEntity.ok(
        Response.builder()
            .timeStamp(now())
            .data(Map.of("servers", serverService.create(server)))
            .message("Server created")
            .status(HttpStatus.CREATED)
            .statusCode(HttpStatus.CREATED.value())
            .build()
    );
  }

  @PutMapping("/update")
  public ResponseEntity<Response> updateServer(@RequestBody @Valid Server server) {
    return ResponseEntity.ok(
        Response.builder()
            .timeStamp(now())
            .data(Map.of("servers", serverService.update(server)))
            .message("Server updated")
            .status(HttpStatus.OK)
            .statusCode(HttpStatus.OK.value())
            .build()
    );
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<Response> getServer(@PathVariable("id") Long id) {

    return ResponseEntity.ok(
        Response.builder()
            .timeStamp(now())
            .data(Map.of("servers", serverService.get(id)))
            .message("Server retrieved")
            .status(HttpStatus.OK)
            .statusCode(HttpStatus.OK.value())
            .build()
    );
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id) {
    return ResponseEntity.ok(
        Response.builder()
            .timeStamp(now())
            .data(Map.of("deleted", serverService.delete(id)))
            .message("Server deleted")
            .status(HttpStatus.OK)
            .statusCode(HttpStatus.OK.value())
            .build()
    );
  }

  @PutMapping("/updateNameByHostName/{id}")
  public ResponseEntity<Response> updateServerNameByHostNameWithOnline(@PathVariable("id") Long id)
      throws IOException {
    return ResponseEntity.ok(
        Response.builder()
            .timeStamp(now())
            .data(Map.of("updatedByHostName", serverService.updateServerNameByHostNameWithOnline(id)))
            .message("Server name updated bu host name")
            .status(HttpStatus.OK)
            .statusCode(HttpStatus.OK.value())
            .build()
    );


  }

  @GetMapping(path = "/serverPick/{fileName}", produces = IMAGE_PNG_VALUE)
  public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException {
    return Files.readAllBytes(Paths.get(
        "C:\\git\\WebLearning\\ServerManager\\Back\\server\\src\\main\\resources\\other\\serverPick\\"
            + fileName));
  }


}
