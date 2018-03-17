insert into units (id, name) values (1, 'TechOps');
insert into units (id, name) values (2, 'ITC');

insert into users (id, type, username, password, first_name, last_name, email) values
    (1, 'ADMIN', 'techit', 'abcd', 'System', 'Admin', 'techit@localhost.localdomain');
    
insert into users (id, type, username, password, first_name, last_name, email, unit_id) values
    (2, 'SUPERVISOR', 'jsmith', 'abcd', 'John', 'Smith', 'jsmith@localhost.localdomain', 1);
insert into users (id, type, username, password, first_name, last_name, email, unit_id) values
    (3, 'TECHNICIAN', 'jjim', 'abcd', 'Jimmy', 'Jim', 'jjim@localhost.localdomain', 1);
insert into users (id, type, username, password, first_name, last_name, email, unit_id) values
    (4, 'TECHNICIAN', 'blee', 'abcd', 'Bob', 'Lee', 'blee@localhost.localdomain', 1);

insert into unit_supervisors (unit_id, supervisor_id) values (1, 2);
insert into unit_technicians (unit_id, technician_id) values (1, 3);
insert into unit_technicians (unit_id, technician_id) values (1, 4);

insert into users (id, type, username, password, first_name, last_name, email) values
    (5, 'REGULAR', 'jojo', 'abcd', 'Joseph', 'Joestar', 'jojo@localhost.localdomain');

insert into tickets (id, created_by, created_for_name, created_for_email, subject, details, unit_id) values
    (1, 5, 'Joseph Joestar', 'jojo@localhost.localdomain', 'Projector Malfunction',
        'The projector is broken in room A220.', 1);
insert into tickets (id, created_by, created_for_name, created_for_email, subject, details, unit_id) values
    (2, 5, 'Joseph Joestar', 'jojo@localhost.localdomain', 'Equipment for EE Senior Design Project',
        'One of the EE senior design projects needs some equipment.', 1);

insert into ticket_technicians (ticket_id, technician_id) values (1, 3);
insert into ticket_technicians (ticket_id, technician_id) values (2, 3);
insert into ticket_technicians (ticket_id, technician_id) values (2, 4);
