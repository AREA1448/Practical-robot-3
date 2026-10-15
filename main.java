import java.util.ArrayList;
import java.util.List;

// ==================== Person ====================
class Person {
    private String lastName;
    private String firstName;
    private String middleName;
    private String birthDate;
    private String phone;
    private String email;

    public Person() {}

    public Person(String lastName, String firstName, String middleName,
                  String birthDate, String phone, String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthDate = birthDate;
        this.phone = phone;
        this.email = email;
    }

    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getBirthDate() { return birthDate; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }

    public String getFullName() {
        return lastName + " " + firstName + " " + middleName;
    }

    public void changeContactInfo(String newPhone, String newEmail) {
        if (newPhone != null && !newPhone.isEmpty()) this.phone = newPhone;
        if (newEmail != null && !newEmail.isEmpty()) this.email = newEmail;
    }

    void updatePersonalData(String lastName, String firstName, String middleName) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
    }

    protected void printBasicInfo() {
        System.out.println("ПІБ: " + getFullName());
        System.out.println("Дата народження: " + birthDate);
        System.out.println("Телефон: " + phone);
        System.out.println("Email: " + email);
    }

    @Override
    public String toString() {
        return "Person{ПІБ='" + getFullName() + "', birthDate='" + birthDate +
                "', phone='" + phone + "', email='" + email + "'}";
    }
}

// ==================== Student ====================
class Student extends Person {
    private String studentId;
    private Group group;
    private String specialty;
    private int course;
    private double averageGrade;

    public Student() { super(); }

    public Student(String lastName, String firstName, String middleName,
                   String birthDate, String phone, String email,
                   String studentId, String specialty, int course) {
        super(lastName, firstName, middleName, birthDate, phone, email);
        this.studentId = studentId;
        this.specialty = specialty;
        this.course = course;
        this.averageGrade = 0.0;
    }

    public String getStudentId() { return studentId; }
    public Group getGroup() { return group; }
    public String getSpecialty() { return specialty; }
    public int getCourse() { return course; }
    public double getAverageGrade() { return averageGrade; }

    public void receiveGrade(int value) {
        if (value < 1 || value > 12) {
            System.out.println("Некоректна оцінка: " + value);
            return;
        }
        this.averageGrade = (this.averageGrade + value) / 2.0;
        System.out.println(getFullName() + " отримав оцінку " + value);
    }

    public void moveToNextCourse() {
        if (course < 4) {
            course++;
            System.out.println(getFullName() + " переведено на " + course + " курс");
        } else {
            System.out.println(getFullName() + " вже на останньому курсі");
        }
    }

    public void changeGroup(Group newGroup) {
        if (this.group != null) this.group.removeStudent(this);
        this.group = newGroup;
        if (newGroup != null) newGroup.addStudent(this);
    }

    public void viewProgress() {
        System.out.println("Успішність студента " + getFullName() + ":");
        System.out.println("Середній бал: " + averageGrade);
        System.out.println("Курс: " + course);
        System.out.println("Група: " + (group != null ? group.getName() : "не призначено"));
    }

    void setGroupInternal(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Student{studentId='" + studentId + "', specialty='" + specialty +
                "', course=" + course + ", averageGrade=" + averageGrade +
                ", group=" + (group != null ? group.getName() : "null") +
                ", " + super.toString() + "}";
    }
}

// ==================== Teacher ====================
class Teacher extends Person {
    private String employeeId;
    private String department;
    private String position;
    private int workExperience;

    public Teacher() { super(); }

    public Teacher(String lastName, String firstName, String middleName,
                   String birthDate, String phone, String email,
                   String employeeId, String department, String position,
                   int workExperience) {
        super(lastName, firstName, middleName, birthDate, phone, email);
        this.employeeId = employeeId;
        this.department = department;
        this.position = position;
        this.workExperience = workExperience;
    }

    public String getEmployeeId() { return employeeId; }
    public String getDepartment() { return department; }
    public String getPosition() { return position; }
    public int getWorkExperience() { return workExperience; }

    public void conductClass(String disciplineName) {
        System.out.println(getFullName() + " проводить заняття з дисципліни: " + disciplineName);
    }

    public void assignGrade(Student student, int value) {
        student.receiveGrade(value);
        System.out.println("Викладач " + getFullName() + " виставив оцінку " + value +
                " студенту " + student.getFullName());
    }

    public void increaseExperience() {
        workExperience++;
        System.out.println(getFullName() + " тепер має стаж " + workExperience + " років");
    }

