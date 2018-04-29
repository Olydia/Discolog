arthur <- function(donne2){
  
  a1 <- abs(donne2$H1 - donne2$X__1)
  
  a2 <- abs(donne2$H2 - donne2$X__2)
  
  a3 <- abs(donne2$H1 - donne2$X__1)
  
  a4 <- abs(donne2$H4 - donne2$X__4)

  cat("H1 ;", mean(a1), "\n")
  cat("H2 ;", mean(a2), "\n")
  cat("H3 ;",  mean(a3), "\n")
  cat("H4 ;", mean(a4), "\n")

  cat("H1 ;", sd(a1),"\n")
  cat("H2 ;", sd(a2), "\n")
  cat("H3 ;", sd(a3),  "\n")
  cat("H4 ;", sd(a4), "\n")
  
  p1<- wilcox.test(a1, mu=0, conf.int= T, conf.level = 0.7, exact=F, correct = F)
  cat("H1 ;","\n")
  print(p1)
  
  p2<-  wilcox.test(a2, mu=0, conf.int= T, conf.level = 0.7, exact=F, correct = F)
  cat("H2 ;","\n")
  print(p2)
  p3 <- wilcox.test(a3, mu=0, conf.int= T, conf.level = 0.7, exact=F, correct = F)
  cat("H3 ;", "\n")
  print(p3)
  
  p4 <-  wilcox.test(a4, mu=0, conf.int= T, conf.level = 0.7, exact=F, correct = F)
  cat("H4 ;","\n")
  print(p4)
}