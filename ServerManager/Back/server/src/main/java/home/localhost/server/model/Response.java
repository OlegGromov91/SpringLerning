package home.localhost.server.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;


@Data
@SuperBuilder
@JsonInclude(Include.NON_NULL)
public class Response {

  protected LocalDateTime timeStamp;
  protected int statusCode;
  protected HttpStatus status;
  protected String reason;
  protected String message;
  protected String developerMessage;
  protected Map<?, ?> data;


}
