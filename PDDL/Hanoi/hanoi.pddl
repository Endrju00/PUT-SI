(define
	(domain hanoi)
	(:requirements :adl)
	(:types disk rod)
	(:constants x y z - rod)
	(:predicates
		(mniejszy ?mniejszy ?wiekszy - disk)
		(napaliku ?palik - rod ?dysk - disk)
	)

	(:action przesun-na-pusty
		:parameters (?zpalika ?napalik - rod ?dysk - disk)
		:precondition
		(and
			(napaliku ?zpalika ?dysk)
			(not (exists (?innydysk - disk) (napaliku ?napalik ?innydysk)))
			(not (exists (?innydysk2 - disk) (and (mniejszy ?innydysk2 ?dysk) (napaliku ?zpalika ?innydysk2))))
		)
		:effect
		(and
			(napaliku ?napalik ?dysk)
			(not (napaliku ?zpalika ?dysk))
		)
	)
	(:action przesun
		:parameters (?zpalika ?napalik - rod ?dyskzpalika ?dysknapaliku - disk)
		:precondition
		(and
			(napaliku ?zpalika ?dyskzpalika)
			(napaliku ?napalik ?dysknapaliku)
			(not (exists (?innydysk - disk) (and (mniejszy ?innydysk ?dyskzpalika) (napaliku ?zpalika ?innydysk))))
			(not (exists (?innydysk2 - disk) (and (mniejszy ?innydysk2 ?dysknapaliku) (napaliku ?napalik ?innydysk2))))
			(mniejszy ?dyskzpalika ?dysknapaliku)
		)
		:effect
		(and
			(not (napaliku ?zpalika ?dyskzpalika))
			(napaliku ?napalik ?dyskzpalika)
		)
	)	
)