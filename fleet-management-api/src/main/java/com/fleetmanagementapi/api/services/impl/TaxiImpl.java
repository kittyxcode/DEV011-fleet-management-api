package com.fleetmanagementapi.api.services.impl;

import com.fleetmanagementapi.api.model.entities.Taxi;
import com.fleetmanagementapi.api.model.entities.Trajectory;
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

        // Obtener el taxi que se va a eliminar
        Taxi taxi = taxiRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Taxi no encontrado con id: " + id));

        // Obtener todas las trayectorias asociadas a este taxi
        List<Trajectory> trajectories = trajectoryRepository.findByTaxiId(id);

        // Eliminar todas las trayectorias asociadas a este taxi
        trajectoryRepository.deleteAll(trajectories);

        // Borrar el taxi de la tabla de taxis
        taxiRepository.delete(taxi);
    }

    @Override
    public boolean existTaxi(Integer id) {
        return taxiRepository.existsById(id);
    }
}
