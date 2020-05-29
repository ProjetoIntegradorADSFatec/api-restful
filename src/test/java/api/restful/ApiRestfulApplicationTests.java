package api.restful;

import org.springframework.beans.factory.annotation.Autowired;

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

	@Autowired
	private CatalogServiceImpl catalogService; 

	@Test
	public void insertInCatalog() {
		Catalog catalog = new Catalog(
			"clip_20170612T083546_Sigma0_VH_db",
			"sentinel A image clip_Sigma0_VH_db.tif INPE",
			"VH",
			"2017-06-12 08:35:46",
			new ArrayList<Coordinate>(),
			"http://www.dpi.inpe.br/agricultural-database/lem/dados/cenas/Sentinel1/20170612_S1A/clip_20170612T083546_Sigma0_VH_db.tif"
		);

		catalogService.add(catalog);
		// First Step
		assertTrue(catalog.getId() != null);
		
		// SET CATALOG 
		/*
		List<Coordinate> coordinates = new ArrayList<Coordinate>();
		coordinates.add(new Coordinate(
			"projection",
			1.11,
			2.1512522,
			null
		));
		*/

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
