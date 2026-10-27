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


    }




}
