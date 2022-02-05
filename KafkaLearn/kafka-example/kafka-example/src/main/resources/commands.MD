	START ZOOKEEPER:

  .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properies
  
	START KAFKA:
	
  .\bin\windows\kafka-server-start.bat .\config\server.properies
  
	START CONSUMER CONSOLE
  
  .\bin\windows\kafka-console-consumer.bat --topic testLocal --from-beginning --bootstrap-server localhost:9092