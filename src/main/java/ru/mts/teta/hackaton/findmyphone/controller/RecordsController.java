package ru.mts.teta.hackaton.findmyphone.controller;

import org.apache.tomcat.util.bcel.Const;
import org.springframework.format.annotation.DateTimeFormat;
import org.yaml.snakeyaml.scanner.Constant;
import ru.mts.teta.hackaton.findmyphone.config.Constants;
import ru.mts.teta.hackaton.findmyphone.exceptions.WrongPageNumberException;
import ru.mts.teta.hackaton.findmyphone.service.RecordService;
import ru.mts.teta.hackaton.findmyphone.domain.dto.RecordDto;
import ru.mts.teta.hackaton.findmyphone.domain.RecordsPage;

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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@Controller
@RequestMapping("/record")
public class RecordsController {

	@Autowired
	RecordService recordService;

	private List<String> getPagesNumbering(int openedPage, int totalPages) {
		ArrayList<String> pages = new ArrayList<String>();
		if (openedPage == 1) {
			pages.add("1");
			if (totalPages > 1) {
				pages.add("2");
				if (totalPages > 2) {
					pages.add("...");
				}
			}
			return pages;
		}
		if (openedPage - 1 != 1) {
			pages.add("1");
			pages.add("...");
		}
		for (int i=openedPage-1; i <= Math.min(totalPages, openedPage+1); ++i) {
			pages.add(String.valueOf(i));
		}
		if (openedPage + 1 < totalPages) {
			pages.add("...");	
			pages.add(String.valueOf(totalPages));
		}
		return pages;
	}

	@GetMapping
	public String getRecordsList(Model model,
							@RequestParam(name="s", required=false) String searchString,
							@RequestParam(name="page", required=false) Integer openedPage)
							throws WrongPageNumberException {
		if (searchString == null) {
			searchString = "";
		}
		if (openedPage == null) {
			openedPage = 0;
		} else {
			openedPage--;
		}
		System.out.println(searchString);
		RecordsPage recordsPage = recordService.getRecordsBySearchString(searchString, openedPage);
		List<RecordDto> records = recordsPage.getRecords();
		int totalPages = recordsPage.getTotalPages();
		if (totalPages != 0 && openedPage > totalPages-1) {
			throw new WrongPageNumberException();
		}
		if (totalPages != 0) {
			model.addAttribute("records", records);
		} else {
			model.addAttribute("records", List.of());
		}
		model.addAttribute("pages", this.getPagesNumbering(openedPage.intValue()+1, totalPages));
		model.addAttribute("s", searchString);
		return "records_list";
	}

	@GetMapping("/{recordId}")
	public String getRecord(Model model,
							@PathVariable("recordId") Long recordId) throws Exception{
		RecordDto recordDto = recordService.findById(recordId);
		model.addAttribute("record", recordDto);
		return "record_info";
	}

	@GetMapping("/map/{token}")
	public String getMap(Model model, @PathVariable("token") String token,
						 @RequestParam(value = "time_begin", required = false) @DateTimeFormat(pattern = Constants.timeFormat) LocalDateTime timeBegin,
						 @RequestParam(value = "time_end", required = false) @DateTimeFormat(pattern = Constants.timeFormat) LocalDateTime timeEnd) {
		if (timeBegin == null) {
			timeBegin = Constants.minTime;
		}
		if (timeEnd == null) {
			timeEnd = Constants.maxTime;
		}

		List<RecordDto> records = recordService.getRecords(token, timeBegin, timeEnd);

		model.addAttribute("metrics", records);
		model.addAttribute("gps_amount", records.size());
		return "map";
	}

	@ExceptionHandler
    public ModelAndView wrongPageNumberExceptionHandler(WrongPageNumberException ex) {
        ModelAndView modelAndView = new ModelAndView("redirect:/record");
        modelAndView.setStatus(HttpStatus.TEMPORARY_REDIRECT);
        return modelAndView;
    }
}

