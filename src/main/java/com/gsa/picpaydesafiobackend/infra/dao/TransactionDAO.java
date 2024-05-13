package com.gsa.picpaydesafiobackend.infra.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsa.picpaydesafiobackend.infra.dao.orms.TransactionORM;

public interface TransactionDAO extends JpaRepository<TransactionORM, UUID> {
}
