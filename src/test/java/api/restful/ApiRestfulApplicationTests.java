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
	public void listCatalog() {
		/*
			Salva um catálogo, verifica que foi salvo, então adiciona as coordenadas ao catálogo
			e depois verifica se as tais coordenadas foram de fato salvas.
		*/
		assertTrue(service.listCatalog().size() > 1);
	}
}
