package com.github.kalininaleksandrv.makejpagreateagain.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
/*
InheritanceType.JOINED means we will create 3 separate table - for ContactInfo, for ContactInfoEmail and
for ContactInfoPhone, when all ContactInfo entity will be fetched - the 2 outer join will be performed
 */
@JsonTypeInfo(use = NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ContactInfoEmail.class, name = "email"),
        @JsonSubTypes.Type(value = ContactInfoPhone.class, name = "phone")})
public abstract class ContactInfo {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_seq_gen")
    @SequenceGenerator(allocationSize = 1, name="contact_seq_gen", sequenceName="contact_id_seq")
    private Integer id;

    @Getter
    @Setter
    private String description;
}
