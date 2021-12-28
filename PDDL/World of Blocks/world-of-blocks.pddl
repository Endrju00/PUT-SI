(define
	(domain world-of-blocks)
	(:requirements :adl)
	(:predicates
		(on-top ?x ?y)
		(on-floor ?x)
		(clear ?x)
		(holding ?x)
	)
	; przesuñ paczkê na pod³ogê
	(:action podnies-z-paczki
		:parameters (?x ?z)
		:precondition
		(and
			(clear ?x)
			(on-top ?x ?z)
			(not (exists (?p) (holding ?p)))
		)
		:effect
		(and
			(not (on-top ?x ?z))
			(clear ?z)
			(holding ?x)
		)
	)
	; opusc na paczke
	(:action opusc-na-paczke
		:parameters (?y ?x)
		:precondition
		(and
			(holding ?x)
			(clear ?y)
			(not (= ?x ?y))
		)
		:effect
		(and
			(on-top ?x ?y)
			(not (clear ?y))
			(not (holding ?x))
		)
	)
	; opuszczenie na podloge
	(:action opusc-na-podloge
	    :parameters (?x)
	    :precondition
	    (and
	        (holding ?x)
	    )
	    :effect
	    (and
	        (on-floor ?x)
	        (not (holding ?x))
	    )
	)
	; podnoszenie z podlogi
	(:action podnies-z-podlogi
	    :parameters (?x)
	    :precondition
	    (and
	        (not (exists (?p) (holding ?p)))
	        (on-floor ?x)
	        (clear ?x)
	    )
	    :effect
	    (and
	        (holding ?x)
	        (not (on-floor ?x))
	    )
 
	)
)