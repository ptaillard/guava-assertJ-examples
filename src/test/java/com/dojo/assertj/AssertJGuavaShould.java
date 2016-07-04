package com.dojo.assertj;


import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.guava.api.Assertions.assertThat;
import static org.assertj.guava.api.Assertions.entry;

import org.testng.annotations.Test;

import com.google.common.base.Optional;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Multimap;
import com.google.common.collect.Range;
import com.google.common.collect.Table;

public class AssertJGuavaShould {

    @Test
    public void checkOptionalAbsent() {
        Optional<String> purch = Optional.absent();

        assertThat(purch).isAbsent();
    }
    
    @Test
    public void checkOptionalContent() {
        Optional<String> purch = Optional.of("Purch");

        assertThat(purch).isPresent()
                            .contains("Purch");
        
        assertThat(purch).extractingValue()
                            .isEqualTo("Purch");
        
        assertThat(purch).extractingCharSequence()
                            .startsWith("P");
    }
    
    @Test
    public void checkGuavaTable() {
        Table<Integer, String, String> bestMovies = HashBasedTable.create();

        bestMovies.put(1970, "Palme d'Or", "M.A.S.H");
        bestMovies.put(1994, "Palme d'Or", "Pulp Fiction");
        bestMovies.put(2008, "Palme d'Or", "Entre les murs");
        bestMovies.put(2000, "Best picture Oscar", "American Beauty");
        bestMovies.put(2011, "Goldene Bär", "A Separation");

        assertThat(bestMovies).hasRowCount(5).hasColumnCount(3).hasSize(5)
                              .containsValues("American Beauty", "A Separation", "Pulp Fiction")
                              .containsCell(1994, "Palme d'Or", "Pulp Fiction")
                              .containsColumns("Palme d'Or", "Best picture Oscar", "Goldene Bär")
                              .containsRows(1970, 1994, 2000, 2008, 2011);

    }
    
    @Test
    public void checkMultimap() {
      
      Multimap<String, String> nbaTeams = ArrayListMultimap.create();
      nbaTeams.putAll("Lakers", newArrayList("Kobe Bryant", "Magic Johnson", "Kareem Abdul Jabbar"));
      nbaTeams.putAll("Spurs", newArrayList("Tony Parker", "Tim Duncan", "Manu Ginobili"));

      assertThat(nbaTeams).hasSize(6);
      assertThat(nbaTeams).containsKeys("Lakers", "Spurs");
      assertThat(nbaTeams).contains(entry("Lakers", "Kobe Bryant"), entry("Spurs", "Tim Duncan"));
      assertThat(nbaTeams).containsValues("Kareem Abdul Jabbar", "Tony Parker");
      assertThat(nbaTeams).hasSameEntriesAs(nbaTeams);
      assertThat(nbaTeams).containsAllEntriesOf(nbaTeams);

      Multimap<String, String> emptyMultimap = ArrayListMultimap.create();
      assertThat(emptyMultimap).isEmpty();
    }
    
    @Test
    public void checkRange() {

      Range<Integer> range = Range.closed(10, 12);

      assertThat(range).isNotEmpty()
                       .contains(10, 11, 12)
                       .hasClosedLowerBound()
                       .hasLowerEndpointEqualTo(10);
    }
}
