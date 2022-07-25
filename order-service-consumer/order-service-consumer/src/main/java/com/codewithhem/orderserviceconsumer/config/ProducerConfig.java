package com.codewithhem.orderserviceconsumer.config;

import com.codewithhem.orderserviceconsumer.model.Order;
import com.codewithhem.orderserviceconsumer.model.User;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProducerConfig {
    @Bean
    public ProducerFactory<String, Order> producerOrderFactory(){
        return new DefaultKafkaProducerFactory<>(createConfig());
    }


    @Bean
    public KafkaTemplate<String, Order> kafkaOrderTemplate(){
        return new KafkaTemplate<>(producerOrderFactory());
    }

    @Bean
    public ProducerFactory<String, User> producerUserFactory(){
        return new DefaultKafkaProducerFactory<>(createConfig());
    }


    @Bean
    public KafkaTemplate<String, User> kafkaUserTemplate(){
        return new KafkaTemplate<>(producerUserFactory());
    }

    @Bean
    public Map<String, Object> createConfig(){
        Map<String, Object> config = new HashMap<>();
        config.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return config;
    }
}

