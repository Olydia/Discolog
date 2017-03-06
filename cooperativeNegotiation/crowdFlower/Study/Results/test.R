my_09rray <- read.table(file.choose(), header = T, sep = ";")
attach(my_09rray)

p1<- wilcox.test(H1_09, H1_07, mu=0, alt ="two.sided", paired =T, conf.int= T, conf.level = 0.8, exact=F, correct = F)
print(p1)

p2<-  wilcox.test(H2_09, H2_07, mu=0, alt ="two.sided", paired =T, conf.int= T, conf.level = 0.8, exact=F, correct = F)
print(p2)

p3 <- wilcox.test(H3_09, H3_07, mu=0, alt ="two.sided", paired =T, conf.int= T, conf.level = 0.8, exact=F, correct = F)
print(p3)

p4 <-  wilcox.test(H4_09, H4_07, mu=0, alt ="two.sided", paired =T, conf.int= T, conf.level = 0.8, exact=F, correct = F)
print(p4)
