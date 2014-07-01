%% TEST pour le planner regstrips.pl

%% C EST CELUI-CI QUI MARCHE !

% preconditions(action(params), [préconditions]
% achieves(action(params),move(Ag,Pos,Pos_1), effetpositif).
% deletes(move(Ag,Pos,Pos_1), effetnegatif).

% strips_rule(Action, Precondlist, AddList, DelList)

% monkey-banana 1

preconditions(move(X,Y,_),[at(monkey,Y),at(X,Y)]).
achieves(move(X,_,Z),at(X,Z)).
achieves(move(_,_,Z),at(monkey,Z)).
deletes(move(X,Y,_),at(X,Y)).
deletes(move(_,Y,_),at(monkey,Y)).

primitive(at(_,_)).

holds(at(banana,a),init).
holds(at(monkey,b),init).
holds(at(box,c),init).
achieves(init,X) :-
   holds(X,init).

test1(Plan):-solve([at(box,a)],2,Plan).

