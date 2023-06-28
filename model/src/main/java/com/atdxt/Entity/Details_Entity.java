package com.atdxt.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Entity
@Table(name= "userdetails")
public class Details_Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int detid;
    @Column(name="email")
    private String email;

    @Column(name= "designation")
    private String designation;

    @Column(name = "createdon")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
    private LocalDateTime createdon;

    @Column(name = "modified")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
    private LocalDateTime modified;

    public String getFormattedDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        createdon = LocalDateTime.parse(formattedDateTime, formatter);
        modified = LocalDateTime.parse(formattedDateTime, formatter);
    }

    @PreUpdate
    protected void onUpdate() {
        modified = LocalDateTime.now();
    }







}
