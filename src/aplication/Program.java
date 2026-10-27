package aplication;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.time.LocalDate;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("======== TEST 1: FindByID ==========");
        Seller seller = sellerDao.findById(7);

        System.out.println("======== TEST 2: FindByDepartment ==========");

        Department dep = new Department(2,null);
        List<Seller> list = sellerDao.findByDepartment(dep);
        for (Seller s: list){
            System.out.println(s);
        }
    }
}
