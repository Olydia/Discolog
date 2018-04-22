similarite<-function (donne1){
  
  print(donne1$H2)
  d1 <- abs(donne1$H1 - donne1$X__1)
  m1 <- mean(d1)
  print(m1)
  print(sd(d1))
  
 # print(wilcox.test(d1, mu = 0, alternative = "two.sided"))
  t<- shapiro.test(d1)
    # t.test(d1, mu=0, conf.level = 0.8)
 print(t)
  
  
  d2 <- abs(donne1$H2 - donne1$X__2)
  m2 <- mean(d2)
  print(m2)
  print(sd(d2))
  
 # print(wilcox.test(d2, mu = 0, alternative = "two.sided"))
  
 t2<- shapiro.test(d2)
    #t.test(d2, mu=0, conf.level = 0.8)
 print(t2)
  
  d3 <- abs(donne1$H3 - donne1$X__3)
  m3 <- mean(d3)
  print(m3)
  print(sd(d3))
  
  #print(wilcox.test(d3, mu = 0, alternative = "two.sided"))
  
  t3<-  shapiro.test(d3)
    #t.test(d3, mu=0, conf.level = 0.8)
  print(t3)
  
  d4 <- abs(donne1$H4 - donne1$X__4)
  m4 <- mean(d4)
  print(m4)
  print(sd(d4))
  
  #print(wilcox.test(d4, mu = 0, alternative = "two.sided"))
  
  t4<- shapiro.test(d4)
    #t.test(d4, mu=0, conf.level = 0.8)
  print(t4)
  
}
