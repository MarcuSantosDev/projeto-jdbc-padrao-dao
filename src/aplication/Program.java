package aplication;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("======== TEST 1: FindByID ==========");
        Seller seller = sellerDao.findById(7);

        System.out.println("======== TEST 2: FindByDepartment ==========");

        Department dep = new Department(2,null);
        List<Seller> listByDepartment = sellerDao.findByDepartment(dep);
        for (Seller s: listByDepartment){
            System.out.println(s);
        }

        System.out.println("======== TEST 3: FindALl ==========");
        List<Seller> listAll = sellerDao.findAll();
        for (Seller s: listAll){
            System.out.println(s);
        }

        System.out.println("======== TEST 4: Insert ==========");
        Seller newSeller = new Seller(null, "Greg","greg@gmail.com",LocalDate.parse("1998-04-21"),4000.0,dep);
        sellerDao.insert(newSeller);
        System.out.println("Inserted new id = " + newSeller.getId());
    }
}
