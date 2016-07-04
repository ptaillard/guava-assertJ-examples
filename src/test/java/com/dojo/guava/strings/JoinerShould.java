package com.dojo.guava.strings;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.google.common.base.Joiner;

public class JoinerShould {

    @Test
    public void joinListContainingNulls() {
        List<String> listContainingNull = newArrayList("abc", null, "myvalue", null);
        
        String resultWithDefaultValueForNull = Joiner.on(',')
                                                     .useForNull("undefined")
                                                     .join(listContainingNull);
        assertThat(resultWithDefaultValueForNull).isEqualTo("abc,undefined,myvalue,undefined");

        
        String resultSkipNulls = Joiner.on('@')
                                       .skipNulls()
                                       .join(listContainingNull);
        assertThat(resultSkipNulls).isEqualTo("abc@myvalue");
    }
    
    @Test
    public void joinMap() {
        Map<Integer, String> map = newHashMap();
        map.put(10, "ten");
        map.put(7, "seven");
        
        String result = Joiner.on('#')
                              .withKeyValueSeparator(":").join(map);
        
        assertThat(result).isEqualTo("7:seven#10:ten");
    }
    
}
