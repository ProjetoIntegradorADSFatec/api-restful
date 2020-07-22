package api.restful.controller;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;

import api.restful.model.catalog.Catalog;
import api.restful.model.catalog.Coordinate;
import api.restful.model.collection.Item;
import api.restful.model.geojson.Geojson;

import api.restful.services.CatalogServiceImpl;

import com.fasterxml.jackson.annotation.JsonView;
import api.restful.model.views.Views;

@RestController
@RequestMapping(value = "catalog")
public class CatalogController {

	@Autowired
	CatalogServiceImpl service = new CatalogServiceImpl();

	public void setService(CatalogServiceImpl service) {
		this.service = service;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
	public Item catalog_list() {
		try {
			return this.service.listItems();
		}  catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Catalog list not found can not connect", e);
	   	}
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST, produces = "application/json")
	public Item catalog_search(@RequestBody Geojson geojson) {
		try {
			return this.service.search(geojson);
		}  catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Catalog list not found can not connect", e);
		}
	}

	@JsonView(Views.Public.class)
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
	public Catalog add_catalog(@RequestBody Catalog catalog) {
		try {
			Catalog c = (Catalog) catalog;
			if (this.service.add(c)) {
				return c;
			} else {
				return new Catalog();
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Catalog can not be created", e);
		}
	}

	@JsonView(Views.Public.class)
	@RequestMapping(value = "/remove", method = RequestMethod.GET, produces = "application/json")
	public Catalog remove_catalog(@RequestParam String id) {
		try {
			Catalog c = (Catalog) this.service.findById(new Long(id));
			if (this.service.remove(c)) {
				c.setCoordinates(new ArrayList<Coordinate>());
				return c;
			} else {
				return new Catalog();
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Catalog can not be deleted", e);
		}
	}
}