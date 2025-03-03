package test.projectbus;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import test.projectbus.logic.service.TimeCompareService;

import static test.projectbus.logic.repository.TimeTable.school_front;

@SpringBootTest
public class timeTransTest {

    @Autowired
    private TimeCompareService timeCompareService;

    @Test
    void test01() {

        System.out.println("current : " + timeCompareService.compareTime(school_front));
    }
}
