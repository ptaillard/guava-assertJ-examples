package com.dojo.assertj;

import static com.dojo.team.PersonBuilder.onePerson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Percentage.withPercentage;
import static org.assertj.core.groups.Tuple.tuple;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.testng.annotations.Test;

import com.dojo.team.Person;
import com.google.common.collect.Lists;

public class AssertJShould {
    
    private Person marc = onePerson().withName("Marc").withAge(20).build();
    private Person sbaff = onePerson().withName("sbaff").withAge(30).build();
    private Person bernard = onePerson().withName("Bernard").withAge(29).build();
    private Person loic = onePerson().withName("loic").withAge(35).build();
    private Person alex = onePerson().withName("alex").withAge(35).build();
    private Person seb = onePerson().withName("seb").withAge(34).build();
    private Person jean = onePerson().withName("jean").withAge(35).build();
    
    private List<Person> team = Lists.newArrayList(marc, sbaff, bernard, loic, alex, seb);
    
    private List<Integer> ages = Lists.newArrayList(30, 4, 63, 98);

    @Test
    public void allowFLuentAssertionsForBoolean() {

        Assertions.assertThat(true).isTrue();
        assertThat(false).isFalse();
    }
    
    @Test
    public void allowFLuentAssertionsForCheckNullOrEmpty() {

        String value = null;
        assertThat(value).isNull();
        assertThat("").isNullOrEmpty();
        assertThat("value").isNotNull();
    }
    
    @Test
    public void allowFLuentAssertions() {

        assertThat(marc.getAge()).isEqualTo(20);
        
        assertThat(bernard.getAge()).isGreaterThan(marc.getAge())
                                    .isBetween(marc.getAge(), loic.getAge())
                                    .isNotEqualTo(loic.getAge())
                                    .isLessThan(loic.getAge())
                                    .isIn(10, 29, 63)
                                    .isNotIn(ages)
                                    .isNotNegative()
                                    .isPositive()
                                    .isCloseTo(30, withPercentage(4));
                                    
        
        assertThat(loic.getName()).isEqualTo("loic");
        
        assertThat(loic.getName()).containsIgnoringCase("oi");
        assertThat(marc.getName()).as("check %s's name (ignoring case)", marc.getName()).isEqualToIgnoringCase("marc");
        
        assertThat(bernard.getName()).startsWith("Be").endsWith("d");
    }
    
    
    
    @Test
    public void allowFLuentAssertionsForCollections() {
        
        assertThat(team).contains(loic);
        assertThat(team).containsExactly(marc, sbaff, bernard, loic, alex, seb);
        assertThat(team).containsExactlyInAnyOrder(sbaff, alex, loic, marc, seb, bernard);
        
        assertThat(team).extracting("name")
                        .contains("seb", "sbaff")
                        .doesNotContain(jean.getName());
        
        assertThat(team).extracting("name", "age")
                        .contains(tuple("sbaff", 30),
                                  tuple("seb", 34));
        
        assertThat(team).filteredOn("age", 35)
                        .containsOnly(alex, loic);
        
        assertThat(team).filteredOn(Person -> Person.getName().contains("a"))
                        .containsOnly(alex, sbaff, bernard, marc);
        
        assertThat(team).filteredOn(Person -> Person.getName().contains("e"))
                                    .contains(seb)
                                    .doesNotContain(marc)
                                    .extracting(Person -> Person.getAge())
                                    .containsExactly(29, 35, 34);
    }
    
    @Test
    public void allowFluentAssertionsWithConditions() {
        
        Condition<Person> notTeenagers = new Condition<Person>() {

            @Override
            public boolean matches(Person person) {
                return person.getAge() > 18;
            }
        };
        
        Condition<Person> notSeniors = new Condition<Person>() {

            @Override
            public boolean matches(Person person) {
                return person.getAge() < 60;
            }
        };
        
        Condition<Person> thirtyFiveYearsOld = new Condition<Person>() {

            @Override
            public boolean matches(Person person) {
                return person.getAge() == 35;
            }
        };
        
        assertThat(team).are(notTeenagers)
                        .are(notSeniors)
                        .areAtLeast(2, thirtyFiveYearsOld);
    }
    
    @Test
    public void allowFLuentAssertionsForThrowable() {
        
        Throwable thrown = Assertions.catchThrowable(() -> { throw new Exception("boom!"); });
        assertThat(thrown).hasMessageContaining("boom");
    }
}
