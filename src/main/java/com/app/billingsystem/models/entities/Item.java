package com.app.billingsystem.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  int id;
    private  String name;
    private String description;
    private  double price;
    private MultipartFile image;
    private  Date creationDate;
    private Date lastModifiedDate;
    @ManyToOne
    @JoinColumn(name = "item")
    private SoldItems soldItems;
    @ManyToOne
    @JoinColumn(name="user")
    private User user;



}
