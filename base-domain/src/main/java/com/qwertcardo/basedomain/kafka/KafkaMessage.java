package com.qwertcardo.basedomain.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KafkaMessage<T> {
    private String topic;
    private String context;
    private T entity;
    private Map<String, Object> properties;

    public String getAction() {
        return (String) this.properties.get("action");
    }
}