    void changeDepartment(String newDepartment) {
        this.department = newDepartment;
    }

    @Override
    public String toString() {
        return "Teacher{employeeId='" + employeeId + "', department='" + department +
                "', position='" + position + "', workExperience=" + workExperience +
                ", " + super.toString() + "}";
    }
}

// ==================== Group ====================
class Group {
    private String name;
    private String specialty;
    private int course;
    private Teacher curator;
    private List<Student> students;

    public Group() {
        this.students = new ArrayList<>();
    }

    public Group(String name, String specialty, int course, Teacher curator) {
        this.name = name;
        this.specialty = specialty;
        this.course = course;
        this.curator = curator;
        this.students = new ArrayList<>();
    }

    public String getName() { return name; }
    public String getSpecialty() { return specialty; }
    public int getCourse() { return course; }
    public Teacher getCurator() { return curator; }
    public int getStudentsCount() { return students.size(); }

    public void addStudent(Student student) {
        if (student != null && !students.contains(student)) {
            students.add(student);
            student.setGroupInternal(this);
            System.out.println("Студента " + student.getFullName() + " додано до групи " + name);
        }
    }

    public void removeStudent(Student student) {
        if (students.remove(student)) {
            student.setGroupInternal(null);
            System.out.println("Студента " + student.getFullName() + " видалено з групи " + name);
        }
    }

    public void changeCurator(Teacher newCurator) {
        this.curator = newCurator;
        System.out.println("Новий куратор групи " + name + ": " +
                (newCurator != null ? newCurator.getFullName() : "не призначено"));
    }

    public double calculateAverageGrade() {
        if (students.isEmpty()) return 0.0;
        double sum = 0;
        for (Student s : students) sum += s.getAverageGrade();
        return sum / students.size();
    }

    public void printStudents() {
        System.out.println("Студенти групи " + name + ":");
        for (Student s : students) {
            System.out.println(" - " + s.getFullName() + " (середній бал: " + s.getAverageGrade() + ")");
        }
    }

    List<Student> getStudentsInternal() {
        return students;
    }

    @Override
    public String toString() {
        return "Group{name='" + name + "', specialty='" + specialty +
                "', course=" + course + ", studentsCount=" + students.size() +
                ", curator=" + (curator != null ? curator.getFullName() : "null") + "}";
    }
}

// ==================== Discipline ====================
class Discipline {
    private String name;
    private int hours;
    private Teacher teacher;
    private int semester;
    private String controlForm;

    public Discipline() {}

    public Discipline(String name, int hours, Teacher teacher, int semester, String controlForm) {
        this.name = name;
        this.hours = hours;
        this.teacher = teacher;
        this.semester = semester;
        this.controlForm = controlForm;
    }

    public String getName() { return name; }
    public int getHours() { return hours; }
    public Teacher getTeacher() { return teacher; }
    public int getSemester() { return semester; }
    public String getControlForm() { return controlForm; }

    public void assignTeacher(Teacher teacher) {
        this.teacher = teacher;
        System.out.println("Викладача " + teacher.getFullName() +
                " призначено на дисципліну \"" + name + "\"");
    }

    public void changeHours(int newHours) {
        if (newHours > 0) {
            this.hours = newHours;
            System.out.println("Кількість годин дисципліни \"" + name + "\" змінено на " + newHours);
        }
    }

    void setControlForm(String form) {
        this.controlForm = form;
    }

    @Override
    public String toString() {
        return "Discipline{name='" + name + "', hours=" + hours +
                ", teacher=" + (teacher != null ? teacher.getFullName() : "null") +
                ", semester=" + semester + ", controlForm='" + controlForm + "'}";
    }
}

// ==================== Grade ====================
class Grade {
    private int value;
    private String date;
    private Student student;
    private Discipline discipline;
    private Teacher teacher;
    private String type;

    public Grade() {}

    public Grade(int value, String date, Student student,
                 Discipline discipline, Teacher teacher, String type) {
        this.value = value;
        this.date = date;
        this.student = student;
        this.discipline = discipline;
        this.teacher = teacher;
        this.type = type;
    }

    public int getValue() { return value; }
    public String getDate() { return date; }
    public Student getStudent() { return student; }
    public Discipline getDiscipline() { return discipline; }
    public Teacher getTeacher() { return teacher; }
    public String getType() { return type; }

    public void changeValue(int newValue) {
        if (newValue >= 1 && newValue <= 12) {
            this.value = newValue;
            System.out.println("Оцінку змінено на " + newValue);
        } else {
            System.out.println("Некоректне значення оцінки");
        }
    }

