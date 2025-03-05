package test.projectbus.logic.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class TimeCompareService {

    private final TimeTransLocalTime timeTransLocalTime;

    public String[] compareTime(List<String> timeList) {

        List<LocalTime> timeSet = timeTransLocalTime.transformTime(timeList);
        LocalTime now = LocalTime.now();

        LocalTime limitTime = LocalTime.parse("22:10", DateTimeFormatter.ofPattern("HH:mm"));
        if (now.isAfter(limitTime)) {
            return null;
        }

        List<Long> differences = timeSet.stream()
                .map(time -> ChronoUnit.MINUTES.between(now, time))
                .filter(diff -> diff > 0)
                .sorted()
                .toList();

        Optional<Long> firstTime = differences.isEmpty() ? Optional.empty() : Optional.of(differences.get(0));
        Optional<Long> secondTime = differences.size() > 1 ? Optional.of(differences.get(1)) : Optional.empty();


        String nextTime1 = firstTime.map(this::formattingTime).orElse("서버에 오류가 생긴듯");
        String nextTime2 = secondTime.map(this::formattingTime).orElse("서버에 오류가 생긴듯");

        String[] result = {nextTime1, nextTime2};

        log.info("현재 남은 시간 : [{}]", nextTime1);
        log.info("현재 남은 시간 : [{}]", nextTime2);

        return result;
    }

    /**
     * Long 을 String 으로 x시간 xx분 으로 포멧시킴.
     */
    private String formattingTime(Long minutes) {

        if (minutes < 60) {
            return minutes + "분";
        } else {
            long hours = minutes / 60;
            long remainingMinutes = minutes % 60;
            return hours + "시간 " + remainingMinutes + "분";
        }
    }
}
