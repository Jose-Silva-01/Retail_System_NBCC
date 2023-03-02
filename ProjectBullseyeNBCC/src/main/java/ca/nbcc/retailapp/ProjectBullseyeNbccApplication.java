package ca.nbcc.retailapp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;
import javax.validation.ConstraintViolation;
import javax.validation.Path.Node;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ca.nbcc.retailapp.controller.InventoryController;
import ca.nbcc.retailapp.controller.StoresController;
import ca.nbcc.retailapp.controller.LoginController;
import ca.nbcc.retailapp.controller.OrderController;
import ca.nbcc.retailapp.model.Department;
import ca.nbcc.retailapp.model.Employee;
import ca.nbcc.retailapp.model.Inventory;
import ca.nbcc.retailapp.model.JobRole;
import ca.nbcc.retailapp.model.Order;
import ca.nbcc.retailapp.model.Product;
import ca.nbcc.retailapp.model.ProductCategory;
import ca.nbcc.retailapp.model.Store;
import ca.nbcc.retailapp.model.Suplier;
import ca.nbcc.retailapp.repo.DepartmentJpaRepo;
import ca.nbcc.retailapp.repo.EmployeeJpaRepo;
import ca.nbcc.retailapp.repo.InventoryJpaRepo;
import ca.nbcc.retailapp.repo.OrderJpaRepo;
import ca.nbcc.retailapp.repo.ProductJpaRepo;
import ca.nbcc.retailapp.repo.StoreJpaRepo;
import ca.nbcc.retailapp.repo.SupplierJpaRepo;
import ca.nbcc.retailapp.repo.OrderDetailsJpaRepo;
import ca.nbcc.retailapp.repo.ReportJpaRepo;
import ca.nbcc.retailapp.service.DepartmentService;
import ca.nbcc.retailapp.service.InventoryService;
import ca.nbcc.retailapp.service.OrderService;
import ca.nbcc.retailapp.service.ProductService;
import ca.nbcc.retailapp.service.ReportService;
import ca.nbcc.retailapp.service.StoreService;
import ca.nbcc.retailapp.service.SupplierService;
import ca.nbcc.retailapp.service.LoginService;
import ca.nbcc.retailapp.service.OrderDetailsService;

@SpringBootApplication
public class ProjectBullseyeNbccApplication {

	private static ProductJpaRepo pRepo;
	private static SupplierJpaRepo sRepo;
	private static InventoryJpaRepo iRepo;
	private static StoreJpaRepo stoRepo;
	private static DepartmentJpaRepo depRepo;
	private static OrderJpaRepo oRepo;
	private static EmployeeJpaRepo er;
	private static EmployeeJpaRepo ls;
	private static ReportJpaRepo rRepo;
	private static OrderDetailsJpaRepo odRepo;
	private static DepartmentJpaRepo dRepo;

	@Autowired
	public ProjectBullseyeNbccApplication(ProductJpaRepo pRepo, SupplierJpaRepo sRepo, InventoryJpaRepo iRepo,
			StoreJpaRepo stoRepo, OrderJpaRepo oRepo, OrderDetailsJpaRepo odRepo, ReportJpaRepo rRepo,
			EmployeeJpaRepo er, EmployeeJpaRepo ls, DepartmentJpaRepo dRepo) {
		ProjectBullseyeNbccApplication.pRepo = pRepo;
		ProjectBullseyeNbccApplication.sRepo = sRepo;
		ProjectBullseyeNbccApplication.iRepo = iRepo;
		ProjectBullseyeNbccApplication.stoRepo = stoRepo;
		ProjectBullseyeNbccApplication.oRepo = oRepo;
		ProjectBullseyeNbccApplication.er = er;
		ProjectBullseyeNbccApplication.odRepo = odRepo;
		ProjectBullseyeNbccApplication.rRepo = rRepo;
		ProjectBullseyeNbccApplication.ls = ls;
		ProjectBullseyeNbccApplication.dRepo = dRepo;

	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ProjectBullseyeNbccApplication.class, args);

		// DB CONNECTION

