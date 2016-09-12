package test;


import org.mobangjack.db.consts.mysql.Encrypt;
import org.mobangjack.db.jodb.table.Table;
import org.mobangjack.db.jodb.table.column.Column;

@Table(name="administrator")
public class Admin extends SuperAdmin{

	@Column(type = "varchar(32)")
	private String username;
	@Column(type = "varbinary(500)",encrypt = Encrypt.AES_ENCRYPT,encryptKey="key")
	private String password;
	@Column(type = "varchar(32)")
	private String authority;
	
	public String getUsername() {
		return username;
	}
	public Admin setUsername(String username) {
		this.username = username;
		return this;
	}
	public String getPassword() {
		return password;
	}
	public Admin setPassword(String password) {
		this.password = password;
		return this;
	}
	public String getAuthoriry() {
		return authority;
	}
	public Admin setAuthoriry(String authority) {
		this.authority = authority;
		return this;
	}
	
	public Admin setId(Integer id) {
		super.setId(id);
		return this;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id:").append(getId()).append("\n")
		  .append("username:").append(getUsername()).append("\n")
		  .append("password:").append(getPassword()).append("\n")
		  .append("authority:").append(getAuthoriry()).append("\n");
		return sb.toString();
	}
	
}
