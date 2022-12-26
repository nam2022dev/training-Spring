package com.codede.spring.repo;

import com.codede.spring.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface PersonRepo extends JpaRepository<Person, Integer>, Repository<Person, Integer> {

    //Lay ra ban ghi moi nhat
    @Query(value = "SELECT * FROM person ORDER BY id DESC LIMIT 1",
            nativeQuery = true)
    Person get();

//    @Query(value = "select * from person p where p.fullname ilike '%:x%'",
//            nativeQuery = true)
        Page<Person> findByFullNameLikeIgnoreCase(String name, Pageable pageable);
}
