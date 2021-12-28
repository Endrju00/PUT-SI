(define
	(domain sokoban)
	(:requirements :adl)
	(:predicates
		(poziomo ?poz1 ?poz2)
		(pionowo ?poz1 ?poz2)
		(paczka ?poz)
		(robot ?poz)
		(cel ?poz)
	)
	; przejdz na inna pozycje
	(:action idz
		:parameters (?poz1 ?poz2)
		:precondition
		(and
			(robot ?poz1)
			(not (paczka ?poz2))
			(or (poziomo ?poz1 ?poz2) (pionowo ?poz1 ?poz2))
		)
		:effect
		(and
			(robot ?poz2)
			(not (robot ?poz1))
		)
	)
	(:action pchaj
		:parameters (?pozRobota ?staraPozPaczki ?nowaPozPaczki)
		:precondition
		(and
			(not (= ?pozRobota ?nowaPozPaczki))
			(robot ?pozRobota)
			(paczka ?staraPozPaczki)
			(not (paczka ?nowaPozPaczki))
			(or
				(and (poziomo ?pozRobota ?staraPozPaczki) (poziomo ?staraPozPaczki ?nowaPozPaczki))
				(and (pionowo ?pozRobota ?staraPozPaczki) (pionowo ?staraPozPaczki ?nowaPozPaczki))
			)
		)
		:effect
		(and
			(robot ?staraPozPaczki)
			(paczka ?nowaPozPaczki)
			(not (robot ?pozRobota))
			(not (paczka ?staraPozPaczki))	
		)
	)	
)