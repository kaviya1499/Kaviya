package com.atdxt.JdbcConnection.Service;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.atdxt.JdbcConnection.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;



@Repository
public class UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL = "select * from jdbc";
    private static final String INSERT_SQL = "INSERT INTO jdbc (name, age, address) VALUES (?, ?, ?)";

    public List<User> isData() {

        List<User> customers = new ArrayList<User>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL);

        for (Map<String, Object> row : rows)
        {
            User student = new User();
            student.setName((String)row.get("name"));
            student.setAge((String)row.get("age"));
            student.setAddress((String)row.get("address"));

            customers.add(student);
        }

        return customers;
    }
    public void insertUser(User user) {
        jdbcTemplate.update(INSERT_SQL, user.getName(), user.getAge(), user.getAddress());
    }

}
