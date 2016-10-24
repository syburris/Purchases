package com.YoungMoney;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by stevenburris on 10/24/16.
 */
public interface CustomerRepo extends CrudRepository<Customer, Integer> {
}
