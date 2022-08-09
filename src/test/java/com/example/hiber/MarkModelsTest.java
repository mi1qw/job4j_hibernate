package com.example.hiber;

import com.example.hiber.persistence.CRUDStore;
import com.example.hiber.tomany.Mark;
import com.example.hiber.tomany.MarkStore;
import com.example.hiber.tomany.Model;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j

public class MarkModelsTest {
    private static final CRUDStore<Mark> markStore = new MarkStore();

    @Test
    void oneToMany5MarksInModels() {
        Mark mercedesBenz = new Mark("Mercedes-Benz");
        Model maybach = new Model("S-Class Maybach");
        Model eClass = new Model("E-Class");
        Model amgSL = new Model("AMG SL");
        Model eqsSUV = new Model("EQS SUV");
        Model tClass = new Model("T-Class");
        mercedesBenz.add(maybach);
        mercedesBenz.add(eClass);
        mercedesBenz.add(amgSL);
        mercedesBenz.add(eqsSUV);
        mercedesBenz.add(tClass);
        markStore.add(mercedesBenz);

        assertThat(mercedesBenz.getModels())
                .hasSize(5)
                .extracting("name", String.class)
                .containsExactlyInAnyOrder(
                        "S-Class Maybach",
                        "E-Class",
                        "AMG SL",
                        "EQS SUV",
                        "T-Class"
                );
    }
}
