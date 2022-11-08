package com.furkanyilmaz.ui.api.Impl;

import com.google.gson.JsonElement;
import com.furkanyilmaz.business.services.IDailyService;
import com.furkanyilmaz.error.ApiResult;
import com.furkanyilmaz.ui.api.IDailyApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Lombok
@RequiredArgsConstructor

@RestController
@RequestMapping("gateway/daily")
public class DailyApiImpl implements IDailyApi {

    //Injection
    private final IDailyService dailyService;
    private static final String PATH = "gateway/daily";

    //http://localhost:5555/gateway/daily  ==> POST
    //SAVE
    @Override
    @PostMapping
    public ApiResult saveDaily(@RequestBody JsonElement jsonElement) {
        dailyService.dailySave(jsonElement);
        return new ApiResult(200, "Kayıt olundu", PATH);
    }

    //http://localhost:5555/gateway/daily  ==> GET
    @Override
    @GetMapping
    public ResponseEntity<List<?>> listDaily() {
        dailyService.dailyList();
        return ResponseEntity.ok(dailyService.dailyList());
    }

    //http://localhost:5555/gateway/daily/1
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDaily(@PathVariable(name="id") Long id) {
        return ResponseEntity.ok(dailyService.dailyFind(id));
    }

    //http://localhost:5555/gateway/daily/1  ==> DELETE
    @DeleteMapping("/{id}")
    @Override
    public ApiResult deleteDaily(@PathVariable(name="id") Long id) {
        return   new ApiResult(200, id+" nolu kayıt Silindi", PATH);
    }

    //http://localhost:5555/gateway/daily/update/1
    @Override
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDaily(@PathVariable(name="id") Long id, @RequestBody JsonElement jsonElement) {
        return ResponseEntity.ok( dailyService.dailyUpdate(id,jsonElement));
    }
}
