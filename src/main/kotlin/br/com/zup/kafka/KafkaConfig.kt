package br.com.zup.kafka

import br.com.zup.extrato.ExtratoKafka
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.kafka.support.serializer.JsonSerializer
import java.util.*

@Configuration
class KafkaConfig {

    @Value(value = "\${kafka.consumer.bootstrap.server}")
    private lateinit var bootstrapServer: String

    @Value(value = "\${kafka.consumer.group.id}")
    private lateinit var groupId: String

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, ExtratoKafka> {
        //Definindo propriedades do consumer kafka
        val props = mapOf(
            Pair(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer),
            Pair(ConsumerConfig.GROUP_ID_CONFIG, groupId),
            Pair(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java),
            Pair(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer::class.java),
            Pair(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false"),
            Pair(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
        )

        //Aplicando propriedades e definindo classe de deserialização
        val customConsumerFactory = DefaultKafkaConsumerFactory(
            props,
            StringDeserializer(),
            JsonDeserializer(ExtratoKafka::class.java, false))

        val factory = ConcurrentKafkaListenerContainerFactory<String, ExtratoKafka>()
        factory.consumerFactory = customConsumerFactory

        //Definindo commit como manual
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
        factory.containerProperties.isSyncCommits = true

        return factory
    }
}