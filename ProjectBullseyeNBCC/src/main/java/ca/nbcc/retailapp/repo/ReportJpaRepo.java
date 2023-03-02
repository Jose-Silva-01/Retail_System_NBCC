package ca.nbcc.retailapp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.nbcc.retailapp.model.Report;

public interface ReportJpaRepo extends JpaRepository<Report, Long>{
	
	public List<Report> findByRepCategoryOrderByIdAsc(String repCategory);
	public List<Report> findByRepCategoryOrderByDateAsc(String repCategory);
	public List<Report> findByRepCategoryOrderByEmployeeAsc(String repCategory);
}
