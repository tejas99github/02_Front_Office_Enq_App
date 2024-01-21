package com.tejas.service;

import java.util.List;

import com.tejas.binding.DashboardResponse;
import com.tejas.binding.EnquiryForm;
import com.tejas.binding.EnquirySearchCriteria;
import com.tejas.entity.StudentEnqEntity;

public interface EnquiryService {

	public DashboardResponse getDashboardData(Integer userId);

	public List<String> getCourses();

	public List<String> getEnqStatuses();

	public boolean saveEnquiry(EnquiryForm form);

	public List<StudentEnqEntity> getEnquiries();

	public List<StudentEnqEntity> getFilteredEnqs(EnquirySearchCriteria criteria, Integer userId);

	
}
