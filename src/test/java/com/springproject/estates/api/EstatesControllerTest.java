package com.springproject.estates.api;
import static org.assertj.core.api.Assertions.assertThat;
import com.springproject.estates.domain.EstateModel;
import com.springproject.estates.services.EstateServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;





@SpringBootTest
class EstatesControllerTest {

    @Autowired
    EstateServices estateServices;

    @Test
    void addEstate() {
        //given
        EstateModel savedestate = new EstateModel("flat",80,6);
        //when
        estateServices.SaveEstate(savedestate);
        //then
        assertThat(savedestate.getId()).isGreaterThan(0);
    }

    @Test
    void deletEstate() {
        //given
        EstateModel savedestate = new EstateModel("flat",80,6);
        EstateModel estateModel=estateServices.SaveEstate(savedestate);
        EstateModel findestate=estateServices.FindEstate(estateModel.getId());

        //when
        estateServices.DeletEstate(findestate.getId());
        EstateModel deletedestate = estateServices.FindEstate(findestate.getId());

        //then
        assertThat(deletedestate).isNull();
    }

    @Test

    void updateEstate() {
        //given
        EstateModel savedestate = new EstateModel("flat",80,6);
        EstateModel estateModel=estateServices.SaveEstate(savedestate);
        EstateModel estatefind=estateServices.FindEstate(estateModel.getId());


        //when
        estatefind.setName("house");
        estatefind.setPrice(90);
        estatefind.setNumberShares(80);
        estateServices.SaveEstate(estatefind);
        EstateModel updateestateModel=estateServices.FindEstate(estatefind.getId());

        //then
        assertThat(updateestateModel.getPrice()).isEqualTo(90);
        assertThat(updateestateModel.getName()).isEqualTo("house");
        assertThat(updateestateModel.getNumberShares()).isEqualTo(80);
    }
}





/*****
 this.mockMvc.perform(get("/estate")).andDo(print())
 .andExpect(status().isOk());
 *///////////