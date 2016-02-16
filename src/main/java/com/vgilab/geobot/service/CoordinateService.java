
package com.vgilab.geobot.service;

import com.vgilab.geobot.persistence.entity.Coordinates;
import com.vgilab.geobot.persistence.repositories.CoordinateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 *
 * @author ljzhang
 */
@Service("coordinateService")
public class CoordinateService {
    
    @Autowired
    CoordinateRepository coordinateRepository;

    public Coordinates save(Coordinates coordinate) {
        return this.coordinateRepository.save(coordinate);
    }

    public Page<Coordinates> findAll(PageRequest pageRequest) {
        return this.coordinateRepository.findAll(pageRequest);
    }

    public long count() {
        return this.coordinateRepository.count();
    }

    public Coordinates findOne(Long id) {
        return this.coordinateRepository.findOne(id);
    }
}
