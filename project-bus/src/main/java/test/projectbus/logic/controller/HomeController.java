package test.projectbus.logic.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import test.projectbus.logic.repository.RegionRepository;
import test.projectbus.logic.service.TimeCompareService;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final RegionRepository regionRepository;
    private final TimeCompareService timeCompareService;
    /**
     * 지역 추가하고 model 에 넣어 html 에 보내준다.
     */
    @ModelAttribute(name = "regions")
    public Map<String, String> regions() {

        Map<String, String> regions = new LinkedHashMap<>();
        regions.put("학교", "school");
        regions.put("가미", "gami");
        regions.put("루이까스텔", "loi");
        regions.put("풍물거리", "poung");
        return regions;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    /**
     * selectRegion 은 영어로 된 지역을 반환한다.
     */
    @PostMapping("/")
    public String selectRegion(@RequestParam("selectRegion") String selectRegion, Model model) {

        List<String> reionList = regionRepository.getReionList(selectRegion);
        String nextTime = timeCompareService.compareTime(reionList);

        model.addAttribute("nextTime", nextTime);
        model.addAttribute("currentTime", currentTime());

        log.info("currentTime : [{}]", LocalTime.now());
        log.info("nextTime : [{}]", nextTime);

        return "selectRegion";
    }

    private static String currentTime() {

        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return now.format(formatter);
    }
}
