#file path + name
pValue <- function(csvfile){
  
  my_array <- read.table(csvfile, header = T, sep = ";")
  attach(my_array)
  p1<- wilcox.test(H1_A, H1_B, mu=0, alt ="two.sided", paired =T, conf.int= T, conf.level = 0.95, exact=F, correct = F)
  print(p1)
  
  p2<-  wilcox.test(H2_A, H2_B, mu=0, alt ="two.sided", paired =T, conf.int= T, conf.level = 0.95, exact=F, correct = F)
  print(p2)
  
  p3 <- wilcox.test(H3_A, H3_B, mu=0, alt ="two.sided", paired =T, conf.int= T, conf.level = 0.95, exact=F, correct = F)
  print(p3)
  
  p4 <-  wilcox.test(H4_A, H4_B, mu=0, alt ="two.sided", paired =T, conf.int= T, conf.level = 0.95, exact=F, correct = F)
  print(p4)
}



    
# print(p)

  
  