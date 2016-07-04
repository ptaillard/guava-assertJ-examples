package com.dojo.guava.functional;

import static com.dojo.team.PersonBuilder.onePerson;
import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.testng.annotations.Test;

import com.dojo.team.Person;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

public class FunctionalShould {
    
    /*
     * Java 8 provides a new "Streams" library, which is similar to FluentIterable but generally more powerful.
     * Key differences include:
     *  - A stream is single-use:
     *      It becomes invalid as soon as any "terminal operation" such as findFirst() or iterator() is invoked.
     *  - Streams offer many features not found here, including min/max, distinct, reduce, sorted, 
     *      the very powerful collect, and built-in support for parallelizing stream operations.
     *  - FluentIterable contains several features not available on Stream, which are noted in the method descriptions below.
     *  - Streams include primitive-specialized variants such as IntStream, the use of which is strongly recommended.
     *  - Streams are standard Java, not requiring a third-party dependency (but do render your code incompatible with Java 7 and earlier).
     */
    
    private List<Person> persons = newArrayList(
                                                onePerson().withName("lea").withAge(16).build(),
                                                onePerson().withName("paul").withAge(17).build(),
                                                onePerson().withName("camille").withAge(18).build(),
                                                onePerson().withName("lucas").withAge(14).build(),
                                                onePerson().withName("michel").withAge(50).build()
                                              );
    
    private class AdultPredicate implements Predicate<Person> {
        @Override
        public boolean apply(Person person) {
            return person.getAge() >= 18;
        }
    }
    
    private class NameExtractor implements Function<Person, String> {
        @Override
        public String apply(Person person) {
            return person.getName();
        }
    }
    
    @Test
    public void retrieveAdultNamesWithCollections2() {
        
        Collection<Person> adults = Collections2.filter(persons, new AdultPredicate());
        Collection<String> adultNames = Collections2.transform(adults, new NameExtractor());

        assertThat(adultNames).containsExactlyInAnyOrder("camille", "michel");
    }
    
    @Test
    public void retrieveAdultNamesWithIterables() {
        
        Iterable<Person> adults = Iterables.filter(persons, new AdultPredicate());
        
        
        List<String> adultNames = newArrayList(Iterables.transform(adults, new NameExtractor()));
        
        assertThat(adultNames).containsExactlyInAnyOrder("camille", "michel");
    }
    
    @Test
    public void retrieveAdultNamesWithFluentIterables() {
        ImmutableList<String> adultNames = FluentIterable.from(persons).filter(new AdultPredicate()).transform(new NameExtractor()).toList();
    
        assertThat(adultNames).containsExactlyInAnyOrder("camille", "michel");
    }
    
    @Test
    public void retrieveAdultNamesWithFluentIterablesWithJava8() {
        ImmutableList<String> adultNames = FluentIterable.from(persons)
                                                            .filter((Person person) -> { 
                                                                return person.getAge() >= 18;
                                                             })
                                                            .transform((Person person) -> {
                                                                return person.getName();
                                                            }).toList();
    
        assertThat(adultNames).containsExactlyInAnyOrder("camille", "michel");
    }
    
    @Test
    public void retrieveAdultNamesWithFluentIterablesWithJava8MoreConcise() {
        ImmutableList<String> adultNames = FluentIterable.from(persons)
                                                            .filter((Person person) -> person.getAge() >= 18)
                                                            .transform((Person person) -> person.getName()).toList();
    
        assertThat(adultNames).containsExactlyInAnyOrder("camille", "michel");
    }
    
    @Test
    public void retrieveAdultNamesWithFluentIterablesWithJava8MoreMoreConcise() {
        ImmutableList<String> adultNames = FluentIterable.from(persons)
                                                            .filter(person -> person.getAge() >= 18)
                                                            .transform(person -> person.getName()).toList();
    
        assertThat(adultNames).containsExactlyInAnyOrder("camille", "michel");
    }
    
    @Test
    public void retrieveAdultNamesWithStream() {
        List<String> adultNames = persons.stream().filter(person -> person.getAge() >= 18)
                                                .map(person -> person.getName())
                                                .collect(Collectors.toList());
    
        assertThat(adultNames).containsExactlyInAnyOrder("camille", "michel");
    }
    
}
