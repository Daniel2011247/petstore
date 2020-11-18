package com.petstore.data.repository;

import com.petstore.data.model.Gender;
import com.petstore.data.model.Pet;
import com.petstore.data.model.Store;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL;


@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class PetRepositoryTest {

    @Autowired
    PetRepository petRepository;

    @Autowired
    StoreRepository storeRepository;


    @BeforeEach
    void setUp() {
    }


    //Test that we can save a pet in database
    @Test
    @Transactional
    @Rollback(value = false)
    public void whenStoreIsMapped_ThenForeignKeyIsPresent(){

        //step 1 : Create an instance of a pet
        Pet pet = new Pet();
        pet.setName("Danny");
        pet.setAge(2);
        pet.setBreed("Dog");
        pet.setColor("Black");
        pet.setPetSex(Gender.MALE);

        //create a store
        Store store = new Store();
        store.setName("Pet Sellers");
        store.setLocation("Yaba");
        store.setContactNo("09088585748");

        log.info("Pet instance before saving --> {}", pet);
        //map pet to share
        pet.setStore(store);
        //save pet
        petRepository.save(pet);

        log.info("Pet instance after saving --> {}", pet);
        log.info("Pet instance after saving --> {}", store);

        //assert
        assertThat(pet.getId()).isNotNull();
        assertThat(store.getId()).isNotNull();
        assertThat(pet.getStore()).isNotNull();
    }



    @Test
    @Transactional
    @Rollback(value = false)
    public void whenIAddPetsToAStore_thanICanFetchAListOfPetsFromStore(){

        Pet jack = new Pet();
        jack.setName("Jack");
        jack.setAge(5);
        jack.setBreed("Dog");
        jack.setColor("Black");
        jack.setPetSex(Gender.MALE);

        //create a pet
        Pet sally = new Pet();
        sally.setName("sally");
        sally.setAge(2);
        sally.setBreed("Dog");
        sally.setColor("Brown");
        sally.setPetSex(Gender.FEMALE);

        log.info("Pet instance before saving -->{}", jack, sally);

        //create a store
        Store store = new Store();
        store.setName("Pet Sellers");
        store.setLocation("Yaba");
        store.setContactNo("09088585748");

        jack.setStore(store);
        sally.setStore(store);

        store.addPets(jack);
        store.addPets(sally);

        storeRepository.save(store);

        log.info("Store saved --> {}", store);


        assertThat(sally.getId()).isNotNull();
        assertThat(store.getId()).isNotNull();
        assertThat(jack.getStore()).isNotNull();
        assertThat(store.getPetList()).isNotNull();
    }

    @Test
    public void whenFindAllPetIsCalled_thenReturnAllPetsInStore(){
        List<Pet> savedPets = petRepository.findAll();

        assertThat(savedPets).isNotEmpty();
        assertThat(savedPets.size()).isEqualTo(7);
    }

    public void updateExistingPetDetailsTest(){

        //fetch a pet

        Pet sally = petRepository.findById(35).orElse( null) ;
        log.info("Pet object retrieved from data --> {}", sally);
        //assert the field
        assertThat(sally).isNotNull();
        assertThat(sally.getColor()).isEqualTo("brown");
        //update pet field
        sally.setColor("purple");
        //save pet
        petRepository.save(sally);
        log.info("After updating pet object --> {}", sally);

        //assert that updated field has change
        assertThat(sally.getColor()).isEqualTo("purple");

    }

    @Test
    public void whenIdeletePetFromDatabase_thenPetIsDeleted(){

        assertThat(petRepository.existsById(30)).isTrue();
        petRepository.deleteById(30);
        assertThat(petRepository.existsById(30)).isFalse();
    }

}