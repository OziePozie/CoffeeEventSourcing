package org.beauty.repositories;

import org.beauty.entity.Order;

public interface OrderStore {

    boolean save(Order order);

    Order findByID(Long id);


}
