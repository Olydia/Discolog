
generate_plan(States,Goal,Plan,Plan):- sub_set(Goal, States), nl,
										write('The plan is: '),nl,
										print_solution(Plan),nl.
											   
											   
/* ------------------------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------- 	 plan modelisation	  -----------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

generate_plan(States,Goal,Action_seen,Plan):-  	action(Action,Precond,Delete_effect,Add_effect), 			/*  get the first action*/
												sub_set(Precond,States), 									/* 	Is the precondition are part of the states of world*/
												not(member(Action, Action_seen)), 							/* 	check if the action didn't executed before*/
												remove_list(States,Delete_effect,States1), 					/* 	delete the states canceled by the action*/
												append(States1,Add_effect,States_new), 	
												generate_plan(States_new,Goal,[Action|Action_seen],Plan).  	/* 	recurssive call
												


-----------------------------------------------------------------------------------------------------------------------------------------------------------------											   
----------------------------------------------------------------------------------   Action of the plan  --------------------------------------------------------
 action : (Name,
			 Preconditions, 
			 States that the action remove from the wolrd_states,
			 Effects added to the states of the world) 
			
-----------------------------------------------------------------------------------------------------------------------------------------------------------------*/


/*---------------------------------------------------------------------------------------------------------------------------------------------------------------
 ------------------------------------------------------------------------		Functions 		-----------------------------------------------------------------
# -----------------------------------------------------------------------------------------------------------------------------------------------------------------									 
 delete elements of List T from list L and return the result in list L1*/

remove_list(L,[],L).
remove_list(L,[X|T],S):- delete(L,X,Inter), remove_list(Inter,T,S).


/*If T is a sub_set of list L*/
sub_set([],_).
sub_set([X|R],S) :- member(X,S), subset(R,S).


print_solution([]).
print_solution([X | Y]):- print_solution(Y),nl,write(X).