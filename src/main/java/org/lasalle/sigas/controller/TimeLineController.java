package org.lasalle.sigas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("timeline")
public class TimeLineController {

	@RequestMapping(method = RequestMethod.GET)
	public String timeline() {
		return "timeline/TimeLineSys";
	}
}
