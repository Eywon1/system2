package project_system.org;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class EmployeeTaskController {

    

    private final String DB_URL = "jdbc:mysql://localhost:3306/pomsdb";
    private final String DB_USER ="root";
    private final String DB_PASSWORD = "luese_192003";
    
 

   /*public void getEmployeeTask(String department){
   
    try{
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM mycompanydb.employeesinfo WHERE Department = ?");
            preparedStatement.setString(1, department);
            ResultSet resultSet = preparedStatement.executeQuery();

             ObservableList<List> List = FXCollections.observableArrayList();

            while(resultSet.next()){
                List.add(new List(resultSet.getString("EmployeeID"), resultSet.getString("EmployeeName"), resultSet.getString("Department"), resultSet.getString("Task"), resultSet.getString("TaskStatus"), resultSet.getString("TaskDeadline")));

    }catch(Exception e){
        e.printStackTrace();
    }
    }

       final JList<String> list1 = new JList<>(List); 
        */



   














   
}
