package com.moonshot.urlshorteningservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Url {

    @Id
    @GeneratedValue
    private long id;
    @Lob
    private String userProvidedUrl;
    private String shortUrlLink;
    private LocalDateTime creationDate;
    private LocalDateTime expiryDate;
}
