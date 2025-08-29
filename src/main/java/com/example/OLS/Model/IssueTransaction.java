package com.example.OLS.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class IssueTransaction{

   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   private UUID id;

   @ManyToOne ///Many issuetransaction should be associated with a single user Many - to - one relation
   @JoinColumn(name = "user_id",nullable = false)/// it creates a foreign key user_id stores the reference id of user
   @JsonBackReference
   private User user;

   @ManyToOne/// Many issueTransaction should be associated with a single book
   @JoinColumn(name = "book_id",nullable = false)
   @JsonBackReference
   private Book book;

   @NonNull
   private LocalDate issueDate;

   private LocalDate returnDate;

    private boolean returned = false;

    private double fine = 0.0;// if applicable

}
