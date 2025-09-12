package com.example.OLS.Controller;

import com.example.OLS.Model.IssueTransaction;
import com.example.OLS.Repository.IssuingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/issue")
public class IssueController {

    @Autowired
    private IssuingServices issuingServices;


    @GetMapping("/getalltransactions")
    public ResponseEntity<List<IssueTransaction>> getAllTransaction(){
        if(issuingServices.getALLtransactions()==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
            return new ResponseEntity<>(issuingServices.getALLtransactions(),HttpStatus.OK);
    }
    @GetMapping("gettransactionbyid/{id}")
    public ResponseEntity<IssueTransaction> getTransactionById(@PathVariable UUID id){
        try{
          IssueTransaction issueTransaction = issuingServices.gettransactionbyid(id);
          return new ResponseEntity<>(issueTransaction,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/bookissue/{title}")
    public ResponseEntity<?> bookIssue(@PathVariable String title, Authentication authentication){
        IssueTransaction issueTransaction = null;
        String username = authentication.getName();
        try{
            issueTransaction = issuingServices.saveIssueTransaction(username,title);
            return new ResponseEntity<>(issueTransaction,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }
    }


    @PutMapping("/returnbook/{bookid}")
    public ResponseEntity<?>returnBook( @PathVariable String bookid,Authentication authentication){
        IssueTransaction issueTransaction = null;
        String username = authentication.getName();
        try{
            issueTransaction = issuingServices.returnBook(username,bookid);
            return new ResponseEntity<>(issueTransaction,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }
    }


}
