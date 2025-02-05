package com.dailycodework.cookshare.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;

    private String fileTye;

    @Lob
    private Blob image;

    private String downloadUrl;

    @OneToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

}
