effectSize <- function(v, n){
  # V <-   T + <-  la somme des rangs des observations pour lesquelles la différence est positive
  # pour calculer la sommes des rangs dont la différence est négative
  # T- <-  n(n+1)/2 - T+
  # T <-  min(T-, T+)
  
  # E[T]<-  n(n+1)/4
  E <-  n * (n+1)/4
  print(E)
  
  # V[T] <-  n+(n+1)(2n+1)/24
  Vi <-  n+(n+1)*( (2* n) +1)
  V <-  Vi/24
  print(V)
  
  # Z <-  T- E[T] / square(V[T])
  Zi <-  T -E
  Z <-  Zi/sqrt(V)
  print(Z)
  
  
}
  
effectSize(860.5,100)
