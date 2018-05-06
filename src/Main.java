import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Main {

	Session session;

	public static void main(String[] args) {
		Main main = new Main();
		main.addNewData(); 
		//main.printSchools();
		main.executeQueries();
		//main.addTeacher();
		main.close();
	}

	public Main() {
		session = HibernateUtil.getSessionFactory().openSession();
	}

	public void close() {
		session.close();
		HibernateUtil.shutdown();
	}

	private void printSchools() {
		Criteria crit = session.createCriteria(School.class);
		List<School> schools = crit.list();

		System.out.println("### Schools and classes");
		for (School s : schools) {
			System.out.println(s);
			for (SchoolClass schoolClass: s.getClasses()){
				System.out.println("	"+schoolClass);
				System.out.println("	  >Students:");
				for(Student stud : schoolClass.getStudents()){
					System.out.println("		"+stud);
				}
			}
		}
	}

	private void jdbcTest() {
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("org.sqlite.JDBC");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection("jdbc:sqlite:school.db", "", "");

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM schools";
			ResultSet rs = stmt.executeQuery(sql);

			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				String name = rs.getString("name");
				String address = rs.getString("address");

				// Display values
				System.out.println("Name: " + name);
				System.out.println(" address: " + address);
			}
			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");
	}// end jdbcTest

	private void addNewData(){
		Transaction transaction = session.beginTransaction();
		School newSchool = new School();
		newSchool.setName("Szkola podstawowa");
		newSchool.setAddress("Bibice");		
		session.save(newSchool); // gdzie newSchool to instancja nowej szko造
		transaction.commit();
	}
	
	private void addTeacher(){
		Transaction transaction = session.beginTransaction();
		Teacher newTeacher = new Teacher();
		newTeacher.setName("Janusz");
		newTeacher.setSurname("Nowak");
		newTeacher.setPesel("78092309094");
		session.save(newTeacher); // gdzie newSchool to instancja nowej szko造
		transaction.commit();
	}
	
	private void executeQueries() {
		example();
		podpunkt1();
		podpunkt2();
		example();
		podpunkt3();
		podpunkt4();
		podpunkt5();
		podpunkt6();
		zadanie5();
		example();
	}
	
	private void example() {
		System.out.print("Example: ");
        String hql = "FROM School";
        Query query = session.createQuery(hql);
        List results = query.list();
        System.out.println(results);
	}
	
	private void podpunkt1() {
		System.out.print("Podpunkt 1: ");
        String hql1 = "FROM School where name = 'AE'";
        Query query = session.createQuery(hql1);
        List results = query.list();
        System.out.println(results);
	}
	
	private void podpunkt2() {
		System.out.print("Podpunkt 2: ");
        String hql2 = "FROM School where name = 'Szkola podstawowa'";
        Query query = session.createQuery(hql2);
        List results = query.list();
        Transaction transaction = session.beginTransaction();
        for (Object s : results){
        	session.delete(s);
        }
        transaction.commit();
        System.out.println("Szko造 zosta造 usuni皻e");
	}
	
	private void podpunkt3() {
		System.out.print("Podpunkt 3: ");
        Integer res;
        res = 0;
        String hql3 = "select count(*) from School";
        Query query = session.createQuery(hql3);
        res = (Integer) query.uniqueResult();
        System.out.println(res);
	}
	
	private void podpunkt4() {
		System.out.print("Podpunkt 4: ");
        Integer res = 0;
        String hql4 = "select count(*) from Student";
        Query query = session.createQuery(hql4);
        res = (Integer) query.uniqueResult();
        System.out.println(res);
        
	}
	
	private void podpunkt5() {
		System.out.print("Podpunkt 5: ");
        String hql6 = "SELECT s FROM School s where size(s.classes)>=2";
        Query query = session.createQuery(hql6);
        List results = query.list();
        System.out.println(results); 
	}
	
	private void podpunkt6() {
		System.out.print("Podpunkt 6: ");
        String hql6 = "SELECT s FROM School s INNER JOIN s.classes classes WHERE (classes.profile = 'mat-fiz' AND classes.currentYear >= 2)";
        Query query = session.createQuery(hql6);
        List results = query.list();
        System.out.println(results);
	}
	
	private void zadanie5() {
        Query query = session.createQuery("from School where id= :id");
        query.setLong("id", 2);
        School school = (School) query.uniqueResult();
        school.setAddress("Katowice");
        
        Transaction transaction = session.beginTransaction();	
		session.save(school);
		transaction.commit();
}
}
