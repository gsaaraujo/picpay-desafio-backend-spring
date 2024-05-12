package com.gsa.picpaydesafiobackend.infra.controllers.transfer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.gsa.picpaydesafiobackend.infra.http.responses.NotFound;
import com.gsa.picpaydesafiobackend.infra.http.responses.Conflict;
import com.gsa.picpaydesafiobackend.infra.http.responses.BadRequest;
import com.gsa.picpaydesafiobackend.infra.http.responses.HttpResponse;
import com.gsa.picpaydesafiobackend.infra.http.responses.ResponseError;
import com.gsa.picpaydesafiobackend.application.exceptions.PayeeWalletNotFoundException;
import com.gsa.picpaydesafiobackend.application.exceptions.PayerWalletNotFoundException;
import com.gsa.picpaydesafiobackend.application.exceptions.PayerCannotBeShopkeeperException;
import com.gsa.picpaydesafiobackend.application.exceptions.TransactionNotAuthorizedException;
import com.gsa.picpaydesafiobackend.application.exceptions.PayerCannotTransferToHimselfException;
import com.gsa.picpaydesafiobackend.application.exceptions.PayerDoesNotHaveEnoughBalanceException;

@RestControllerAdvice(assignableTypes = TransferController.class)
public class TransferExceptionHandler {

  @ExceptionHandler(PayerWalletNotFoundException.class)
  public ResponseEntity<HttpResponse> handle(PayerWalletNotFoundException exception) {
    var responseError = new ResponseError("The payer does not exist in our records",
        "Please check if the payer ID is correct");
    var notFound = new NotFound(responseError);
    return ResponseEntity.status(HttpStatus.CONFLICT).body(notFound);
  }

  @ExceptionHandler(PayeeWalletNotFoundException.class)
  public ResponseEntity<HttpResponse> handle(PayeeWalletNotFoundException exception) {
    var responseError = new ResponseError("The payee does not exist in our records",
        "Please check if the payee ID is correct");
    var notFound = new NotFound(responseError);
    return ResponseEntity.status(HttpStatus.CONFLICT).body(notFound);
  }

  @ExceptionHandler(PayerCannotBeShopkeeperException.class)
  public ResponseEntity<HttpResponse> handle(PayerCannotBeShopkeeperException exception) {
    var responseError = new ResponseError("The payer is a shopkeeper and therefore cannot make transfers");
    var conflict = new Conflict(responseError);
    return ResponseEntity.status(HttpStatus.CONFLICT).body(conflict);
  }

  @ExceptionHandler(PayerCannotTransferToHimselfException.class)
  public ResponseEntity<HttpResponse> handle(PayerCannotTransferToHimselfException exception) {
    var responseError = new ResponseError("The payer cannot transfer to himself",
        "Please check if the payer and payee IDs are different");
    var conflict = new Conflict(responseError);
    return ResponseEntity.status(HttpStatus.CONFLICT).body(conflict);
  }

  @ExceptionHandler(PayerDoesNotHaveEnoughBalanceException.class)
  public ResponseEntity<HttpResponse> handle(PayerDoesNotHaveEnoughBalanceException exception) {
    var responseError = new ResponseError("The payer does not have enough balance to make the transfer",
        "Please check if the payer has enough balance");
    var conflict = new Conflict(responseError);
    return ResponseEntity.status(HttpStatus.CONFLICT).body(conflict);
  }

  @ExceptionHandler(TransactionNotAuthorizedException.class)
  public ResponseEntity<HttpResponse> handle(TransactionNotAuthorizedException exception) {
    var responseError = new ResponseError("The transaction was not authorized");
    var conflict = new Conflict(responseError);
    return ResponseEntity.status(HttpStatus.CONFLICT).body(conflict);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<HttpResponse> handle(MethodArgumentNotValidException exception) {

    var allErrorMessages = exception.getBindingResult().getAllErrors().stream().map(error -> error.getDefaultMessage())
        .toList().toString();

    allErrorMessages = allErrorMessages.substring(1, allErrorMessages.length() - 1);
    var responseError = new ResponseError(allErrorMessages, "Please check the request body");
    var badRequest = new BadRequest(responseError);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequest);
  }

}
