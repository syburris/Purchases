package com.YoungMoney;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by stevenburris on 10/24/16.
 */
public interface PurchasesRepo extends CrudRepository<Purchase, Integer> {
    List<Purchase> findByCategory(String category);
}
