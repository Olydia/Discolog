comparaison <- function(donne1, donne2){
  
  b1 <- abs(donne1$H1 - donne1$X__1)
  a1 <- abs(donne2$H1 - donne2$X__1)
  
  b2 <- abs(donne1$H2 - donne1$X__2)
  a2 <- abs(donne2$H2 - donne2$X__2)
  
  b3 <- abs(donne1$H1 - donne1$X__1)
  a3 <- abs(donne2$H1 - donne2$X__1)
  
  b4 <- abs(donne1$H4 - donne1$X__4)
  a4 <- abs(donne2$H4 - donne2$X__4)
  
  print("moyenne; Bob; Arthur")
  cat("H1 ;", mean(b1), "; ", mean(a1), "\n")
  cat("H2 ;", mean(b2), "; " , mean(a2), "\n")
  cat("H3 ;", mean(b3), "; " , mean(a3), "\n")
  cat("H4 ;", mean(b4), "; " , mean(a4), "\n")
  
  cat("ecart type; Bob; Arthur" , "\n")
  cat("H1 ;", sd(b1), "; ", sd(a1), "\n")
  cat("H2 ;", sd(b2), "; " , sd(a2), "\n")
  cat("H3 ;", sd(b3), "; " , sd(a3), "\n")
  cat("H4 ;", sd(b4), "; " , sd(a4), "\n")
  
  p1<- wilcox.test(b1, a1, mu=0, alt ="two.sided", paired =T, conf.int= T, conf.level = 0.95, exact=F, correct = F)
  cat("H1 ;","\n")
  print(p1)
  
  p2<-  wilcox.test(b2, a2, mu=0, alt ="two.sided", paired =T, conf.int= T, conf.level = 0.95, exact=F, correct = F)
  cat("H2 ;","\n")
  print(p2)
  p3 <- wilcox.test(b3, a3, mu=0, alt ="two.sided", paired =T, conf.int= T, conf.level = 0.95, exact=F, correct = F)
  cat("H3 ;", "\n")
  print(p3)
  
  p4 <-  wilcox.test(b4, a4, mu=0, alt ="two.sided", paired =T, conf.int= T, conf.level = 0.95, exact=F, correct = F)
  cat("H4 ;","\n")
  print(p4)
}