pere(geoffroy,hubert).
pere(hubert,charles).
pere(charles,sylvie).
pere(charles,corinne).
mere(bertille,sylvie).
mere(bertille,corinne).
mere(sidonie,geoffroy).

/* Question 2 */
parent(X,Y) :- mere(X,Y).
parent(X,Y) :- pere(X,Y).
/* Peut aussi s'écrire parent(X,Y) :- mere(X,Y);pere(X,Y). */

grand_pere(X,Y) :- pere(X,Z), parent(Z,Y).

frere_ou_soeur(X,Y) :- parent(Z,X),parent(Z,Y).

/* Question 3 */

ancetre(X,Y) :- parent(X,Y).
ancetre(X,Y) :- parent(X,Z),ancetre(Z,Y).