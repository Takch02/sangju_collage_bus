package test.projectbus.logic.service;

import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class TimeTransLocalTime {

    /** HH:mm 형식으로 모두 바꾼 후 list에 넣는다.
     *  parameter 에 필요한 위치를 넣으면 된다.
     */

    public List<LocalTime> transformTime(List<String> timeList) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return timeList.stream().map(s -> LocalTime.parse(s, formatter)).toList();
    }
}