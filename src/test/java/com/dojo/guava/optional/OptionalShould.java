package com.dojo.guava.optional;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.google.common.base.Optional;

public class OptionalShould {

    @Test
    public void createOptionalFromNullable() {
        String nullString = null;
        Optional<String> optional = Optional.fromNullable(nullString);
        
        assertThat(optional.isPresent()).isFalse();
        
        String myValue = "myvalue";
        optional = Optional.fromNullable(myValue);
        
        assertThat(optional.isPresent()).isTrue();
        assertThat(optional.get()).isEqualTo(myValue);
    }
    
    @Test
    public void createOptionalOf() {
        String myValue = "myvalue";
        Optional<String> optional = Optional.of(myValue);
        
        assertThat(optional.isPresent()).isTrue();
        assertThat(optional.get()).isEqualTo(myValue);
    }
    
    @Test
    public void addDefaultValue() {
        String myValue = null;
        Optional<String> optional = Optional.fromNullable(myValue);
        
        String defaultValue = "default";
        assertThat(optional.or(defaultValue)).isEqualTo(defaultValue); 
    }
}
