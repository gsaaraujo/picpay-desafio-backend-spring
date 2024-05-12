package com.gsa.picpaydesafiobackend.infra.dao;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.gsa.picpaydesafiobackend.infra.dao.orms.CustomerORM;

public interface CustomerDAO extends CrudRepository<CustomerORM, UUID> {

}
