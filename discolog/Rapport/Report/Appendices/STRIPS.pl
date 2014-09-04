strips_achieves(init,X) :-
   strips_holds(X,init).
%test1(Plan):- strips_solve([p2],10,Plan).
strips_unsatisfiable(_) :- fail.
% =============================================================
strips_solve(G,N,P) :-
   strips_solve(G,[G],N,P).

strips_solve(G,_,_,init) :-
   strips_solved(G).

strips_solve(G,AS,NAs,do(A,Pl)) :-
   NAs > 0,
   strips_satisfiable(G),
   strips_useful(G,A),
   strips_wp(G,A,G1),
   \+ strips_subgoal_loop(G1,AS),
   NA1 is NAs-1,
   strips_solve(G1,[G1|AS],NA1,Pl).

% strips_subgoal_loop(G,AS) is true if we are in a loop of subgoals to solve.
% This occurs if G is a more difficult to solve goal than one of its ancestors
strips_subgoal_loop(G1,AS) :-
    strips_grnd(G1), strips_member(An,AS), strips_subset(An,G1).

% strips_solved(G) is true if goal list G is true initially
strips_solved([]).
strips_solved([G|R]) :-
   strips_holds(G,init),
   strips_solved(R).

% satisfiable(G) is true if (based on a priori information) it is possible for
%  goal list G to be true all at once.
strips_satisfiable(G) :-
   \+ strips_unsatisfiable(G).

% strips_useful(G,A) is true if action A is useful to solve a goal in goal list G
% we try first those subgoals that do not hold initially
strips_useful([S|R],A) :-
   strips_holds(S,init),
   strips_useful(R,A).
strips_useful([S|_],A) :-
   strips_achieves(A,S).
strips_useful([S|R],A) :-
   \+ strips_holds(S,init),
   strips_useful(R,A).

% domain specific rule about what may be useful to solve even if it was true
%  initially. 
strips_useful(G,A) :-
   strips_member(S,G),
   strips_member(S,[handempty]), % handempty is the only such goal in this domain
   strips_holds(S,init),
   strips_achieves(S,A).

% strips_wp(G,A,G0) is true if G0 is the weakest precondition that needs to hold
% immediately before action A to ensure that G is true immediately after A
strips_wp([],A,G1) :-
   strips_preconditions(A,G),
   strips_filter_derived(G,[],G1).
strips_wp([S|R],A,G1) :-
   strips_wp(R,A,G0),
   strips_regress(S,A,G0,G1).

% strips_regress(Cond,Act,SG0,SG1) is true if regressing Cond through Act
% starting with subgoals SG0 produces subgoals SG1
strips_regress(S,A,G,G) :-
   strips_achieves(A,S).
strips_regress(S,A,G,G1) :-
   strips_primitive(S),
   \+ strips_achieves(A,S),
   \+ strips_deletes(A,S),
   strips_insert(S,G,G1).

strips_filter_derived([],L,L).
strips_filter_derived([G|R],L,[G|L1]) :-
   strips_primitive(G),
   strips_filter_derived(R,L,L1).
strips_filter_derived([A \= B | R],L,L1) :-
   dif(A,B),
   strips_filter_derived(R,L,L1).
% strips_filter_derived([G|R],L0,L2) :-
%   (G <- B),
%   strips_filter_derived(R,L0,L1),
%   strips_filter_derived(B,L1,L2).
strips_filter_derived([B|R],L0,L2) :-
   strips_filter_derived(R,L0,L1),
   strips_filter_derived(B,L1,L2).

strips_regress_all([],_,G,G).
strips_regress_all([S|R],A,G0,G2) :-
   strips_regress(S,A,G0,G1),
   strips_regress_all(R,A,G1,G2).
% =============================================================================
% strips_member(X,L) is true if X is a member of list L
strips_member(X,[X|_]).
strips_member(X,[_|L]) :-
   strips_member(X,L).

strips_notin(_,[]).
strips_notin(A,[B|C]) :-
   dif(A,B),
   strips_notin(A,C).

% strips_subset(L1,L2) is true if L1 is a subset of list L2
strips_subset([],_).
strips_subset([A|B],L) :-
   strips_member(A,L),
   strips_subset(B,L).

% writeln(L) is true if L is a list of items to be written on a line, followed by a newline.
writeln(L) :- \+ \+ (numbervars(L,0,_), writelnw(L) ).
writelnw([]) :- nl.
writelnw([H|T]) :- write(H), writeln(T).

% strips_insert(E,L0,L1) inserts E into list L0 producing list L1.
% If E is already a member it is not added.
strips_insert(A,[],[A]).
strips_insert(A,[B|L],[A|L]) :- A==B.
strips_insert(A,[B|L],[B|R]) :-
   \+ A == B,
   strips_insert(A,L,R).
strips_grnd(G) :-
   numbervars(G,0,_).