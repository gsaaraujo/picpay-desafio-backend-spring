package com.gsa.picpaydesafiobackend.infra.dao;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.gsa.picpaydesafiobackend.infra.dao.orms.TransactionORM;

public interface TransactionDAO extends CrudRepository<TransactionORM, UUID> {

}
