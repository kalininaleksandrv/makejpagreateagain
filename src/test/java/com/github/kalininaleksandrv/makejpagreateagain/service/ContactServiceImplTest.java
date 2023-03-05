package com.github.kalininaleksandrv.makejpagreateagain.service;

import com.github.kalininaleksandrv.makejpagreateagain.model.ContactInfo;
import com.github.kalininaleksandrv.makejpagreateagain.model.ContactInfoEmail;
import com.github.kalininaleksandrv.makejpagreateagain.model.ContactInfoPhone;
import com.github.kalininaleksandrv.makejpagreateagain.model.Contacts;
import com.github.kalininaleksandrv.makejpagreateagain.repo.ContactInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContactServiceImplTest extends UserAndAccountBaseApplicationTests {

    @Autowired
    ContactServiceImpl contactService;

    @Autowired
    ContactInfoRepository contactInfoRepository;

    @Test
    void findAllContacts() {

        /*
        let's not forget - one contact already in DB, we need 9 more to reach 10
         */
        List<ContactInfo> allContactInfo = IntStream.rangeClosed(1, 9)
                .mapToObj(val -> (val % 2 == 0) ?
                        new ContactInfoPhone(val + "-111111111") :
                        new ContactInfoEmail(val + "some@email.com"))
                .collect(Collectors.toList());

        contactInfoRepository.saveAll(allContactInfo);

        /*
        we can use Contact interface instead of ContactInfo class
        to fetch all impl of this interface
        see query in repo impl
     */
        List<Contacts> allContacts = contactService.findAllContacts();
        assertEquals(10, allContacts.size());
    }
}