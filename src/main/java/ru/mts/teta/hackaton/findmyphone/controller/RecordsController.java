package ru.mts.teta.hackaton.findmyphone.controller;

import ru.mts.teta.hackaton.findmyphone.service.RecordService;
import ru.mts.teta.hackaton.findmyphone.domain.dto.RecordDto;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
@RequestMapping("/records")
public class RecordsController {

	@Autowired
	RecordService recordService;

	@GetMapping
	public String getRecordsList(Model model, @RequestParam(name="s", required=false) String searchString) {
		if (searchString == null) {
			searchString = "";
		}
		List<RecordDto> records = recordService.getRecordsBySearchString(searchString);
		model.addAttribute("records", records);
		return "records_list";
	}
}

