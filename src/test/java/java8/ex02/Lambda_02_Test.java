package java8.ex02;

import java8.data.Account;
import java8.data.Data;
import java8.data.Person;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Exercice 02 - Map
 */
public class Lambda_02_Test {

    // tag::PersonToAccountMapper[]
    interface PersonToAccountMapper {
        Account map(Person p);
    }
    // end::PersonToAccountMapper[]

    // tag::map[]
    private List<Account> map(List<Person> personList, PersonToAccountMapper mapper) {
        List <Account> result = new ArrayList <> ();
        
        for (Person p : personList)
        {
        	result.add(mapper.map(p));
        }
    	
        return result;
    }
    // end::map[]


    // tag::test_map_person_to_account[]
    @Test
    public void test_map_person_to_account() throws Exception {

        List<Person> personList = Data.buildPersonList(100);

        // TODO transformer la liste de personnes en liste de comptes
        // TODO tous les objets comptes ont un solde à 100 par défaut
        PersonToAccountMapper p = new PersonToAccountMapper ()
        {
			@Override
			public Account map(Person p)
			{
				Account a = new Account ();
				a.setBalance(100);
				a.setOwner(p);
				return a;
				
			}
        	
        };
        List<Account> result = map(personList, p);

        assertThat(result, hasSize(personList.size()));
        assertThat(result, everyItem(hasProperty("balance", is(100))));
        assertThat(result, everyItem(hasProperty("owner", notNullValue())));
    }
    // end::test_map_person_to_account[]

    // tag::test_map_person_to_firstname[]
    @Test
    public void test_map_person_to_firstname() throws Exception {

        List<Person> personList = Data.buildPersonList(100);

        // TODO transformer la liste de personnes en liste de prénoms
        /*
        PersonToAccountMapper p = new PersonToAccountMapper ()
        {

			@Override
			public Account map(Person p)
			{
				Account a = new Account ();
				a.setBalance(100);
				a.setOwner(p);
				return a;
			}
        	
        };
        //*/
        PersonToAccountMapper p = t -> {
        	Account a = new Account ();
			a.setBalance(100);
			a.setOwner(t);
			return a;
        };
        List<Account> resultTemp = map(personList, p);
        
        
        List<String> result = new ArrayList <> ();
        
        for (Account a : resultTemp)
        {
        	result.add(a.getOwner().getFirstname());
        }

        assertThat(result, hasSize(personList.size()));
        assertThat(result, everyItem(instanceOf(String.class)));
        assertThat(result, everyItem(startsWith("first")));
    }
    // end::test_map_person_to_firstname[]
}
