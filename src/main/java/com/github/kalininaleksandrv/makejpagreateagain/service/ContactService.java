package com.github.kalininaleksandrv.makejpagreateagain.service;

import com.github.kalininaleksandrv.makejpagreateagain.model.ContactInfo;
import com.github.kalininaleksandrv.makejpagreateagain.model.Contacts;

import java.util.List;

public interface ContactService {

    List<Contacts> findAllContacts();

    List<ContactInfo> findAllContactInfo();
}
