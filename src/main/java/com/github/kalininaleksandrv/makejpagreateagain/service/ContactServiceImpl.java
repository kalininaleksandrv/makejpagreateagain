package com.github.kalininaleksandrv.makejpagreateagain.service;

import com.github.kalininaleksandrv.makejpagreateagain.model.ContactInfo;
import com.github.kalininaleksandrv.makejpagreateagain.model.Contacts;
import com.github.kalininaleksandrv.makejpagreateagain.repo.ContactInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService{

    private final ContactInfoRepository contactInfoRepository;

    @Override
    public List<Contacts> findAllContacts() {
        return contactInfoRepository.findAllContacts();
    }

    @Override
    public List<ContactInfo> findAllContactInfo() {
        return contactInfoRepository.findAll();
    }


}
