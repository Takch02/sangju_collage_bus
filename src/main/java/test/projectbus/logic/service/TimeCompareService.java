package test.projectbus.logic.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class TimeCompareService {

    private final TimeTransLocalTime timeTransLocalTime;

    public String compareTime(List<String> timeList) {
        List<LocalTime> timeSet = timeTransLocalTime.transformTime(timeList);
        LocalTime now = LocalTime.now();

        LocalTime limitTime = LocalTime.parse("22:10", DateTimeFormatter.ofPattern("HH:mm"));
        if (now.isAfter(limitTime)) {
            return "오늘은 운행이 끝남.";
        }

        Optional<Long> nextTime = timeSet.stream()
                .map(time -> ChronoUnit.MINUTES.between(now, time))
                .filter(diff -> diff > 0)
                .min(Long::compareTo);

        log.info("현재 남은 시간 : [{}]", nextTime);

        return nextTime.map(minutes -> minutes + "분").orElse("서버에 오류가 생긴듯");
    }
}
