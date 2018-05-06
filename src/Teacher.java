import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Teacher implements java.io.Serializable {

	private long id;
	private String name;
	private String surname;
	private String pesel;
	private List<SchoolClass> classes;

	public String toString() {
		return "Teacher " + getName() + " " + getSurname() + " (" + getPesel() + ")";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPesel() {
		return pesel;
	}

	public void setPesel(String pesel) {
		this.pesel = pesel;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<SchoolClass> getClasses() {
		return classes;
	}

	public void setClasses(ArrayList<SchoolClass> classes) {
		if (classes == null) {
			classes = new ArrayList<SchoolClass>();
		}
		this.classes = classes;
	}

}