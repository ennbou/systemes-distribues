# Systèmes Distribués, Stream et batch processing

## Microservices

1.  [Customer](https://github.com/ennbou/microservices/tree/main/customer-service)
2.  [Inventory](https://github.com/ennbou/microservices/tree/main/inventory-service)
3.  [Billing](https://github.com/ennbou/microservices/tree/main/billing-service)
4.  [Gateway](https://github.com/ennbou/microservices/tree/main/gateway-service)
5.  [Discoery(Eureka)](https://github.com/ennbou/microservices/tree/main/discovery-service)

## Frontend (React + Material-UI & Keycloak Adapter)

## Kafka

1. [Facturation Producer](https://github.com/ennbou/systemes-distribues/tree/main/factures-kafka)
2. [Facturation Consumer](https://github.com/ennbou/systemes-distribues/tree/main/factures-consumer)
3. [Facturation Stream Processing](https://github.com/ennbou/systemes-distribues/tree/main/kafka-stream-processing)

consumer for topic `amountclient` (Le Total des factures reçus pour les 5 dernières secondes)
```
cd kafka_2.13-2.7.0 && .\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic amountclient --property print.key=true --property key.separator=" : " --key-deserializer "org.apache.kafka.common.serialization.StringDeserializer" --value-deserializer "org.apache.kafka.common.serialization.DoubleDeserializer"
```

consumer for topic `amountclient` (Le total des factures de chaque client  pour 5 dernières secondes)
```
cd kafka_2.13-2.7.0 && .\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic amount5s  --property print.key=true --property key.separator=" : " --key-deserializer "org.apache.kafka.common.serialization.StringDeserializer" --value-deserializer "org.apache.kafka.common.serialization.DoubleDeserializer"
```