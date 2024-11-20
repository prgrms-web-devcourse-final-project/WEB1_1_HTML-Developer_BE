package com.backend.allreva.concert.command.application.util;

import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class CsvUtil {

    public static List<String> readConcertHallIds() {
        ClassLoader classLoader = CsvUtil.class.getClassLoader();
        File file = new File(
                Objects.requireNonNull(classLoader.getResource("kopis/performance_hall_list.csv")).getFile());
        List<String> ids = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
            String[] line;
            csvReader.readNext();

            // 각 id를 읽고 출력
            while ((line = csvReader.readNext()) != null) {
                String id = line[0];
                ids.add(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("hallId를 불러올 수 없습니다.");
        }

        return ids;
    }
}
