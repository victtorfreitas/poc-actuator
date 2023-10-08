package com.poc.actuator.config;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.poc.actuator.repository.entity.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomHealth {
    @JsonAnyGetter
    private Map<String, Status> healthDetails;
}