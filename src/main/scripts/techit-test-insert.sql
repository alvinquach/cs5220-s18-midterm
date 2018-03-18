insert into units (id, name) values (1, 'TechOps');
insert into units (id, name) values (2, 'ITC');

-- All hash are bcrypt('abcd')
insert into users (id, type, username, hash, first_name, last_name, email) values
    (1, 'ADMIN', 'techit', '$2a$10$v2/oF1tdBlXxejoMszKW3eNp/j6x8CxSBURUnVj006PYjYq3isJjO',
        'System', 'Admin', 'techit@localhost.localdomain');
insert into users (id, type, username, hash, first_name, last_name, email, unit_id) values
    (2, 'SUPERVISOR', 'jsmith', '$2a$10$9PJIPq9PMYHd9L8kb66/Nuu7DDQqq29eOsVF1F8SnPZ2UfD6KC/ly',
        'John', 'Smith', 'jsmith@localhost.localdomain', 1);
insert into users (id, type, username, hash, first_name, last_name, email, unit_id) values
    (3, 'TECHNICIAN', 'jjim', '$2a$10$Q8G5BtMC.C5oonZzvBS.0usxJ2fpccf.I46pw3IGi.zorntvTSYbK',
        'Jimmy', 'Jim', 'jjim@localhost.localdomain', 1);
insert into users (id, type, username, hash, first_name, last_name, email, unit_id) values
    (4, 'TECHNICIAN', 'blee', '$2a$10$d8lhouzPhxZ.nLCaqjh5gevTyA3tZDUMwuy2WAsWAm.i/ag/btcxe',
        'Bob', 'Lee', 'blee@localhost.localdomain', 1);

insert into unit_supervisors (unit_id, supervisor_id) values (1, 2);
insert into unit_technicians (unit_id, technician_id) values (1, 3);
insert into unit_technicians (unit_id, technician_id) values (1, 4);

insert into users (id, type, username, hash, first_name, last_name, email) values
    (5, 'REGULAR', 'jojo', '$2a$10$Qn0U5T00Fkutb7UyBE9yg.aOBp2Z9OqN4SAWCSkdm4mrZmYIuYpq.',
        'Joseph', 'Joestar', 'jojo@localhost.localdomain');

insert into tickets (id, created_by, created_for_name, created_for_email, subject, details, unit_id) values
    (1, 5, 'Joseph Joestar', 'jojo@localhost.localdomain', 'Projector Malfunction',
        'The projector is broken in room A220.', 1);
insert into tickets (id, created_by, created_for_name, created_for_email, subject, details, unit_id) values
    (2, 5, 'Joseph Joestar', 'jojo@localhost.localdomain', 'Equipment for EE Senior Design Project',
        'One of the EE senior design projects needs some equipment.', 1);

insert into ticket_technicians (ticket_id, technician_id) values (1, 3);
insert into ticket_technicians (ticket_id, technician_id) values (2, 3);
insert into ticket_technicians (ticket_id, technician_id) values (2, 4);
