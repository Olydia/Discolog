%% TEST pour le planner regstrips.pl

% preconditions(action(params), [préconditions]
% achieves(action(params),move(Ag,Pos,Pos_1), effetpositif).
% deletes(move(Ag,Pos,Pos_1), effetnegatif).

% strips_rule(Action, Precondlist, AddList, DelList)
:- consult('regr_strips.pl').

% charger
preconditions(charger(F,P,A),[at(F,A),at(P,A),avion(P),fret(F),aeroport(A)]).
achieves(charger(F,P,_),on(F,P)).
deletes(charger(F,_,A),at(F,A)).

% decharger
preconditions(decharger(F,P,A),[on(F,P),at(P,A),avion(P),fret(F),aeroport(A)]).
achieves(decharger(F,_,A),at(F,A)).
deletes(decharger(F,P,_),on(F,P)).

% voler
preconditions(voler(P,D,G),[at(P,D),avion(P),aeroport(D),aeroport(G)]).
achieves(voler(P,_,G),at(P,G)).
deletes(voler(P,D,_),at(P,D)).

primitive(at(_,_)).
primitive(on(_,_)).
primitive(avion(_)).
primitive(fret(_)).
primitive(aeroport(_)).

holds(avion(p),init).
holds(fret(f),init).
holds(aeroport(cdg),init).
holds(aeroport(jfk),init).

holds(at(p,cdg),init).
holds(at(f,cdg),init).



inconsistent(on(Y,X), on(Z,X)) :- not(Z=Y).
inconsistent(at(Y,X), at(Z,X)) :- not(Z=Y).
inconsistent(on(Y,X), on(Z,X)) :- not(Z=Y).
inconsistent(at(Y,X), on(Y,Z)).

achieves(init,X) :-
   holds(X,init).

test1(Plan):-solve([at(f,jfk)],6,Plan).