		/*
		 * ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		 * Validator v = factory.getValidator();
		 * 
		 * BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); String pwd =
		 * encoder.encode("123456");
		 * 
		 * Employee eNULL = new Employee(null, pwd);
		 * 
		 * Set<ConstraintViolation<Employee>> violations = v.validate(eNULL);
		 * System.out.println(); System.out.println("Validation Problems: ");
		 * for(ConstraintViolation<Employee> cv : violations) { System.out.println("" +
		 * eNULL + ": "); String field = null; for(Node node : cv.getPropertyPath()) {
		 * field = node.getName(); } System.out.println("-PROBLEM: " + cv.getMessage());
		 * System.out.println("-FIELD: " + field); }
		 * 
		 * BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); String
		 * password ="123456"; String encodedPassword =
		 * passwordEncoder.encode(password); System.out.println();
		 * System.out.println("Password is  : " + password);
		 * System.out.println("Encoded password is : " + encodedPassword);
		 * System.out.println();
		 * 
		 * boolean isPasswordMatch = passwordEncoder.matches(password, encodedPassword);
		 * System.out.println("Password : " + password + "   isPasswordMatch    : " +
		 * isPasswordMatch);
		 * 
		 * password = "yawinhgdgdhg"; isPasswordMatch =
		 * passwordEncoder.matches(password, encodedPassword);
		 * System.out.println("Password : " + password +
		 * "           isPasswordMatch    : " + isPasswordMatch);
		 */

		/****************** TEST - ADDING ELEMENTS TO THE DATABASE ******************/

		// Creating a test Inventory Controller
    
		InventoryController inventoryController = new InventoryController(new ProductService(pRepo),
				new SupplierService(sRepo), new InventoryService(iRepo), new StoreService(stoRepo));

		// Creating a test Store Controller
		StoresController storeController = new StoresController(new StoreService(stoRepo),
				new DepartmentService(dRepo));

		// test employee controller
		LoginController testLoginController = new LoginController(new LoginService(er));

		// Adding stores to the database
		List<Store> stores = new ArrayList<>();
		stores.add(new Store("Bullseye", "Mountain Road", "Moncton", "(506)888-2235", false));
		stores.add(new Store("Bullseye", "Union Street", "Saint John", "(506)888-2235", false));
		stores.add(new Store("Bullseye", "Main Street", "Dieppe", "(506)888-2235", false));
		stores.add(new Store("Bullseye", "Clark Street", "Oromocto", "(506)888-2235", false));
		stores.add(new Store("Bullseye", "Smyhte Stree", "Fredericton", "(506)888-2235", false));
		stores.add(new Store("Bullseye", "John Street", "Miramichi", "(506)888-2235", false));
		stores.add(new Store("Bullseye", "Main Street", "Sussex", "(506)888-2235", false));
		stores.add(new Store("Bullseye", "Archibald Street", "Moncton", "(506)888-2235", true));

		Store store1 = storeController.addStoreTest(stores.get(0));
		Store store2 = storeController.addStoreTest(stores.get(1));
		Store store3 = storeController.addStoreTest(stores.get(2));
		Store store4 = storeController.addStoreTest(stores.get(3));
		Store store5 = storeController.addStoreTest(stores.get(4));
		Store store6 = storeController.addStoreTest(stores.get(5));
		Store store7 = storeController.addStoreTest(stores.get(6));
		Store store8 = storeController.addStoreTest(stores.get(7));

		// Adding departments to the database
		List<Department> departments = new ArrayList<>();
		departments.add(new Department("Administration", store1));
		departments.add(new Department("Marketing and sales", store1));
		departments.add(new Department("Human resources", store1));
		departments.add(new Department("Customer service", store1));
		departments.add(new Department("Accounting and finance", store1));
		departments.add(new Department("Research and development", store1));
		departments.add(new Department("Information Technology", store1));
		departments.add(new Department("Administration", store2));
		departments.add(new Department("Marketing and sales", store2));
		departments.add(new Department("Human resources", store2));
		departments.add(new Department("Customer service", store2));
		departments.add(new Department("Accounting and finance", store2));
		departments.add(new Department("Research and development", store2));
		departments.add(new Department("Information Technology", store2));

		Department dept1 = storeController.addDepartmentTest(departments.get(0));
		Department dept2 = storeController.addDepartmentTest(departments.get(1));
		Department dept3 = storeController.addDepartmentTest(departments.get(2));
		Department dept4 = storeController.addDepartmentTest(departments.get(3));
		Department dept5 = storeController.addDepartmentTest(departments.get(4));
		Department dept6 = storeController.addDepartmentTest(departments.get(5));
		Department dept7 = storeController.addDepartmentTest(departments.get(6));
		Department dept8 = storeController.addDepartmentTest(departments.get(7));
		Department dept9 = storeController.addDepartmentTest(departments.get(8));
		Department dept10 = storeController.addDepartmentTest(departments.get(9));
		Department dept11 = storeController.addDepartmentTest(departments.get(10));
		Department dept12 = storeController.addDepartmentTest(departments.get(11));
		Department dept13 = storeController.addDepartmentTest(departments.get(12));
		Department dept14 = storeController.addDepartmentTest(departments.get(13));

