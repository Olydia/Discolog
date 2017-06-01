aa <- read.table(file.choose(), header = T, sep = ";")
attach(aa)

p1<- wilcox.test(H1.09.09B., H1.07.07B., mu=0, alt ="two.sided", paired =F, conf.int= T, conf.level = 0.5, exact=F, correct = F)
print(p1)

p2<-  wilcox.test(H2.09.09B., H2.07.07B., mu=0, alt ="two.sided", paired =F, conf.int= T, conf.level = 0.5, exact=F, correct = F)
print(p2)

p3 <- wilcox.test(H3.09.09B., H3.07.07B., mu=0, alt ="two.sided", paired =F, conf.int= T, conf.level = 0.5, exact=F, correct = F)
print(p3)

p4 <-  wilcox.test(H4.09.09B., H4.07.07B., mu=0, alt ="two.sided", paired =F, conf.int= T, conf.level = 0.5, exact=F, correct = F)
print(p4)

# ----------------------------------------------------------------------------



ks.test(H2.09.09B., H2.07.07B., alt ="two.sided")
ks.test(H3.09.09B., H3.07.07B. ,alt ="two.sided")

ks.test(H4.09.09B., H4.07.07B., alt ="two.sided")