    void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Grade{value=" + value + ", date='" + date +
                "', student=" + (student != null ? student.getFullName() : "null") +
                ", discipline=" + (discipline != null ? discipline.getName() : "null") +
                ", teacher=" + (teacher != null ? teacher.getFullName() : "null") +
                ", type='" + type + "'}";
    }
}

// ==================== Journal ====================
class Journal {
    private Group group;
    private Discipline discipline;
    private int semester;
    private List<Grade> grades;

    public Journal() {
        this.grades = new ArrayList<>();
    }

    public Journal(Group group, Discipline discipline, int semester) {
        this.group = group;
        this.discipline = discipline;
        this.semester = semester;
        this.grades = new ArrayList<>();
    }

    public Group getGroup() { return group; }
    public Discipline getDiscipline() { return discipline; }
    public int getSemester() { return semester; }

    public void addGrade(Grade grade) {
        if (grade != null) {
            grades.add(grade);
            System.out.println("Оцінку додано до журналу: " + grade.getValue() +
                    " (" + grade.getStudent().getFullName() + ")");
        }
    }

    public void viewGrades() {
        System.out.println("Журнал групи " + group.getName() +
                " з дисципліни \"" + discipline.getName() + "\":");
        for (Grade g : grades) {
            System.out.println(" - " + g.getStudent().getFullName() +
                    ": " + g.getValue() + " (" + g.getType() + ")");
        }
    }

    public double calculateAverage() {
        if (grades.isEmpty()) return 0.0;
        double sum = 0;
        for (Grade g : grades) sum += g.getValue();
        return sum / grades.size();
    }

    List<Grade> getGradesInternal() {
        return grades;
    }

    @Override
    public String toString() {
        return "Journal{group=" + (group != null ? group.getName() : "null") +
                ", discipline=" + (discipline != null ? discipline.getName() : "null") +
                ", semester=" + semester + ", gradesCount=" + grades.size() + "}";
    }
}

// ==================== Main ====================
public class Main {
    public static void main(String[] args) {
        System.out.println("========== ДЕМОНСТРАЦІЯ ІНКАПСУЛЯЦІЇ ==========\n");

        Teacher teacher = new Teacher(
                "Іваненко", "Олена", "Петрівна",
                "15.03.1980", "+380501112233", "ivanenko@college.edu.ua",
                "T-001", "Циклова комісія ІТ", "Викладач", 12
        );

        Group group = new Group("К-31", "Комп'ютерні науки", 3, teacher);

        Student student1 = new Student(
                "Коваленко", "Ярослав", "Русланович",
                "12.07.2005", "+380671234567", "kovalenko@student.edu.ua",
                "ST-2023-045", "122 Комп'ютерні науки", 3
        );

        Student student2 = new Student(
                "Петренко", "Марія", "Олександрівна",
                "22.11.2004", "+380931112233", "petrenko@student.edu.ua",
                "ST-2023-046", "122 Комп'ютерні науки", 3
        );

        group.addStudent(student1);
        group.addStudent(student2);

        Discipline discipline = new Discipline(
                "Об'єктно-орієнтоване програмування", 72, teacher, 5, "Екзамен"
        );

        teacher.conductClass(discipline.getName());
        teacher.assignGrade(student1, 11);
        teacher.assignGrade(student2, 10);

        Journal journal = new Journal(group, discipline, 5);
        Grade grade1 = new Grade(11, "12.10.2026", student1, discipline, teacher, "Поточна");
        Grade grade2 = new Grade(10, "12.10.2026", student2, discipline, teacher, "Поточна");
        journal.addGrade(grade1);
        journal.addGrade(grade2);

        System.out.println("\n----- Інформація про групу -----");
        System.out.println(group);
        group.printStudents();
        System.out.println("Середній бал групи: " + group.calculateAverageGrade());

        System.out.println("\n----- Журнал -----");
        journal.viewGrades();
        System.out.println("Середній бал з дисципліни: " + journal.calculateAverage());

        System.out.println("\n----- Успішність студента -----");
        student1.viewProgress();

        System.out.println("\n----- Зміна контактних даних -----");
        student1.changeContactInfo("+380999887766", "new.email@student.edu.ua");
        System.out.println(student1);

        System.out.println("\n----- Переведення на наступний курс -----");
        student1.moveToNextCourse();

        System.out.println("\n========== КІНЕЦЬ ДЕМОНСТРАЦІЇ ==========");
    }
}
