package com.tejas.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tejas.binding.DashboardResponse;
import com.tejas.binding.EnquiryForm;
import com.tejas.binding.EnquirySearchCriteria;
import com.tejas.entity.StudentEnqEntity;
import com.tejas.service.EnquiryService;

@Controller

public class EnquiryController {

	@Autowired
	private HttpSession session;

	@Autowired
	private EnquiryService enqService;

	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {

		// ToDo: logic to fetch data for dashboard

		Integer userID = (Integer) session.getAttribute("userId");

		DashboardResponse dashboardData = enqService.getDashboardData(userID);

		model.addAttribute("dashboardData", dashboardData);

		return "dashboard";
	}

	@PostMapping("/addEnq")
	public String addEnquiry(@ModelAttribute("formObj") EnquiryForm formObj, Model model) {

		System.out.println(formObj);

		// ToDo : save the data

		boolean status = enqService.saveEnquiry(formObj);

		if (status) {
			model.addAttribute("succMsg", "Enquiry Added");
		} else {
			model.addAttribute("errMsg", "Problem Occured");
		}

		return "add-enquiry";

	}

	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {

		// Get courses for drop down
		List<String> courses = enqService.getCourses();

		// Get enquire Status for drop down
		List<String> enqStatuses = enqService.getEnqStatuses();

		// Create binding class object
		EnquiryForm formObj = new EnquiryForm();

		// Set data in model object
		model.addAttribute("courseNames", courses);
		model.addAttribute("statusNames", enqStatuses);
		model.addAttribute("formObj", formObj);

		return "add-enquiry";
	}

	@GetMapping("/enquiries")
	public String viewEnquiriesPage(EnquirySearchCriteria criteria, Model model) {
		initForm(model);
		List<StudentEnqEntity> enquiries = enqService.getEnquiries();
		model.addAttribute("enquiries", enquiries);
		return "view-enquiries";
	}

	private void initForm(Model model) {
		// get courses for drop down
		List<String> courses = enqService.getCourses();
		// get enquire status for drop down
		List<String> enqStatuses = enqService.getEnqStatuses();
		// create binding class obj
		EnquiryForm formObj = new EnquiryForm();
		// set data in model object
		model.addAttribute("courseNames", courses);
		model.addAttribute("statusNames", enqStatuses);

		model.addAttribute("formObj", formObj);
	}

	@GetMapping("/filter-enquiries")
	public String getFilteredEnqs(@RequestParam String cname, @RequestParam String status, @RequestParam String mode,
			Model model) {

		EnquirySearchCriteria criteria = new EnquirySearchCriteria();
		criteria.setCourseName(cname);
		criteria.setEnqStatus(status);
		criteria.setClassMode(mode);

		Integer userId = (Integer) session.getAttribute("userId");
		List<StudentEnqEntity> filteredEnqs = enqService.getFilteredEnqs(criteria, userId);

		model.addAttribute("enquiries", filteredEnqs);

		return "filter-enquiries-page";
	}

	@GetMapping("/logout")
	public String logout() {

		session.invalidate();

		return "index";

	}

}
