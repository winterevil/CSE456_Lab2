package vn.edu.eiu.lab2;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import vn.edu.eiu.lab2.entity.Student;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory
            ("pu1-mysql-persistence");

    public static void main(String[] args) {
        //insertStudent();
        //getStudentById();
        //getAllStudents();
        //getStudentsByGpa();
        //updateGpaById();
        //updateYobById();
        //deleteStudentById();
        //getStudentsByConditions("Le", 8);
    }

    //Định nghĩa các hàm CRUD cho Student
    //Tìm kiếm sv theo nhiều điều kiện được truyền vào(sv có tên là pName, có gpa là pGpa)
    public static void getStudentsByConditions(String pName, double pGpa) {
        EntityManager em = emf.createEntityManager();
        List<Student> students = em.createQuery("select s from Student s where s.name like :pName or s.gpa>:pGpa").setParameter("pName", "%" + pName + "%").setParameter("pGpa", pGpa).getResultList();
        students.forEach(System.out::println);

    }

    //Xóa một sv dựa trên Id
    public static void deleteStudentById() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Student student = em.find(Student.class, "CSE2025003");
        em.remove(student);
        em.getTransaction().commit();
        em.close();
    }

    //Cập nhật điểm dựa trên Id
    public static void updateGpaById() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Student student = em.find(Student.class, "CSE2025001");
        student.setGpa(10);
        em.getTransaction().commit();
        em.close();
    }

    //Cập nhật năm sinh dựa trên Id
    public static void updateYobById() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Student student = em.find(Student.class, "CSE2025001");
        student.setYob(1999);
        em.getTransaction().commit();
        em.close();
    }

    //Hàm tìm sv dựa trên điều kiện Gpa > x
    public static void getStudentsByGpa() {
        EntityManager em = emf.createEntityManager();
        List<Student> students = em.createQuery("SELECT s FROM Student s WHERE s.gpa > 8", Student.class).getResultList();
        System.out.println(students.size() + " students found");
        students.forEach(System.out::println);
        em.close();
    }

    //Hàm thực hiện câu lệnh tìm một sinh viên thông qua mã sinh viên
    public static void getStudentById() {
        EntityManager em = emf.createEntityManager();
        Student std = em.find(Student.class, "CSE2025001");
        System.out.println("Student found: " + std.toString());
        em.close();
    }

    public static void getAllStudents() {
        EntityManager em = emf.createEntityManager();
        //Khi viết truy vấn select thì có thể dùng các loại cú pháp sql sau:
        //SQL thuần
        //HQL: được chỉnh sửa bởi hibernate
        //JPQL: được chỉnh sửa bởi JPA lệnh truy vấn theo kiểu OOP
        List<Student> stds = em.createQuery("select s from Student s", Student.class).getResultList();
        System.out.println("The list of students: ");
        for (Student std : stds) {
            System.out.println(std.toString());
        }
        em.close();
    }

    public static void insertStudent() {
        //1. Tạo người quản lý việc tương tác database
        EntityManager em = emf.createEntityManager();
        //2. Chuẩn bị data để insert
        Student std1 = new Student("CSE2025001", "Lan Le", 2000, 8.5);
        Student std2 = new Student("CSE2025002", "Trung Le", 2001, 9.5);
        Student std3 = new Student("CSE2025003", "Minh Ly", 2002, 7.5);
        //3. Người quản lý thực hiện việc insert
        //Khi thực thi các câu lệnh sql dạng DML (Data Manipulation Language: có làm thay đổi
        // dữ liệu thì bắt bộc phải đặt trong 1 transaction để đảm bảo tính ACID: Atomicity,
        // Consistency, Isolation, Durability: Một là thực thi câu lệnh từ đầu đến cuối,
        // còn ngược lại thì không làm gì hết
        em.getTransaction().begin();
        em.persist(std1); //Ghi xuống db nhưng chưa thực hiện ghi
        em.persist(std2);
        em.persist(std3);
        em.getTransaction().commit(); //Đã ghi xuống db, nếu không thành công thì hủy (rollback)
        em.close(); //Cho anh quản lý nghỉ việc
    }
}