package api.restful;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.springframework.beans.factory.annotation.Autowired;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.ArrayList;

import api.restful.model.catalog.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@Rollback
class ApiRestfulApplicationTests {

	@Autowired
	private CatalogRepository catalogRepository;
	@Autowired
	private CoordinateRepository coordinateRepository;

	private final static String test_projection = "EPSG:4326";

	@Test
	public void insertInCatalog() {
		/*
			 Insere um catálogo no banco de dados com mais de uma coordenada e
			excecuta testes para garantir que tanto o catálogo e as coordenadas
			foram salvas no banco (possuem id válido).
		*/
		Catalog catalog = new Catalog(
			"clip_20170612T083546_Sigma0_VH_db",
			"sentinel A image clip_Sigma0_VH_db.tif INPE",
			"VH",
			"2017-06-12 08:35:46",
			new ArrayList<Coordinate>(),
			"http://www.dpi.inpe.br/agricultural-database/lem/dados/cenas/Sentinel1/20170612_S1A/clip_20170612T083546_Sigma0_VH_db.tif"
		);
		catalogRepository.save(catalog);
		// First Step
		assertTrue(catalog.getId() != null);
		// Coordinate Test
		List<Coordinate> coordinates = new ArrayList<Coordinate>();
		coordinates.add(new Coordinate(
			test_projection,
			-12.042006714207925,
			-45.8734130859375,
			catalog
		));
		coordinates.add(new Coordinate(
			test_projection,
			-12.224602049269444,
			-45.7415771484375,
			catalog
		));
		catalog.setCoordinates(coordinates);
		catalogRepository.save(catalog);
		coordinateRepository.saveAll(catalog.getCoordinates());
		// Assuring that it's saved
		for(Coordinate c : catalog.getCoordinates()) {
			assertTrue(c.getId() != null);
		}
	}

	@Test
	public void listCatalog() {
		/*
			 Salva um catálogo, verifica que foi salvo, então adiciona as coordenadas ao catálogo
			e depois verifica se as tais coordenadas foram de fato salvas.
		*/
		assert(catalogRepository.count() == 0);
		Catalog catalog = new Catalog(
			"clip_20170928T083551_Sigma0_VV_db",
			"sentinel A image clip_Sigma0_VV_db.tif INPE",
			"VH",
			"2017-09-28 08:35:51",
			new ArrayList<Coordinate>(),
			"http://www.dpi.inpe.br/obt/agricultural-database/lem/dados/cenas/Sentinel1/20170928_S1A/clip_20170928T083551_Sigma0_VV_db.tif"
		);
		catalogRepository.save(catalog);
		assertTrue(catalog.getId() != null);
		assertFalse(catalogRepository.count() > 1);
		List<Coordinate> coordinates = new ArrayList<Coordinate>();
		coordinates.add(new Coordinate(
			test_projection,
			-12.031261926409883,
			-45.9173583984375,
			catalog
		));
		coordinates.add(new Coordinate(
			test_projection,
			-11.857943177992869,
			-46.5106201171875,
			catalog
		));
		catalog.setCoordinates(coordinates);
		catalogRepository.save(catalog);
		assertFalse(catalogRepository.count() > 1);
	}

	@Test
	public void searchInCatalog() {
		/*
			 Executa a inserção de catálogo do método insertInCatalog,
			verifica se o catálogo está registrado e se seu nome está salvo corretamente.
		*/
		this.insertInCatalog();
		assertFalse(catalogRepository.count() == 0);
		assertTrue(catalogRepository.count() == 1);
		// assertTrue(catalogRepository.existsById(1L) == true);
		List<Catalog> items = (List<Catalog>) catalogRepository.findAll();
		Catalog last = items.get(items.size() - 1);
		assertTrue(last.getName() == "clip_20170612T083546_Sigma0_VH_db");
	}

	@Test
	public void  deleteCoordinate() {
		/*
			 Insere um catálogo no banco de dados com mais de uma coordenada e
			então remove uma das coordenadas, salva a alteração do catálogo e por fim
			verifica se a coordenada foi de fato removida.
		*/
		this.insertInCatalog();
		List<Catalog> items = (List<Catalog>) catalogRepository.findAll();
		// Pode ser otimizado para uma query JPQL
		Catalog last = items.get(items.size() - 1);
		int coord_size = last.getCoordinates().size();
		assertTrue(coord_size > 1);
		Coordinate coord_item  = last.getCoordinates().remove(1);
		catalogRepository.save(last);
		assert(last.getCoordinates().size() == (coord_size - 1));
		for(Coordinate coord : last.getCoordinates()) {
			assertFalse(coord.getId() == coord_item.getId());
		}
	}

	@Test
	public void deleteCatalog() {
		/*
			 Insere um catálogo no sistema e o excluí verificando também se
			suas coordenadas (Entidade Fraca) foram excluídas.
		*/
		this.insertInCatalog();
		assertFalse(catalogRepository.count() == 0);
		List<Catalog> items = (List<Catalog>) catalogRepository.findAll();
		Catalog last = items.get(items.size() - 1);
		List<Coordinate> coordinates = last.getCoordinates();
		catalogRepository.delete(last);
		assertFalse(catalogRepository.existsById(last.getId()));
		// As coordenadas são uma Entidade Fraca, dependem exclusivamente de Catálogo para existir
		for(Coordinate coord : coordinates) {
			assertFalse(coordinateRepository.existsById(coord.getId()));
		}
	}

}
