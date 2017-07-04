effectSize <- function(a, b){
  # V <-   T + <-  la somme des rangs des observations pour lesquelles la différence est positive
  # pour calculer la sommes des rangs dont la différence est négative
  # T- <-  n(n+1)/2 - T+
  # T <-  min(T-, T+)
  diff <- c(a - b) #calculating the vector containing the differences
  diff <- diff[ diff!=0 ] #delete all differences equal to zero
  diff.rank <- rank(abs(diff)) #check the ranks of the differences, taken in absolute
  diff.rank.sign <- diff.rank * sign(diff) #check the sign to the ranks, recalling the signs of the values of the differences
  ranks.pos <- sum(diff.rank.sign[diff.rank.sign > 0]) #calculating the sum of ranks assigned to the differences as a positive, ie greater than zero
  ranks.neg <- -sum(diff.rank.sign[diff.rank.sign < 0])
  
  # print(ranks.pos)
  #  print(ranks.neg)
  
  W <-  min(ranks.pos, ranks.neg)
  cat("Original T",W,"\n")
 # W <-  wilcxonTest(a, b)
 # cat ("test T: " ,W ,"\n")
  n <- length(diff)
  cat ("longueur", n,"\n")
  # E[T]<-  n(n+1)/4
  E <-  n * (n+1)/4
  #cat ("longueur", E,"\n")
  
  # V[T] <-  n(n+1)(2n+1)/24
  Vi <-  n*(n+1)*((2* n) +1)
 # cat ("Vi", Vi,"\n")
  
  V <-  Vi/24
 # cat ("V", V,"\n")
  
  # Z <-  T- E[T] / square(V[T])
  Zi <-  W - E
 # cat ("Zi", Zi,"\n")
  
  Z <-  Zi/sqrt(V)
  cat ("Z", Z,"\n")
  
 # r = Z /sqrt(Na, Nb)
  N = length(a) + length(b)
  r = Z /sqrt(N)
  cat ("R", r,"\n")
  
  return (r)
}
  
