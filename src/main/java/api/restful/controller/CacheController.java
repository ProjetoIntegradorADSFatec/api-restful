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

import api.restful.model.Cache;
import api.restful.services.CacheServiceImpl;

@RestController
@RequestMapping(value = "cache")
public class CacheController {

	@Autowired
	CacheServiceImpl service = new CacheServiceImpl();

	public void setService(CacheServiceImpl service) {
		this.service = service;
	}

	@CrossOrigin
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
	public List<Cache> cache_list() {
		try {
			return this.service.list();
		}  catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cache list not found can not connect", e);
	   	}
	}

	@CrossOrigin
	@RequestMapping(value = "/remove", method = RequestMethod.DELETE, produces = "application/json")
	public Cache remove_cache(@RequestBody Cache cache) {
		try {
			Cache usr = (Cache) this.service.findOneById(cache.getId());
			if (this.service.remove(usr)) {
				return usr;
			} else {
				return new Cache();
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cache can not be deleted", e);
		}
	}

	@CrossOrigin
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
	public Cache add_cache(@RequestBody Cache cache) {
		try {
			Cache c = (Cache) cache;
			if (this.service.add(c)) {
				return c;
			} else {
				return new Cache();
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cache can not be created", e);
		}
	}
}