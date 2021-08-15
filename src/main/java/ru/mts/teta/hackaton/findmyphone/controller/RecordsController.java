package ru.mts.teta.hackaton.findmyphone.controller;

import ru.mts.teta.hackaton.findmyphone.exceptions.WrongPageNumberException;
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
import java.util.ArrayList;

@Controller
@RequestMapping("/record")
public class RecordsController {

	@Autowired
	RecordService recordService;

	private List<String> getPagesNumbering(Long openedPage, Long totalPages) {
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
		for (long i=openedPage-1; i < Math.min(totalPages.longValue(), openedPage.longValue()+1); ++i) {
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
							@RequestParam(name="page", required=false) Long page)
							throws WrongPageNumberException {
		if (searchString == null) {
			searchString = "";
		}
		if (page == null) {
			page = 0L;
		} else {
			page--;
		}
		Long openedPage = page;
		List<RecordDto> records = recordService.getRecordsBySearchString(searchString, page);
		if (page.longValue() != 0)
			page = Long.valueOf(page.longValue() / 100 + (page.longValue() % 100 != 0 ? 1 : 0));
		else 
			page = 1L;
		if (openedPage > page) {
			throw new WrongPageNumberException();
		}
		model.addAttribute("records", records);
		model.addAttribute("pages", this.getPagesNumbering(openedPage+1, page));
		model.addAttribute("s", searchString);
		return "records_list";
	}


	@ExceptionHandler
    public ModelAndView wrongPageNumberExceptionHandler(WrongPageNumberException ex) {
        ModelAndView modelAndView = new ModelAndView("redirect:/record");
        modelAndView.setStatus(HttpStatus.TEMPORARY_REDIRECT);
        return modelAndView;
    }
}

