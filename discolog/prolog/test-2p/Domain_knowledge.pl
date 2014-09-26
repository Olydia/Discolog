%strips_holds(p1,init).

strips_achieves(init,X) :-
   strips_holds(X,init).

%test1(Plan):- strips_solve([p2],10,Plan).

strips_unsatisfiable(_) :- fail.

% =============================================================
% =============================================================
% =============================================================
% =============================================================
% =============================================================
% =============================================================
% =============================================================


% Computational Intelligence: a logical approach. 
% Prolog Code.
% A REGRESSION PLANNER FOR ACTIONS IN STRIPS NOTATION
% WITH LOOP DETECTION + HEURISTIC INFORMATION ON UNSATISFIABLE GOALS
% Copyright (c) 1998, Poole, Mackworth, Goebel and Oxford University Press.

%:- op(1200,xfx, <-). % force the non-instanciation of x1 and x2 befor computing the implication
	% but what is this <- operator in SWI ?
	% It is the <-(X,Y) op, which displays "no default open r session was found"
	% This operator seem to be used in the R library, which is a swipl library for statistical
	% processing (see "SWI prolog interface to R" and "R.pl"). The documentation pages mention
	% the A <- B operator (ah ah !). In R.pl it is defined as "r_in(A<-B)" (which, I assume,
	% calls the R code...). <- is the variable assignment operator in R.
	% Solution 1 = load the R.pl file and pray that it works in TU-Prolog
	%   does not work (compilation errors when loading in any interpreter other than SWI)
	% Solution 2 = understand what the call to <- does in R
	%   why the hell do they want to go into R and why this simple affectation works ?
	% Maybe the idea is just to force the affectation, to say that B is G un-instantiated
	% (with the 1200 to force the non-computation of G). Whereas the prolog "is" does the
	% evaluation of G, which is not what we want... Can we do this otherwise in prolog ?
	% I removed this <- line and simply use B as the head of the resulting list, and it works...

% N.B. we assume that conjunctions are represented as lists.
% '\=' is the object level not equal.
%:- op(700,xfx, \=).

% solve(G,AS,NS,P) is true if P is a plan to solve goal G that uses 
% less than NS steps.
% G is a list of atomic subgoals. AS is the list of ancestor goal lists.

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
%   writeln(['Trying ',A,' to solve ',G]),
%   writeln(['    New subgoals ',G1]),
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
strips_preconditions(a91,[p1]).
strips_achieves(a91,_).
strips_preconditions(a92,[_]).
strips_achieves(a92,_).
strips_preconditions(a93,[_]).
strips_achieves(a93,p3).
strips_preconditions(a101,[p1]).
strips_achieves(a101,_).
strips_preconditions(a102,[_]).
strips_achieves(a102,_).
strips_preconditions(a103,[_]).
strips_achieves(a103,p3).
strips_preconditions(a111,[p3]).
strips_preconditions(a112,[_]).
strips_achieves(a112,p10).
strips_preconditions(a113,[p10]).
strips_achieves(a113,_).
strips_preconditions(a121,[p3]).
strips_preconditions(a122,[_]).
strips_achieves(a122,_).
strips_preconditions(a123,[_]).
strips_achieves(a123,_).
strips_preconditions(a131,[_]).
strips_achieves(a131,_).
strips_preconditions(a132,[_]).
strips_preconditions(a133,[_]).
strips_achieves(a133,\+p2).
strips_preconditions(a141,[_]).
strips_achieves(a141,_).
strips_preconditions(a142,[_]).
strips_preconditions(a143,[_]).
strips_achieves(a143,p2).
strips_preconditions(a21,[p1]).
strips_achieves(a21,_).
strips_preconditions(a22,[_]).
strips_achieves(a22,_).
strips_preconditions(a23,[_]).
strips_achieves(a23,p17).
strips_preconditions(a31,[p1]).
strips_preconditions(a32,[_]).
strips_achieves(a32,\+p21).
strips_preconditions(a33,[p21]).
strips_achieves(a33,p17).
strips_preconditions(a41,[p17]).
strips_achieves(a41,_).
strips_preconditions(a42,[_]).
strips_achieves(a42,p24).
strips_preconditions(a43,[p24]).
strips_preconditions(a51,[p17]).
strips_achieves(a51,_).
strips_preconditions(a52,[_]).
strips_achieves(a52,_).
strips_preconditions(a53,[_]).
strips_achieves(a53,_).
strips_preconditions(a61,[_]).
strips_achieves(a61,_).
strips_preconditions(a62,[_]).
strips_achieves(a62,_).
strips_preconditions(a63,[_]).
strips_achieves(a63,\+p2).
strips_preconditions(a71,[_]).
strips_preconditions(a72,[_]).
strips_achieves(a72,p30).
strips_preconditions(a73,[p30]).
strips_achieves(a73,p2).
strips_preconditions(r13,[p1]).
strips_achieves(r13,cr13).
strips_preconditions(r3,[p1]).
strips_achieves(r3,cr3).
strips_preconditions(r4,[p1]).
strips_achieves(r4,cr4).
strips_preconditions(r5,[p1]).
strips_achieves(r5,cr5).
strips_preconditions(r7,[p1]).
strips_achieves(r7,cr7).
strips_preconditions(r13,[p1]).
strips_achieves(r13,cr13).
strips_preconditions(r3,[p1]).
strips_achieves(r3,cr3).
strips_preconditions(r4,[p1]).
strips_achieves(r4,cr4).
strips_preconditions(r5,[p1]).
strips_achieves(r5,cr5).
strips_preconditions(r7,[p1]).
strips_achieves(r7,cr7).
strips_primitive(p2).
strips_primitive(p30).
strips_primitive(cr5).
strips_primitive(p24).
strips_primitive(p17).
strips_primitive(cr4).
strips_primitive(p3).
strips_primitive(p1).
strips_primitive(p10).
strips_primitive(cr13).
strips_primitive(cr3).
strips_primitive(cr7).
strips_primitive(p21).
