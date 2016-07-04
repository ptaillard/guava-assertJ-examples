package com.dojo.guava.strings;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.testng.annotations.Test;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

public class SplitterShould {

    @Test
    public void splitEmail() {
        List<String> split = Splitter.on(CharMatcher.anyOf("@."))
                                     .splitToList("jean.dupond@purch.com");
        
        assertThat(split).containsExactly("jean", "dupond", "purch", "com");
    }
    
    @Test
    public void keepOnlyNotEmptyValues() {
        Iterable<String> split = Splitter.on(",")
                                         .trimResults()
                                         .omitEmptyStrings()
                                         .split("  ,  , value1 , value2,");
        
        assertThat(split).containsExactly("value1", "value2");
    }
    
}
