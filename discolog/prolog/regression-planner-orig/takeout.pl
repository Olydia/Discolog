takeout(X,[X|R],R).
takeout(X,[F|R],[F|S]) :- takeout(X,R,S).