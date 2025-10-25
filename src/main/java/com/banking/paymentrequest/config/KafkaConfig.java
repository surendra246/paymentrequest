package com.banking.paymentrequest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;


@Configuration
@EnableKafka
public class KafkaConfig {

    // @Bean
    // public ConsumerFactory<String, LoanApplicationKafkaDTO> consumerFactory() {
    //     JsonDeserializer<LoanApplicationKafkaDTO> deserializer =
    //             new JsonDeserializer<>(LoanApplicationKafkaDTO.class, false);
    //     deserializer.addTrustedPackages("*");

    //     Map<String, Object> props = new HashMap<>();
    //     props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    //     props.put(ConsumerConfig.GROUP_ID_CONFIG, "loanapp-group");
    //     props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    //     props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);

    //     return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    // }

    // @Bean
    // public ConcurrentKafkaListenerContainerFactory<String, LoanApplicationKafkaDTO> kafkaListenerContainerFactory() {
    //     ConcurrentKafkaListenerContainerFactory<String, LoanApplicationKafkaDTO> factory =
    //             new ConcurrentKafkaListenerContainerFactory<>();
    //     factory.setConsumerFactory(consumerFactory());
    //     return factory;
    // }
}

