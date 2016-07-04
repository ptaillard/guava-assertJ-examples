package com.dojo.team;

public class PersonBuilder {
    
    private String name;
    private int age;

    public static PersonBuilder onePerson() {
        return new PersonBuilder();
    }
    
    public PersonBuilder withName(String name) {
        this.name = name;
        return this;
    }
    
    public PersonBuilder withAge(int age) {
        this.age = age;
        return this;
    }
    
    public Person build() {
        return new Person(name, age);
    }
}
