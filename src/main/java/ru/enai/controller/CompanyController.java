package ru.enai.controller;

import org.springframework.web.bind.annotation.*;
import ru.enai.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/company")
public class CompanyController {
    private Long counter = 4L;
    private List<Map<String, String>> companys = new ArrayList<Map<String, String>>()
    {{
        add(new HashMap<String, String>() {{put("id", "1"); put("text", "EPAM"); put("value", "1200");}});
        add(new HashMap<String, String>() {{put("id", "2"); put("text", "GEMBL"); put("value", "1500");}});
        add(new HashMap<String, String>() {{put("id", "3"); put("text", "EGAR"); put("value", "1100");}});
    }};

    @GetMapping
    public List<Map<String, String>>  list() {
        return companys;
    }

    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id) {
        return getCompany(id);
    }

    private Map<String, String> getCompany(String id) {
        return companys.stream()
                .filter(company -> company.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> company) {
        company.put("id", String.valueOf(counter++));
        companys.add(company);
        return company;
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> company) {
        Map<String, String> companyFromDB = getCompany(id);
        companyFromDB.putAll(company);
        companyFromDB.put("id", id);
        return companyFromDB;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        Map<String, String> company = getCompany(id);
        companys.remove(company);
    }
}
