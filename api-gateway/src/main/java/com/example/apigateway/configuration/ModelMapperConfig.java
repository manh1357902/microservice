package com.example.apigateway.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up ModelMapper.
 * Provides a {@link ModelMapper} bean with strict matching strategy for object mapping.
 */
@Configuration
public class ModelMapperConfig {

    /**
     * Configures and provides a ModelMapper bean with strict matching strategy.
     *
     * @return a configured ModelMapper instance
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper;
    }

}
