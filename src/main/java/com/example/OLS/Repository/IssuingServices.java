package com.example.OLS.Repository;


import com.example.OLS.Model.IssueTransaction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public interface IssuingServices {
    List<IssueTransaction>getALLtransactions();
    IssueTransaction gettransactionbyid(UUID id);
    IssueTransaction saveIssueTransaction(String username, String title);
    IssueTransaction returnBook(String username, String bookid);

}
