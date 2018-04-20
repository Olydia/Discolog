#file path + name
pValue <- function(xlsFile, sheet){
  
  m<- read_xlsx(xlsFile,sheet = sheet)
  i = 1;
  
  p1<- wilcox.test(m$H1, m$X__1, mu=0, alt ="two.sided", paired =T, conf.int= T, conf.level = 0.95, exact=F, correct = F)
  print(p1)
  
  p2<-  wilcox.test(m$H2, m$X__2, mu=0, alt ="two.sided", paired =T, conf.int= T, conf.level = 0.95, exact=F, correct = F)
  print(p2)
  
  p3 <- wilcox.test(m$H3, m$X__3, mu=0, alt ="two.sided", paired =T, conf.int= T, conf.level = 0.95, exact=F, correct = F)
  print(p3)
  
  p4 <-  wilcox.test(m$H4, m$X__4, mu=0, alt ="two.sided", paired =T, conf.int= T, conf.level = 0.95, exact=F, correct = F)
  print(p4)
}




# print(p)


