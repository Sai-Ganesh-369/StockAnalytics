package io.endeavour.stocks.service;

import io.endeavour.stocks.entity.AddressEntity;
import io.endeavour.stocks.entity.PersonEntity;
import io.endeavour.stocks.repository.AddressRepository;
import io.endeavour.stocks.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CrudService {
    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;

    public CrudService(PersonRepository personRepository, AddressRepository addressRepository) {
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
    }

    public List<PersonEntity> getPersons() {
        List<PersonEntity> personList = personRepository.findAll();
        return personList;
    }

    public PersonEntity savePerson(PersonEntity person) {
        List<AddressEntity> addressList = Optional.ofNullable(person.getAddresses()).orElse(List.of());
        addressList.forEach(address -> address.setPerson(person));
        return personRepository.save(person);
    }

    public Optional<PersonEntity> getPerson(Integer id) {
        Optional<PersonEntity> optionalPerson = personRepository.findById(id);
        return optionalPerson;
    }

    public void deletePerson(Integer id) {
        personRepository.deleteById(id);
    }
}
