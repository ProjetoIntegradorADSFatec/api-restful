package api.restful;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@Rollback
class ApiRestfulApplicationTests {

	@Test
	public void insertInCatalog() {

	}

	@Test
	public void insertInCoordinate() {

	}

	@Test
	public void searchInCatalog() {

	}

	@Test
	public void  deleteCoordinate() {

	}

	@Test
	public void deleteCatalog() {

	}

}
