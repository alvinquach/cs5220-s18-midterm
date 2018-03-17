package techit.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import techit.model.Unit;
import techit.model.dao.UnitDao;

@RestController
public class UnitController {

    @Autowired
    private UnitDao unitDao;

    @RequestMapping(value = "/unit/{id}", method = RequestMethod.GET)
    public Unit getUnit( @PathVariable Long id )
    {
        return unitDao.getUnit( id );
    }

    @RequestMapping(value = "/unit/", method = RequestMethod.GET)
    public List<Unit> getUnits()
    {
        return unitDao.getUnits();
    }

}
