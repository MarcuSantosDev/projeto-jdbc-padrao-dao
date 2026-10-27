package model.dao.impl;


import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;
import db.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {
    private Connection conn;

    public SellerDaoJDBC(Connection conn){
        this.conn = conn;

    }

    @Override
    public void insert(Seller obj) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("INSERT INTO seller "
                    + "(Name,Email,BirthDate,baseSalary,DepartmentId)  "
                    + "VALUES "
                    + "(?,?,?,?,?) ",
                    Statement.RETURN_GENERATED_KEYS); // RETURN_GENERATED_KEYS → “banco, guarde a chave que você gerar”
                                                      // getGeneratedKeys() → “agora me entregue essa chave”


            st.setString(1,obj.getName());
            st.setString(2,obj.getEmail());
            st.setDate(3, Date.valueOf(obj.getBirthDate()));
            st.setDouble(4,obj.getBaseSalary());
            st.setInt(5,obj.getDepartment().getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            }
                else{
                    throw new DbException("Unexpected error! no rows affected !");
                }


        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT seller .*, department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "WHERE seller.id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) { // Testa se veio um resultado
                Department dep = instantiateDepartment(rs);
                Seller obj = instantiateSeller(rs,dep);
                return obj;
            }
            return null;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException{
        Seller obj = new Seller();

        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("name"));
        obj.setEmail(rs.getString("Email"));
        obj.setBaseSalary(rs.getDouble("BaseSalary"));
        obj.setBirthDate(rs.getDate("BirthDate").toLocalDate());
        obj.setDepartment(dep);
        return obj;
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException{
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Seller> list = new ArrayList<>();
        Map<Integer,Department> map = new HashMap<>();
        try {
            st = conn.prepareStatement("SELECT seller.* , department.Name as DepName " +
                    "FROM seller INNER JOIN department "
                    + "ON  seller.departmentID =department.id "
                    + "ORDER BY seller.name");

            rs = st.executeQuery();

            while (rs.next()){
                Department dep = map.get(rs.getInt("DepartmentId"));
                if (dep == null){
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }
                Seller obj = instantiateSeller(rs,dep);

                list.add(obj);
            }
            return list;
        }

        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Seller> list = new ArrayList<>();
        Map<Integer,Department> map = new HashMap<>();

        try {
            st = conn.prepareStatement("SELECT seller.* , department.Name as DepName "
                    + "FROM seller inner join department ON seller.DepartmentId = department.Id "
                    + "WHERE DepartmentId = ? ORDER BY Name");

            st.setInt(1,department.getId());
            rs = st.executeQuery();

            while (rs.next()){
                Department dep = map.get(rs.getInt("DepartmentId"));
                if(dep == null){
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId") ,dep );
                }

                Seller obj = instantiateSeller(rs,dep);
                 list.add(obj);
            }

            return list;
        }

        catch (SQLException e){
            throw new DbException(e.getMessage());
        }

    }
}


