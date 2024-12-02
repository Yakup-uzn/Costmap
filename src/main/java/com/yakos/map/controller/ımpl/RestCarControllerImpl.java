package com.yakos.map.controller.ımpl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yakos.map.controller.IRestCarController;
import com.yakos.map.dto.DtoModel;
import com.yakos.map.service.ımpl.CarServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping(path = "/car")
public class RestCarControllerImpl implements IRestCarController {

	private final CarServiceImpl carService ;

	public RestCarControllerImpl(CarServiceImpl carService){
		this.carService=carService;
		
	}
	
	
	@GetMapping("/marka/model")
	public List<DtoModel> getModelOptions2(@RequestParam String url, @RequestParam int id) throws IOException {
	    return carService.getModelOptions2(url, id);
	}
	
	
	
	
	 @GetMapping("/car-data")
	 public List<Map<String, Object>> getCarData(@RequestParam String url) throws IOException {
	        return carService.scrapeCarData(url);
	    }
	
	


	
}
