package test.projectbus.logic.repository;

import org.springframework.stereotype.Repository;
import test.projectbus.logic.repository.TimeTable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RegionRepository {

    private final Map<String, List<String>> regionTimeMap = new HashMap<>();

    // 생성자에서 초기화
    public RegionRepository() {
        regionTimeMap.put("school", TimeTable.school_front);
        regionTimeMap.put("gami", TimeTable.gami_front);
        regionTimeMap.put("loi", TimeTable.loi_front);
        regionTimeMap.put("poung", TimeTable.poung_front);
    }


    public List<String> getReionList (String region) {

        return regionTimeMap.get(region);
    }
}
