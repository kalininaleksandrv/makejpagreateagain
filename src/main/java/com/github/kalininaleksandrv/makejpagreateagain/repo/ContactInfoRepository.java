package com.github.kalininaleksandrv.makejpagreateagain.repo;

import com.github.kalininaleksandrv.makejpagreateagain.model.ContactInfo;
import com.github.kalininaleksandrv.makejpagreateagain.model.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContactInfoRepository extends JpaRepository<ContactInfo, Integer> {

    /*
    we can use Contact interface instead of ContactInfo class
    to fetch all impl of this interface
    this method is not overriding of findAll()
     */
    @Query("select c from ContactInfo c")
    List<Contacts> findAllContacts();
}
