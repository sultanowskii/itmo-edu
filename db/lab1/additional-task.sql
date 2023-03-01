--- Get all humans that participated in adventure with risk.id=3
SELECT humans.id, humans.name FROM humans JOIN adventures_humans ON adventures_humans.humans_id = humans.id JOIN adventures ON adventures.id = adventures_humans.adventures_id JOIN risks ON risks.adventures_id = adventures.id WHERE risks.id = 3;
