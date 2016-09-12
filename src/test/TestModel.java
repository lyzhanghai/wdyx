package test;

import java.sql.SQLException;
import java.util.List;

import org.mobangjack.db.Tx;
import org.mobangjack.db.jodb.Jodb;

public class TestModel {
	
	public static void testInsert() throws SQLException{
		System.out.println("---------------------------testInsert-----------------------------");
		Admin admin = new Admin();
		admin.setId(1).setUsername("user1").setPassword("pass1").setAuthoriry("w");
		Jodb.insert(admin,true);
	}
	public static void testDelete() throws SQLException{
		System.out.println("---------------------------testDelete-----------------------------");
		Admin admin = new Admin();
		admin.setId(1).setUsername("user1").setPassword("pass1").setAuthoriry("w");
		Jodb.delete(admin);
	}
	public static void testUpdate() throws SQLException{
		System.out.println("---------------------------testUpdate-----------------------------");
		Admin admin1 = new Admin();
		admin1.setId(1).setUsername("user1").setPassword("pass1").setAuthoriry("w");
		Admin admin2 = new Admin();
		admin2.setId(1).setUsername("user2").setPassword("pass2").setAuthoriry("r");
		Jodb.update(admin1,admin2);
	}
	public static void testSelect() throws SQLException{
		System.out.println("---------------------------testSelect-----------------------------");
		Admin admin = new Admin();
		admin.setId(1);
		List<Admin> admins = Jodb.select(admin);
		for(Admin a:admins){
			System.out.println(a.getId());
			System.out.println(a.getUsername());
			System.out.println(a.getPassword());
			System.out.println(a.getAuthoriry());
		}
	}
	public static void testValidate() throws SQLException{
		System.out.println("---------------------------testValidate-----------------------------");
		Admin admin = new Admin();
		admin.setUsername("user1").setPassword("pass1");
		System.out.println(Jodb.validate(admin));
	}
	public static void testCount() throws SQLException{
		System.out.println("---------------------------testCount-----------------------------");
		Admin admin = new Admin();
		admin.setUsername("user1").setPassword("pass1");
		System.out.println(Jodb.count(admin));
	}
	public static void testTransaction() {
		System.out.println("---------------------------testTransaction-----------------------------");
		Tx tx = new Tx(){

			@Override
			public void run() {
				
				Admin admin1 = new Admin();
				admin1.setId(1).setUsername("user1").setPassword("pass1").setAuthoriry("w");
				System.out.println(Jodb.insert(admin1,true));
				System.out.println(Jodb.select(admin1).get(0));
				
				Admin admin2 = new Admin();
				admin2.setId(1).setUsername("user2").setPassword("pass2").setAuthoriry("r");
				System.out.println(Jodb.update(admin1, admin2));
				System.out.println(Jodb.select(admin2).get(0));
				
				Admin admin3 = new Admin();
				admin3.setId(100).setUsername("user3").setPassword("pass3").setAuthoriry("w");
				System.out.println(Jodb.update(admin2, admin3));
				System.out.println(Jodb.select(admin3).get(0));
				
				//rollback();
			}
			
		};
		tx.start();
	}
	public static void main(String[] args) throws SQLException{
		Jodb.debug = true;
		//testInsert();
		//testDelete();
		//testUpdate();
		//testSelect();
		//testValidate();
		//testCount();
		testTransaction();
		
	}

}
