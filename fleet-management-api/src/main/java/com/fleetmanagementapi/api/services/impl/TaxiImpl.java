package com.fleetmanagementapi.api.services.impl;

import com.fleetmanagementapi.api.model.entities.Taxi;
import com.fleetmanagementapi.api.model.repository.TaxiRepositoryJPA;
import com.fleetmanagementapi.api.model.repository.TrajectoryRepository;
import com.fleetmanagementapi.api.services.ITaxi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class TaxiImpl implements ITaxi {

    @Autowired
    private TaxiRepositoryJPA taxiRepository;

    @Autowired
    private TrajectoryRepository trajectoryRepository;

    @Override
    public Page<Taxi> findAllTaxis(Pageable pageable) {
        return  taxiRepository.findAll(pageable);
    }


    @Override
    public Taxi save(Taxi taxi) {
        return taxiRepository.save(taxi);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        // Verificar si el taxi existe antes de eliminar
        if (taxiRepository.existsById(id)) {
            // Eliminar todas las trayectorias asociadas al taxi
            trajectoryRepository.deleteByTaxiId(id);

            // Eliminar el taxi
            taxiRepository.deleteById(id);
        }
    }

    @Override
    public boolean existTaxi(Integer id) {
        return taxiRepository.existsById(id);
    }
}
