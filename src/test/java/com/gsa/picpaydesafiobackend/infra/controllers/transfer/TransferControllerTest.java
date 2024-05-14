package com.gsa.picpaydesafiobackend.infra.controllers.transfer;

import java.util.UUID;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import com.gsa.picpaydesafiobackend.infra.dao.WalletDAO;
import com.gsa.picpaydesafiobackend.infra.dao.CustomerDAO;
import com.gsa.picpaydesafiobackend.infra.dao.TransactionDAO;
import com.gsa.picpaydesafiobackend.infra.dao.orms.WalletORM;
import com.gsa.picpaydesafiobackend.infra.dao.orms.CustomerORM;
import com.gsa.picpaydesafiobackend.infra.dao.orms.CustomerType;

@SpringBootTest
@AutoConfigureMockMvc
public class TransferControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private WalletDAO walletDAO;
  @Autowired
  private CustomerDAO customerDAO;
  @Autowired
  private TransactionDAO transactionDAO;

  @BeforeEach
  public void beforeEach() {
    transactionDAO.deleteAll();
    walletDAO.deleteAll();
    customerDAO.deleteAll();
  }

  @Test
  void it_should_transfer() throws Exception {
    var payerORM = new CustomerORM(UUID.fromString("9fdf94aa-a539-47bf-8858-dc5b99dc688c"), "John Doe",
        CustomerType.COMMON, "81768869049", "john.doe@gmail.com");
    var walletPayerORM = new WalletORM(UUID.randomUUID(), payerORM, BigDecimal.valueOf(1000.00));
    customerDAO.save(payerORM);
    walletDAO.save(walletPayerORM);

    var payeeORM = new CustomerORM(UUID.fromString("1da7659e-b0ad-47db-a60f-af2216446bed"), "Jane Doe",
        CustomerType.COMMON, "79420473007", "jane.doe@gmail.com");
    var walletPayeeORM = new WalletORM(UUID.randomUUID(), payeeORM, BigDecimal.valueOf(1000.00));
    customerDAO.save(payeeORM);
    walletDAO.save(walletPayeeORM);

    mockMvc.perform(post("/transfer")
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        .content("""
            {
              "payerId": "9fdf94aa-a539-47bf-8858-dc5b99dc688c",
              "payeeId": "1da7659e-b0ad-47db-a60f-af2216446bed",
              "value": 125.40
            }
            """))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status").value("SUCCESS"))
        .andExpect(jsonPath("$.statusCode").value(200))
        .andExpect(jsonPath("$.statusText").value("OK"));
  }

  @Test
  void it_should_fail_if_payer_was_not_found() throws Exception {
    var payeeORM = new CustomerORM(UUID.fromString("1da7659e-b0ad-47db-a60f-af2216446bed"), "Jane Doe",
        CustomerType.COMMON, "79420473007", "jane.doe@gmail.com");
    var walletPayeeORM = new WalletORM(UUID.randomUUID(), payeeORM, BigDecimal.valueOf(1000.00));
    customerDAO.save(payeeORM);
    walletDAO.save(walletPayeeORM);

    mockMvc.perform(post("/transfer")
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        .content("""
            {
              "payerId": "9fdf94aa-a539-47bf-8858-dc5b99dc688c",
              "payeeId": "1da7659e-b0ad-47db-a60f-af2216446bed",
              "value": 125.40
            }
            """))
        .andExpect(status().isConflict())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status").value("ERROR"))
        .andExpect(jsonPath("$.statusCode").value(404))
        .andExpect(jsonPath("$.statusText").value("NOT_FOUND"))
        .andExpect(jsonPath("$.error.message").value("The payer does not exist in our records"))
        .andExpect(jsonPath("$.error.suggestion").value("Please check if the payer ID is correct"))
        .andExpect(jsonPath("$.error.timestamp").exists());
  }

  @Test
  void it_should_fail_if_payee_was_not_found() throws Exception {
    var payerORM = new CustomerORM(UUID.fromString("9fdf94aa-a539-47bf-8858-dc5b99dc688c"), "John Doe",
        CustomerType.COMMON, "81768869049", "john.doe@gmail.com");
    var walletPayerORM = new WalletORM(UUID.randomUUID(), payerORM, BigDecimal.valueOf(1000.00));
    customerDAO.save(payerORM);
    walletDAO.save(walletPayerORM);

    mockMvc.perform(post("/transfer")
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        .content("""
            {
              "payerId": "9fdf94aa-a539-47bf-8858-dc5b99dc688c",
              "payeeId": "1da7659e-b0ad-47db-a60f-af2216446bed",
              "value": 125.40
            }
            """))
        .andExpect(status().isConflict())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status").value("ERROR"))
        .andExpect(jsonPath("$.statusCode").value(404))
        .andExpect(jsonPath("$.statusText").value("NOT_FOUND"))
        .andExpect(jsonPath("$.error.message").value("The payee does not exist in our records"))
        .andExpect(jsonPath("$.error.suggestion").value("Please check if the payee ID is correct"))
        .andExpect(jsonPath("$.error.timestamp").exists());
  }

  @Test
  void it_should_fail_if_payer_is_a_shopkeeper() throws Exception {
    var payerORM = new CustomerORM(UUID.fromString("9fdf94aa-a539-47bf-8858-dc5b99dc688c"), "John Doe",
        CustomerType.SHOPKEEPER, "81768869049", "john.doe@gmail.com");
    var walletPayerORM = new WalletORM(UUID.randomUUID(), payerORM, BigDecimal.valueOf(1000.00));
    customerDAO.save(payerORM);
    walletDAO.save(walletPayerORM);

    var payeeORM = new CustomerORM(UUID.fromString("1da7659e-b0ad-47db-a60f-af2216446bed"), "Jane Doe",
        CustomerType.COMMON, "79420473007", "jane.doe@gmail.com");
    var walletPayeeORM = new WalletORM(UUID.randomUUID(), payeeORM, BigDecimal.valueOf(1000.00));
    customerDAO.save(payeeORM);
    walletDAO.save(walletPayeeORM);

    mockMvc.perform(post("/transfer")
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        .content("""
            {
              "payerId": "9fdf94aa-a539-47bf-8858-dc5b99dc688c",
              "payeeId": "1da7659e-b0ad-47db-a60f-af2216446bed",
              "value": 125.40
            }
            """))
        .andExpect(status().isConflict())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status").value("ERROR"))
        .andExpect(jsonPath("$.statusCode").value(409))
        .andExpect(jsonPath("$.statusText").value("CONFLICT"))
        .andExpect(jsonPath("$.error.message").value("The payer is a shopkeeper and therefore cannot make transfers"))
        .andExpect(jsonPath("$.error.timestamp").exists());
  }

  @Test
  void it_should_fail_if_payer_and_payee_are_the_same_person() throws Exception {
    mockMvc.perform(post("/transfer")
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        .content("""
            {
              "payerId": "9fdf94aa-a539-47bf-8858-dc5b99dc688c",
              "payeeId": "9fdf94aa-a539-47bf-8858-dc5b99dc688c",
              "value": 125.40
            }
            """))
        .andExpect(status().isConflict())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status").value("ERROR"))
        .andExpect(jsonPath("$.statusCode").value(409))
        .andExpect(jsonPath("$.statusText").value("CONFLICT"))
        .andExpect(jsonPath("$.error.message").value("The payer cannot transfer to himself"))
        .andExpect(jsonPath("$.error.suggestion").value("Please check if the payer and payee IDs are different"))
        .andExpect(jsonPath("$.error.timestamp").exists());
  }

  @Test
  void it_should_fail_if_payer_does_not_have_enough_balance() throws Exception {
    var payerORM = new CustomerORM(UUID.fromString("9fdf94aa-a539-47bf-8858-dc5b99dc688c"), "John Doe",
        CustomerType.COMMON, "81768869049", "john.doe@gmail.com");
    var walletPayerORM = new WalletORM(UUID.randomUUID(), payerORM, BigDecimal.valueOf(1000.00));
    customerDAO.save(payerORM);
    walletDAO.save(walletPayerORM);

    var payeeORM = new CustomerORM(UUID.fromString("1da7659e-b0ad-47db-a60f-af2216446bed"), "Jane Doe",
        CustomerType.COMMON, "79420473007", "jane.doe@gmail.com");
    var walletPayeeORM = new WalletORM(UUID.randomUUID(), payeeORM, BigDecimal.valueOf(1000.00));
    customerDAO.save(payeeORM);
    walletDAO.save(walletPayeeORM);

    mockMvc.perform(post("/transfer")
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        .content("""
            {
              "payerId": "9fdf94aa-a539-47bf-8858-dc5b99dc688c",
              "payeeId": "1da7659e-b0ad-47db-a60f-af2216446bed",
              "value": 3000
            }
            """))
        .andExpect(status().isConflict())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status").value("ERROR"))
        .andExpect(jsonPath("$.statusCode").value(409))
        .andExpect(jsonPath("$.statusText").value("CONFLICT"))
        .andExpect(jsonPath("$.error.message").value("The payer does not have enough balance to make the transfer"))
        .andExpect(jsonPath("$.error.suggestion").value("Please check if the payer has enough balance"))
        .andExpect(jsonPath("$.error.timestamp").exists());
  }
}
