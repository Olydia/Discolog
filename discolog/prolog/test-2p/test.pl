discolog_init([T|R]) :- writeln(['assert(strips_holds(',T,' ,init)']), discolog_init(R).
discolog_init([]).