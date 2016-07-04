package com.dojo.guava.collections;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.testng.annotations.Test;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Table;

public class NewCollections {

    @Test
    public void biMap() {
       BiMap<Integer, String> biMap = HashBiMap.create(); 
       biMap.put(12, "twelve");
       biMap.put(2, "two");
       
       assertThat(biMap.get(2)).isEqualTo("two");
       assertThat(biMap.inverse().get("two")).isEqualTo(2);
    }
    
    @Test
    public void hashMultiset() {
       List<String> words = newArrayList("one", "three", "one", "one", "two", "three");
       Multiset<String> multiset = HashMultiset.create(words); 
       
       
       assertThat(multiset.count("one")).isEqualTo(3);
       assertThat(multiset.count("two")).isEqualTo(1);
       assertThat(multiset.count("three")).isEqualTo(2);
    }
    
    @Test
    public void hashMultimap() {
       Multimap<String, String> multimap = HashMultimap.create(); 
       multimap.put("france", "paris");
       multimap.put("france", "lyon");
       multimap.put("france", "grenoble");
       multimap.put("espagne", "madrid");
       multimap.put("espagne", "seville");
       
       assertThat(multimap.get("france")).containsExactlyInAnyOrder("paris", "lyon", "grenoble");
    }
    
    @Test
    public void immutables() {
        ImmutableList<String> immutableList = ImmutableList.<String>builder()
                                                            .add("paris")
                                                            .add("grenoble")
                                                            .add("lyon")
                                                            .build();
        assertThat(immutableList).containsExactlyInAnyOrder("paris", "lyon", "grenoble");
        

        ImmutableMap<Integer, String> immutableMap = ImmutableMap.<Integer, String>builder()
                                                                .put(1, "one")
                                                                .put(2, "two")
                                                                .build();
        assertThat(immutableMap.get(1)).isEqualTo("one");
    }
    
    @Test
    public void table() {
        Table<Integer, Integer, String> valueGraph = HashBasedTable.create();
        valueGraph.put(1, 1, "1-1");
        valueGraph.put(1, 2, "1-2");
        valueGraph.put(2, 3, "2-3");
        valueGraph.put(1, 3, "1-3");

        assertThat(valueGraph.size()).isEqualTo(4);
        assertThat(valueGraph.columnKeySet().size()).isEqualTo(3);
        assertThat(valueGraph.rowKeySet().size()).isEqualTo(2);
        assertThat(valueGraph.row(1)).containsOnlyKeys(1, 2, 3).containsValues("1-1", "1-2", "1-3");
        assertThat(valueGraph.column(1)).containsOnlyKeys(1).containsValues("1-1");
        assertThat(valueGraph.column(3)).containsOnlyKeys(1, 2).containsValues("1-3", "2-3");
    }
}
