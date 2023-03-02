package ca.nbcc.retailapp.controller;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ca.nbcc.retailapp.model.Order;
import ca.nbcc.retailapp.model.OrderReport;
import ca.nbcc.retailapp.model.Report;
import ca.nbcc.retailapp.model.ReportCategory;
import ca.nbcc.retailapp.service.OrderDetailsService;
import ca.nbcc.retailapp.service.ReportService;

@Controller
public class ReportController {
	
	ApplicationContext ctx;
	private ReportService rs;
	private OrderDetailsService ods;

	@Autowired
	public ReportController(ApplicationContext ctx, OrderDetailsService ods, ReportService rs) {
		super();
		this.ctx = ctx;
		this.ods = ods;
		this.rs = rs;
	}	
	
	@GetMapping("/showReports")
	public String toShowReports(Model model) {
		List<Report> reportList = rs.getAllReports();
		//System.out.println(reportList.toString());
		model.addAttribute("reportList", reportList);
		defaultPageDisplay(model);
		return "report-display";
	}
	
	@PostMapping("/searchReports")
	public String toSearchReports(Model model, @RequestParam("searchInput") String searchInput) {
		try {
			if(searchInput.isBlank() || searchInput.isEmpty()) {
				return "redirect:/showReports";
			}else {
				Long inputId = Long.parseLong(searchInput);
				List<Report> reportListSearchResult = new ArrayList<>();
				
				if(rs.getReportById(inputId) != null) {
					reportListSearchResult.add(rs.getReportById(inputId));
				}else {
					model.addAttribute("errorMessageSearch", "No Report found with ID #"+searchInput);
				}
				model.addAttribute("reportList", reportListSearchResult);
			}			
		}catch (Exception e) {
			model.addAttribute("errorMessageSearch", "No Report found with ID"+searchInput);
			model.addAttribute("reportList", new ArrayList<>());			
		}
		defaultPageDisplay(model);
		return "report-display";
	}
	
	@PostMapping("/filterReports")
	public String toShowReportsFiltered(Model model, @RequestParam("orderBy") String orderBy,
										@RequestParam("reportTypes") String reportTypes) {
		if(reportTypes.equals("0")) {
			List<Report> reportListSorted = rs.sortReportsByParam(orderBy);			
			model.addAttribute("reportList", reportListSorted);
		}else {
			List<Report> reportListFiltered = null;	
			
			switch (orderBy) {
				case "id":
					reportListFiltered = rs.sortReportsById(reportTypes);
					break;
				case "date":
					reportListFiltered = rs.sortReportsByDate(reportTypes);
					break;
				case "employee":
					reportListFiltered = rs.sortReportsByEmployee(reportTypes);
					break;
			}			
			model.addAttribute("reportList", reportListFiltered);
		}
		
		model.addAttribute("orderBySelected", orderBy);
		defaultPageDisplay(model);
		return "report-display";
	}
	
	@GetMapping("/reportDetails/{rId}")
	public String toReportDetails(Model model, 
								@PathVariable("rId") int rId) {
		
		Report reportToDisplay =  rs.getReportById((long)rId) ;
		
		// If it's a ORDER REPORT
		if(reportToDisplay.getRepCategory().equals("ORDER")) {	
			OrderReport orderReportToDisplay = (OrderReport) rs.getReportById((long)rId);
			Order orderOfReport = orderReportToDisplay.getOrder();
			model.addAttribute("orderOfReport", orderOfReport);
			model.addAttribute("orderSubTotal", ods.getOrderSubTotalToDisplay(orderOfReport.getOrdDetails()));
			model.addAttribute("orderTaxes", ods.getOrderTaxesToDisplay(orderOfReport.getOrdDetails()));
			model.addAttribute("orderTotal", ods.getOrderTotalToDisplay(orderOfReport.getOrdDetails()));
			model.addAttribute("totalList", ods.getTotalListToDisplay(orderOfReport.getOrdDetails()));
		
		} else if (reportToDisplay.getRepCategory().equals("INVENTORY")) {
			
		}
		
		model.addAttribute("reportToDisplay", reportToDisplay);
		
		return "report-details";
	}
	
	private void defaultPageDisplay(Model model) {
		List<ReportCategory> reportCategoryList = rs.getReportsCategories();
		model.addAttribute("categoryList", reportCategoryList);
	}	
}
