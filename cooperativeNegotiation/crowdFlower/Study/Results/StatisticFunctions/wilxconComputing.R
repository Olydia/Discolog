wilcxonTest<- function(a, b){
  diff <- c(a - b) #calculating the vector containing the differences
  diff <- diff[ diff!=0 ] #delete all differences equal to zero
  diff.rank <- rank(abs(diff)) #check the ranks of the differences, taken in absolute
  diff.rank.sign <- diff.rank * sign(diff) #check the sign to the ranks, recalling the signs of the values of the differences
  ranks.pos <- sum(diff.rank.sign[diff.rank.sign > 0]) #calculating the sum of ranks assigned to the differences as a positive, ie greater than zero
  ranks.neg <- -sum(diff.rank.sign[diff.rank.sign < 0])
  
 # print(ranks.pos)
#  print(ranks.neg)
  
  Tv <-  min(ranks.pos, ranks.neg)
  cat("Original T",Tv,"\n")
  return (Tv)
  
}
