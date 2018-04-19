#file path + name
pValue <- function(xlsFile, sheet){
  
  my_array<- read_xlsx(xlsFile,sheet = sheet)
  noms <-names(my_array)
  i = 1;
  
  p1<- wilcox.test(H1, X__1, mu=0, alt ="two.sided", paired =T, conf.int= T, conf.level = 0.95, exact=F, correct = F)
  print(p1)
  
  p2<-  wilcox.test(H2, X__2, mu=0, alt ="two.sided", paired =T, conf.int= T, conf.level = 0.95, exact=F, correct = F)
  print(p2)
  
  p3 <- wilcox.test(H3, X__3, mu=0, alt ="two.sided", paired =T, conf.int= T, conf.level = 0.95, exact=F, correct = F)
  print(p3)
  
  p4 <-  wilcox.test(H4, X__4, mu=0, alt ="two.sided", paired =T, conf.int= T, conf.level = 0.95, exact=F, correct = F)
  print(p4)
}




# print(p)


