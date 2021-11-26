package com.springproject.estates.services;

import com.springproject.estates.domain.EstateModel;

import java.util.List;

public interface EstateServices {
    List<EstateModel> getAllEstate();
    void SaveEstate(EstateModel estates);
    void DeletEstate(long id);
    EstateModel FindEstate(long id);
    List<EstateModel> EstateIsntSale();



}
