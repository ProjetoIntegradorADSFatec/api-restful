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
import api.restful.services.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@Rollback
class ApiRestfulApplicationTests {

	@Autowired
	private CatalogServiceImpl service;

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
		service.add(catalog);
		assertTrue(service.listCatalog().size() >= 0);
	}

	@Test
	public void listCatalog() {
		/*
			 Salva um catálogo, verifica que foi salvo, então adiciona as coordenadas ao catálogo
			e depois verifica se as tais coordenadas foram de fato salvas.
		*/
		assertTrue(service.listCatalog().size() > 0);
		Catalog catalog = new Catalog(
			"clip_20170928T083551_Sigma0_VV_db",
			"sentinel A image clip_Sigma0_VV_db.tif INPE",
			"VH",
			"2017-09-28 08:35:51",
			new ArrayList<Coordinate>(),
			"http://www.dpi.inpe.br/obt/agricultural-database/lem/dados/cenas/Sentinel1/20170928_S1A/clip_20170928T083551_Sigma0_VV_db.tif"
		);
		service.add(catalog);
		assertTrue(service.listCatalog().size() > 1);
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
		service.add(catalog);
		assertFalse(service.listCatalog().size() < 2);
	}

	@Test
	public void  deleteCoordinate() {
		/*
			 Insere um catálogo no banco de dados com mais de uma coordenada e
			então remove uma das coordenadas, salva a alteração do catálogo e por fim
			verifica se a coordenada foi de fato removida.
		*/
		this.insertInCatalog();
		List<Catalog> items = (List<Catalog>) service.listCatalog();
		// Pode ser otimizado para uma query JPQL
		Catalog last = items.get(items.size() - 1);
		int coord_size = last.getCoordinates().size();
		assertTrue(coord_size > 1);
		Coordinate coord_item  = last.getCoordinates().remove(1);
		service.add(last);
		assert(last.getCoordinates().size() == (coord_size - 1));
		for(Coordinate coord : last.getCoordinates()) {
			assertFalse(coord.getId() == coord_item.getId());
		}
	}
}
