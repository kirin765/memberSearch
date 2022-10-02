package memberSearch.memberSearch;

import memberSearch.memberSearch.Connection.DBConnectionUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@SpringBootApplication
public class MemberSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemberSearchApplication.class, args);
	}

	@Bean
	public DataSource dataSource(){
		return new DriverManagerDataSource(DBConnectionUtil.URL, DBConnectionUtil.USERNAME, DBConnectionUtil.PASSWORD);
	}

}
