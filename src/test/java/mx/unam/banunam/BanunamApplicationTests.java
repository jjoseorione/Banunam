package mx.unam.banunam;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
public class BanunamApplicationTests {

	@Test
	@Sql({"/sql/schema.sql", "/sql/data.sql"})
	protected void contextLoads() {
		System.out.println("•••••• BANUNAM ••••••");
		System.out.println("Carga de esquema y estado de tabla Clientes");
	}
}
