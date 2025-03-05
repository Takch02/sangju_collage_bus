package test.projectbus.logic.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import test.projectbus.logic.repository.RegionRepository;
import test.projectbus.logic.service.TimeCompareService;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final RegionRepository regionRepository;
    private final TimeCompareService timeCompareService;

    @ModelAttribute(name = "regions")
    public List<String> regions() {
        return Arrays.asList("학교", "가미", "루이까스텔", "풍물거리");
    }

    @GetMapping("/")
    public String home() {
        return "home"; // home.html 렌더링
    }

    @GetMapping("/selectRegion")
    public String selectRegion(@RequestParam(value = "selectRegion", required = false) String selectRegion,
                               Model model) {
        if (selectRegion != null) {
            List<String> regionList = regionRepository.getReionList(selectRegion);
            String[] time = timeCompareService.compareTime(regionList);

            model.addAttribute("nextTime1", time[0]);
            model.addAttribute("nextTime2", time[1]);
            model.addAttribute("currentTime", currentTime());
            model.addAttribute("selectRegion", selectRegion);

            log.info("currentTime : [{}]", LocalTime.now());
            log.info("nextTime1 : [{}]", time[0]);
            log.info("nextTime2 : [{}]", time[1]);
        }
        return "selectRegion"; // selectRegion.html 렌더링
    }

    private static String currentTime() {
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return now.format(formatter);
    }

}