		// adding employees to database
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String pwd = encoder.encode("123456");

		testLoginController.addEmployeeTest(new Employee("kateAdmin", pwd, JobRole.STORE_MANAGER, "Kate", "Daley",
				"(506)900-3245", "kate@gmail.com", "Fredericton, Street No. ...", dept7, null));
		testLoginController.addEmployeeTest(new Employee("anaAdmin", pwd, JobRole.STORE_MANAGER, "Ana", "Silva",
				"(506)900-4655", "ana@gmail.com", "Moncton, Street No. ...", dept7, null));
		testLoginController.addEmployeeTest(new Employee("joseAdmin", pwd, JobRole.STORE_MANAGER, "Jose", "Silva",
				"(506)976-0908", "jose@gmail.com", "Dieppe, Street No. ...", dept14, null));
		testLoginController.addEmployeeTest(new Employee("kyleEmployee", pwd, JobRole.STORE_EMPLOYEE, "Kyle", "Silva",
				"(506)976-0908", "kyle@gmail.com", "Toronto, Street No. ...", dept11, null));

		// Adding a supplier to the database
		List<Suplier> supliers = new ArrayList<>();

		supliers.add(new Suplier("Corner123", "USA, Los Angeles"));
		supliers.add(new Suplier("Sunsky", "US, EU and China"));
		supliers.add(new Suplier("Bargain Wholesale", "USA, Los Angeles"));
		supliers.add(new Suplier("Empire Discount", "England"));
		supliers.add(new Suplier("ReGo", "Canada"));
		supliers.add(new Suplier("Fashion Square", "USA, Los Angeles"));
		supliers.add(new Suplier("Dorfin", "Canada"));
		supliers.add(new Suplier("Nexday", "USA, Los Angeles"));
		supliers.add(new Suplier("Faire", "Canada, Toronto"));
		supliers.add(new Suplier("Wordans", "Canada, Vancouver"));
		supliers.add(new Suplier("Jerico", "Canada"));
		supliers.add(new Suplier("FlagHouse", "Canada, Calgary"));

		Suplier suplier1 = inventoryController.addSupplierTest(supliers.get(0));
		Suplier suplier2 = inventoryController.addSupplierTest(supliers.get(1));
		Suplier suplier3 = inventoryController.addSupplierTest(supliers.get(2));
		Suplier suplier4 = inventoryController.addSupplierTest(supliers.get(3));
		Suplier suplier5 = inventoryController.addSupplierTest(supliers.get(4));
		Suplier suplier6 = inventoryController.addSupplierTest(supliers.get(5));
		Suplier suplier7 = inventoryController.addSupplierTest(supliers.get(6));
		Suplier suplier8 = inventoryController.addSupplierTest(supliers.get(7));
		Suplier suplier9 = inventoryController.addSupplierTest(supliers.get(8));
		Suplier suplier10 = inventoryController.addSupplierTest(supliers.get(9));
		Suplier suplier12 = inventoryController.addSupplierTest(supliers.get(10));
		Suplier suplier13 = inventoryController.addSupplierTest(supliers.get(11));
		
		//Adding a product to the database
		List<Product> products = new ArrayList<>();

