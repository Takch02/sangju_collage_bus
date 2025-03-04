package test.projectbus.logic.controller;

import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import test.projectbus.logic.repository.RegionRepository;
import test.projectbus.logic.service.TimeCompareService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Slf4j
@RestController
@RequiredArgsConstructor
public class RestFulApiController {

    private final RegionRepository regionRepository;
    private final TimeCompareService timeCompareService;

    /*
     * post 형식으로 받은 데이터를 ResponseEntity에 넣어 Post 형식으로 보냄.
     * 값 2개를 map 형식으로 보냄.
     */

     @PostMapping(produces = "application/json", path = "/api")
     public ResponseEntity<Map<String, Object>> PostApi(@RequestBody Map<String, String> request) {
        
        log.info("작동 시작");
         String selectRegion = request.get("selectRegion"); // JSON에서 추출
         log.info("selectRegion : [{}]", selectRegion);
         List<String> reionList = regionRepository.getReionList(selectRegion);
         String nextTime = timeCompareService.compareTime(reionList);
     
         Map<String, Object> response = new HashMap<>();
         response.put("nextTime", nextTime);
         response.put("selectRegion", selectRegion);

         log.info("nextTime : [{}]", nextTime);
         log.info("selectRegion : [{}]", selectRegion);
     
         return ResponseEntity.ok(response);
     }
    
}
