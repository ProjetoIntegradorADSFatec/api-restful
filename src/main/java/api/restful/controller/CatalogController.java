package api.restful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import api.restful.model.Catalog;
import api.restful.model.geojson.Request;
import api.restful.services.CatalogServiceImpl;

@RestController
@RequestMapping(value = "catalog")
public class CatalogController {

	@Autowired
	CatalogServiceImpl service = new CatalogServiceImpl();

	public void setService(CatalogServiceImpl service) {
		this.service = service;
	}

	@CrossOrigin
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
	public List<Catalog> catalog_list() {
		try {
			return this.service.list();
		}  catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Catalog list not found can not connect", e);
	   	}
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST, produces = "application/json")
	public List<Catalog> catalog_search(@RequestBody Request request) {
		try {
			return this.service.search(request);
		}  catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Catalog list not found can not connect", e);
		}
	}

	@CrossOrigin
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

	@CrossOrigin
	@RequestMapping(value = "/remove", method = RequestMethod.DELETE, produces = "application/json")
	public Catalog remove_catalog(@RequestBody Catalog catalog) {
		try {
			Catalog c = (Catalog) catalog;
			if (this.service.remove(c)) {
				return c;
			} else {
				return new Catalog();
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Catalog can not be deleted", e);
		}
	}
}