package my.ddos.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    public static final String ORDER_TOPIC = "order_topic";

    public static final String PAYMENT_SUCCESS_TOPIC = "payment_success_topic";

    public static final String PAYMENT_DEAD_LETTER_TOPIC = "payment_dlt";

    public static final String NOTIFICATION_SUCCESS_ORDER_TOPIC = "notification_success_order_topic";

    public static final String NOTIFICATION_FAILURE_ORDER_TOPIC = "notification_failure_order_topic";


    @Bean
    public NewTopic createOrderTopic(){
        return TopicBuilder.name(ORDER_TOPIC)
                .replicas(1)
                .partitions(3)
                .build();
    }


    @Bean
    public NewTopic createPaymentSuccessTopic(){
        return TopicBuilder.name(PAYMENT_SUCCESS_TOPIC)
                .replicas(1)
                .partitions(3)
                .build();
    }


    @Bean
    public NewTopic createPaymentDeadLetterTopic(){
        return TopicBuilder.name(PAYMENT_DEAD_LETTER_TOPIC)
                .replicas(1)
                .partitions(3)
                .build();
    }
}
