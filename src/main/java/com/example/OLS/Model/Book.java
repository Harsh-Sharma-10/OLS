package com.example.OLS.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book{

    @Id
    private String id;
    @NonNull
    private String title;
    @NonNull
    private String author;
    @NonNull
    private String ISBN;
    @NonNull
    private int quantity;
    @NonNull
    private boolean status;

    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL)
    private List<IssueTransaction> transactions = new ArrayList<>();



    }

