package com.remondis.remap.wrapperclass;

import org.junit.Test;

import com.remondis.remap.AssertMapping;
import com.remondis.remap.Mapping;

public class DemoTest {
  @Test
  public void demoMapping() {
    MyMapper customerPersonMapper = Mapping.from(Customer.class)
        .to(Person.class)
        // A customer has an address, a person might have no address
        .omitInSource(Customer::getAddress)
        // A person has a body height that is not available for a customer
        .omitInDestination(Person::getBodyHeight)
        // A customer has a titles aka. salutation that maps to a persons salutation.
        .reassign(Customer::getTitle)
        .to(Person::getSalutation)
        // A customer has a gender as string, person uses a gender enumeration
        .replace(Customer::getGender, Person::getGender)
        .withSkipWhenNull(Gender::valueOf)
        .mapper(MyMapper.class);

    AssertMapping.of(customerPersonMapper)
        // A customer has an address, a person might have no address
        .expectOmitInSource(Customer::getAddress)
        // A person has a body height that is not available for a customer
        .expectOmitInDestination(Person::getBodyHeight)
        // A customer has a titles aka. salutation that maps to a persons salutation.
        .expectReassign(Customer::getTitle)
        .to(Person::getSalutation)
        // A customer has a gender as string, person uses a gender enumeration
        .expectReplace(Customer::getGender, Person::getGender)
        .andSkipWhenNull()
        .ensure();
  }
}