		products.add(new Product("Mountain Bike", ProductCategory.SPORT_EQUIPMENT, "Bicycle to use on Mountains", 19, 2,
				10, 4, "Blue", 599.99, suplier1));
		products.add(new Product("Baseball Bat", ProductCategory.SPORT_EQUIPMENT, "Essential for playing baseball", 20,
				0.4, 1, 0.8, "Wood", 199.99, suplier2));
		products.add(new Product("Baseball Gloves", ProductCategory.ACESSORIES, "Gloves for baseball players", 20, 0.4,
				0.5, 0.8, "Grey", 39.99, suplier3));
		products.add(new Product("Basketball", ProductCategory.SPORT_EQUIPMENT, "Balls specific for basketball", 20,
				0.6, 1, 0.8, "Orange", 29.99, suplier1));
		products.add(new Product("Shoulder Pads", ProductCategory.SPORT_EQUIPMENT, "Used to protect shoulders", 20, 0.1,
				0.2, 0.3, "White", 19.99, suplier2));
		products.add(new Product("Knee Pads", ProductCategory.SPORT_EQUIPMENT, "Used to protect knee", 20, 0.1, 0.2,
				0.3, "White", 19.99, suplier3));
		products.add(new Product("Thigh pads", ProductCategory.SPORT_EQUIPMENT, "Used to protect thigh", 20, 0.1, 0.2,
				0.3, "White", 19.99, suplier4));
		products.add(new Product("Helmet", ProductCategory.ACESSORIES, "Used to protect the head from impacts", 20, 0.5,
				0.8, 0.8, "Black", 49.99, suplier3));
		products.add(new Product("Gloves", ProductCategory.CLOTHING, "Gloves for adults", 20, 0.5, 0.5, 0.7, "Grey",
				29.99, suplier5));
		products.add(new Product("Bag", ProductCategory.ACESSORIES, "Bag to use on every occasion", 20, 1, 0.5, 1.5,
				"Blue", 49.99, suplier6));
		products.add(new Product("Nets", ProductCategory.SPORT_EQUIPMENT, "Wide nets for playing many sports", 20, 3,
				0.4, 0.5, "Blue", 19.99, suplier6));
		products.add(new Product("Racquets", ProductCategory.SPORT_EQUIPMENT,
				"Bat used especially in tennis, badminton, and squash", 20, 0, 0, 0, "Blue", 15.99, suplier5));
		products.add(new Product("Workout Bands", ProductCategory.ACESSORIES,
				"To give you more strenght during workouts", 20, 0, 0, 0, "Blue", 19.99, suplier6));
		products.add(new Product("Step Machine", ProductCategory.ACESSORIES, "Machine to monitor your steps", 20, 0, 0,
				0, "Blue", 159.99, suplier2));
		products.add(new Product("Dumbbells", ProductCategory.SPORT_EQUIPMENT, "Weight to exercise", 20, 0, 0, 0,
				"Blue", 19.99, suplier4));
		products.add(new Product("Pull-Up Bars", ProductCategory.SPORT_EQUIPMENT, "Free bar to pratice pull-ups", 20, 0,
				0, 0, "Blue", 199.99, suplier4));
		products.add(new Product("Boxing Gloves", ProductCategory.ACESSORIES, "Boxing Gloves for adults", 20, 0, 0, 0,
				"Blue", 29.99, suplier5));
		products.add(new Product("Yoga Mats", ProductCategory.ACESSORIES, "Essential for yoga trains", 20, 0, 0, 0,
				"Blue", 29.99, suplier6));
		products.add(new Product("Climbing Shoe", ProductCategory.CLOTHING, "For a better climbing performance", 20, 0,
				0, 0, "Blue", 59.99, suplier6));
		products.add(new Product("Treadmills", ProductCategory.SPORT_EQUIPMENT, "Similuates running on the outdoors",
				20, 0, 0, 0, "Blue", 899.99, suplier6));

		Product product1 = inventoryController.addProductTest(products.get(0));
		Product product2 = inventoryController.addProductTest(products.get(1));
		Product product3 = inventoryController.addProductTest(products.get(2));
		Product product4 = inventoryController.addProductTest(products.get(3));
		Product product5 = inventoryController.addProductTest(products.get(4));
		Product product6 = inventoryController.addProductTest(products.get(5));
		Product product7 = inventoryController.addProductTest(products.get(6));
		Product product8 = inventoryController.addProductTest(products.get(7));
		Product product9 = inventoryController.addProductTest(products.get(8));
		Product product10 = inventoryController.addProductTest(products.get(9));
		Product product11 = inventoryController.addProductTest(products.get(10));
		Product product12 = inventoryController.addProductTest(products.get(11));
		Product product13 = inventoryController.addProductTest(products.get(12));
		Product product14 = inventoryController.addProductTest(products.get(13));
		Product product15 = inventoryController.addProductTest(products.get(14));
		Product product16 = inventoryController.addProductTest(products.get(15));
		Product product17 = inventoryController.addProductTest(products.get(16));
		Product product18 = inventoryController.addProductTest(products.get(17));
		Product product19 = inventoryController.addProductTest(products.get(18));
		Product product20 = inventoryController.addProductTest(products.get(19));

		// Adding Inventory to the database
		inventoryController.addInventoryTest(new Inventory(2, store1, product2));
		inventoryController.addInventoryTest(new Inventory(50, store1, product3));
		inventoryController.addInventoryTest(new Inventory(16, store1, product4));
		inventoryController.addInventoryTest(new Inventory(50, store1, product5));
		inventoryController.addInventoryTest(new Inventory(18, store1, product6));
		inventoryController.addInventoryTest(new Inventory(43, store1, product7));
		inventoryController.addInventoryTest(new Inventory(87, store1, product8));
		inventoryController.addInventoryTest(new Inventory(10, store1, product9));
		inventoryController.addInventoryTest(new Inventory(8, store2, product10));
		inventoryController.addInventoryTest(new Inventory(24, store2, product11));
		inventoryController.addInventoryTest(new Inventory(44, store2, product12));
		inventoryController.addInventoryTest(new Inventory(80, store2, product13));
		inventoryController.addInventoryTest(new Inventory(57, store2, product14));
		inventoryController.addInventoryTest(new Inventory(32, store2, product15));
		inventoryController.addInventoryTest(new Inventory(18, store2, product16));
		inventoryController.addInventoryTest(new Inventory(20, store2, product17));
		inventoryController.addInventoryTest(new Inventory(41, store2, product18));
		inventoryController.addInventoryTest(new Inventory(66, store2, product19));
		inventoryController.addInventoryTest(new Inventory(23, store2, product1));
		inventoryController.addInventoryTest(new Inventory(74, store2, product2));
		inventoryController.addInventoryTest(new Inventory(26, store2, product3));
	}

	/*------------------------------------------------------*/

  
