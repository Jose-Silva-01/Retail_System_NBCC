package ca.nbcc.retailapp.service;


import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import ca.nbcc.retailapp.model.Report;
import ca.nbcc.retailapp.model.ReportCategory;
import ca.nbcc.retailapp.repo.ReportJpaRepo;

@Service
public class ReportService {
	private ReportJpaRepo rRepo;

	@Autowired
	public ReportService(ReportJpaRepo rRepo) {
		super();
		this.rRepo = rRepo;
	}
	
	public List<Report> sortReportsById(String reportType){
		return rRepo.findByRepCategoryOrderByIdAsc(reportType);
	}
	
	public List<Report> sortReportsByDate(String reportType){
		return rRepo.findByRepCategoryOrderByDateAsc(reportType);
	}
	
	public List<Report> sortReportsByEmployee(String reportType){
		return rRepo.findByRepCategoryOrderByEmployeeAsc(reportType);
	}
	
	public List<Report> sortReportsByParam(String param){
		//param must be a report's property
		List<Report> reportListSorted = rRepo.findAll(Sort.by(Sort.Direction.ASC, param));
		
		return reportListSorted;
	}
	
	public Report addNewReport(Report r) {
		return rRepo.save(r);
	}
	
	public List<Report> getAllReports() {
		return rRepo.findAll();
	}
	
	public Report getReportById(Long id) {
		if(rRepo.findById((long)id).isPresent()) {
			return rRepo.findById((long)id).get(); //getting the movie inside the repo
		}
		return null;
	}
	
	public List<ReportCategory> getReportsCategories(){
		List<ReportCategory> reportCategoriesList = new ArrayList<>(EnumSet.allOf(ReportCategory.class));
		return reportCategoriesList;
	}

	public Report findReportById(Long rMID_LONG) throws Exception {
		
		if(rRepo.findById(rMID_LONG).isPresent()) {
			return rRepo.findById(rMID_LONG).get();
		}
		else if (rRepo.findById(rMID_LONG).isEmpty()){
			throw new Exception(" Report not found: ID" + rMID_LONG);
		}
		
		return null;
	}
	
//	public String getMonthString(Report r) {
//		SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
//		String month_name = month_date.format(r.getDate());
//		return month_name;
//	}
}
