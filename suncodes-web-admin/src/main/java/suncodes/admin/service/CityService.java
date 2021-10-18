package suncodes.admin.service;

import suncodes.admin.bean.City;

public interface CityService {

     City getById(Long id);

     void saveCity(City city);

}
