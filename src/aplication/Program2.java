package aplication;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.util.List;

public class Program2 {
    public static void main(String[] args) {
        DepartmentDao daoDepartment = DaoFactory.createDepartmentDao();

        System.out.println("======== Test: Find ALL ========");
        List<Department> list = daoDepartment.findAll();
        list.forEach(System.out::println);

        System.out.println("======== Test: Find by ID ========");
        int id = 2;
        System.out.println(daoDepartment.findById(id));

        System.out.println("======== Test: Insert ========");
        Department newDepartment = new Department(10,"D10");
        daoDepartment.insert(newDepartment);

        System.out.println("Departament add with sucess !");
        list.forEach(System.out::println);
    }




}
