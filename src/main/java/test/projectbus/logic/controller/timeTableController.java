package test.projectbus.logic.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import test.projectbus.logic.repository.RegionRepository;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/timeTable")
@RequiredArgsConstructor
public class timeTableController {

    private final RegionRepository regionRepository;

    @GetMapping("/{selectRegion}")
    public String timeTable(@PathVariable String selectRegion, Model model) {

        List<String> regionList = regionRepository.getReionList(selectRegion);

        model.addAttribute("regionList", regionList);
        return "timeTable";
    }
}