//	HttpServletRequest mock = new HttpServletRequest ()
//	{
//	    private final Map<String, String[]> params = new HashMap<>();
//	    
//	    
//	    public Map<String, String[]> getParameterMap()
//	    {
//	        return params;
//	    }
//
//	}

	HttpServletRequest mock = new HttpServletRequest() {
		private final Map<String, String[]> params = new HashMap<>();

		public Map<String, String[]> getParameterMap() {
			return params;
		}

		public String getParameter(String name) {
			String[] matches = params.get(name);
			if (matches == null || matches.length == 0)
				return null;
			return matches[0];
		}

		@Override
		public Object getAttribute(String name) {
			return null;
		}

		@Override
		public Enumeration<String> getAttributeNames() {
			return null;
		}

		@Override
		public String getCharacterEncoding() {
			return null;
		}

		@Override
		public void setCharacterEncoding(String env) throws UnsupportedEncodingException {

		}

		@Override
		public int getContentLength() {
			return 0;
		}

		@Override
		public long getContentLengthLong() {
			return 0;
		}

		@Override
		public String getContentType() {
			return null;
		}

		@Override
		public ServletInputStream getInputStream() throws IOException {
			return null;
		}

		@Override
		public Enumeration<String> getParameterNames() {
			return null;
		}

		@Override
		public String[] getParameterValues(String name) {
			return null;
		}

		@Override
		public String getProtocol() {
			return null;
		}

		@Override
		public String getScheme() {
			return null;
		}

		@Override
		public String getServerName() {
			return null;
		}

		@Override
		public int getServerPort() {
			return 0;
		}

		@Override
		public BufferedReader getReader() throws IOException {
			return null;
		}

		@Override
		public String getRemoteAddr() {
			return null;
		}

		@Override
		public String getRemoteHost() {
			return null;
		}

		@Override
		public void setAttribute(String name, Object o) {
			// TODO Auto-generated method stub

		}

		@Override
		public void removeAttribute(String name) {
			// TODO Auto-generated method stub

		}

		@Override
		public Locale getLocale() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Enumeration<Locale> getLocales() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isSecure() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public RequestDispatcher getRequestDispatcher(String path) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getRealPath(String path) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getRemotePort() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public String getLocalName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLocalAddr() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getLocalPort() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public ServletContext getServletContext() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public AsyncContext startAsync() throws IllegalStateException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse)
				throws IllegalStateException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isAsyncStarted() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isAsyncSupported() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public AsyncContext getAsyncContext() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DispatcherType getDispatcherType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getAuthType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Cookie[] getCookies() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getDateHeader(String name) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public String getHeader(String name) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Enumeration<String> getHeaders(String name) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Enumeration<String> getHeaderNames() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getIntHeader(String name) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public String getMethod() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getPathInfo() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getPathTranslated() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getContextPath() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getQueryString() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getRemoteUser() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isUserInRole(String role) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Principal getUserPrincipal() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getRequestedSessionId() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getRequestURI() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StringBuffer getRequestURL() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getServletPath() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public HttpSession getSession(boolean create) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public HttpSession getSession() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String changeSessionId() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isRequestedSessionIdValid() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isRequestedSessionIdFromCookie() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isRequestedSessionIdFromURL() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isRequestedSessionIdFromUrl() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void login(String username, String password) throws ServletException {
			// TODO Auto-generated method stub

		}

		@Override
		public void logout() throws ServletException {
			// TODO Auto-generated method stub

		}

		@Override
		public Collection<Part> getParts() throws IOException, ServletException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Part getPart(String name) throws IOException, ServletException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T extends HttpUpgradeHandler> T upgrade(Class<T> httpUpgradeHandlerClass)
				throws IOException, ServletException {
			// TODO Auto-generated method stub
			return null;
		}
	};

}
