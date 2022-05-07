package com.example.SAPproject2.Repositories;

import com.example.SAPproject2.Entities.Packets;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

public interface PacketRepository extends CrudRepository<Packets, Integer> {

    @Transactional
    @Modifying
    @Query(value = " UPDATE Packets SET packetPrice = :percentage * packetPrice")
    void discount(@Param("percentage") BigDecimal percentage);

    @Transactional
    @Modifying
    @Query(value = " UPDATE Packets SET packetPrice = :percentage * packetPrice  + packetPrice")
    void raisePrices(@Param("percentage") BigDecimal percentage);
}