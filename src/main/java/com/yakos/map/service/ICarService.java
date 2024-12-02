package com.yakos.map.service;

import java.io.IOException;
import java.util.List;


import com.yakos.map.dto.DtoModel;

public interface ICarService {

	
	List<DtoModel> getModelOptions2(String ek_url,int a) throws IOException;
}
