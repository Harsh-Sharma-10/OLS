package com.example.OLS.Controller;

import com.example.OLS.Model.IssueTransaction;
import com.example.OLS.Repository.IssuingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("getbytransactonid/{id}")
    public ResponseEntity<IssueTransaction> getTransactionById(@PathVariable Long id){
        try{
          IssueTransaction issueTransaction = issuingServices.getTransaction(String.valueOf(id));
          return new ResponseEntity<>(issueTransaction,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/bookissue/{userid}/{bookid}")
    public ResponseEntity<?> bookIssue(@PathVariable int userid, @PathVariable String bookid){
        IssueTransaction issueTransaction = null;
        try{
            issueTransaction = issuingServices.saveIssueTransaction(userid,bookid);
            return new ResponseEntity<>(issueTransaction,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/returnbook/{txid}")
    public ResponseEntity<?> returnBook(@PathVariable UUID txid){
        IssueTransaction issueTransaction = null;
        try{
            issueTransaction = issuingServices.returnBook(txid);
            return new ResponseEntity<>(issueTransaction,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
