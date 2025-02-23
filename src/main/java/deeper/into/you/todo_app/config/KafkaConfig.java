package deeper.into.you.todo_app.config;

import deeper.into.you.todo_app.notes.notifications.NoteEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ConsumerFactory<String, NoteEvent> noteEventConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        // Настройка ErrorHandlingDeserializer
        JsonDeserializer<NoteEvent> jsonDeserializer = new JsonDeserializer<>();
        jsonDeserializer.addTrustedPackages("*"); // Доверяем всем пакетам

        // Настройка ErrorHandlingDeserializer для обработки ошибок десериализации
        ErrorHandlingDeserializer<NoteEvent> errorHandlingDeserializer = new ErrorHandlingDeserializer<>(jsonDeserializer);

        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, errorHandlingDeserializer.getClass().getName());

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), errorHandlingDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, NoteEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, NoteEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(noteEventConsumerFactory());
        return factory;
    }

}
