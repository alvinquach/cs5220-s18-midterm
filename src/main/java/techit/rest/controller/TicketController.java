package techit.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import techit.model.Ticket;
import techit.model.dao.TicketDao;
import techit.rest.error.RestException;

@RestController
public class TicketController {

    @Autowired
    private TicketDao ticketDao;

    @RequestMapping(value = "/tickets", method = RequestMethod.GET)
    public List<Ticket> getTickets()
    {
        return ticketDao.getTickets();
    }

    @RequestMapping(value = "/tickets/{id}", method = RequestMethod.GET)
    public Ticket getTicket( @PathVariable Long id )
    {
        return ticketDao.getTicket( id );
    }
    
    @RequestMapping(value = "/tickets", method = RequestMethod.POST)
    public Ticket createTicket(@RequestBody Ticket ticket) {
    	if (ticket.getCreatedBy() == null ||
    		ticket.getCreatedForEmail() == null ||
    		ticket.getSubject() == null ||
    		ticket.getUnit() == null
		) {
    		
    		throw new RestException(400, "Missing requred data." );
    	}
        return ticketDao.saveTicket(ticket);
    }

}
