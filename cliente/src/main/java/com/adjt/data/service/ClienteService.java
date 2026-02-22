package com.adjt.data.service;

import com.adjt.data.repository.jpa.ClienteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ClienteService {

    @Inject
    ClienteRepository repository;
}
