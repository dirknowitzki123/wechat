package com.by.frame.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.by.frame.mapper.HomePageMapper;
import com.by.frame.service.IHomePageService;

@Service
public class HomePageServiceImpl implements IHomePageService{
	
	@SuppressWarnings("unused")
	@Autowired private HomePageMapper homePageMapper;
	
	
	
}
