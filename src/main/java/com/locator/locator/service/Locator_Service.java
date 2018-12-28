package com.locator.locator.service;

import com.locator.locator.common.Locator_Model;
import com.locator.locator.repo.Locator_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Locator_Service {

    @Autowired
    private Locator_Repo locatorRepo;

    public void saveLocator(Locator_Model locatorModel){
        locatorRepo.save(locatorModel);
    }
}
