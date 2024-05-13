package com.gsa.picpaydesafiobackend.infra.controllers.gettransactionsbycustomerid;

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
import com.gsa.picpaydesafiobackend.infra.dao.orms.TransactionORM;

@SpringBootTest
@AutoConfigureMockMvc
public class GetTransactionsControllerTest {
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
  void it_should_get_transactions() throws Exception {
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

    var transactionORM = new TransactionORM(UUID.fromString("d0ba89f7-9f6b-4f94-b2df-79d77514a668"), payerORM, payeeORM,
        BigDecimal.valueOf(125.40));
    transactionDAO.save(transactionORM);

    mockMvc.perform(get("/transactions")
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status").value("SUCCESS"))
        .andExpect(jsonPath("$.statusCode").value(200))
        .andExpect(jsonPath("$.statusText").value("OK"))
        .andExpect(jsonPath("$.data[0].transactionId").value("d0ba89f7-9f6b-4f94-b2df-79d77514a668"))
        .andExpect(jsonPath("$.data[0].payeeId").value("9fdf94aa-a539-47bf-8858-dc5b99dc688c"))
        .andExpect(jsonPath("$.data[0].payerId").value("1da7659e-b0ad-47db-a60f-af2216446bed"))
        .andExpect(jsonPath("$.data[0].value").value(125.40));
  }
}