package com.devsuperior.bds02.services;

import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.services.exceptions.DataBaseException;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityRepository repository;

    @Transactional(readOnly = true)
    public List<CityDTO> findAll(){
        return repository.findAll(Sort.by("name")).stream().map(c -> new CityDTO(c)).collect(Collectors.toList());
    }

    @Transactional
    public CityDTO insert(CityDTO dto){
        City city = new City(null, dto.getName());
        city = repository.save(city);
        return new CityDTO(city);
    }


    public void delete(Long id){
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Entity not find by id "+id);
        } catch (DataIntegrityViolationException d){
            throw new DataBaseException("Entity cannot be deleted");
        }
    }
}
