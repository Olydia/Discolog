# correlation for non parametric data
correlation <- function(csvfile){
  
  my_array <- read.table(csvfile, header = T, sep = ";")
  attach(my_array)
  #kendall test 
  res1 <- cor.test(H1_A, H1_B,  method="kendall", exact= FALSE, conf.level = 0.95)
  print(res1)
  
  #spearman
  res2 <- cor.test(H1_A, H1_B,  method="spearman", exact= FALSE, conf.level = 0.95)
  print(res2)
  
  
  #kendall test 
  res1 <- cor.test(H2_A, H2_B,  method="kendall", exact= FALSE, conf.level = 0.95)
  print(res1)
  
  #spearman
  res2 <- cor.test(H2_A, H2_B,  method="spearman", exact= FALSE, conf.level = 0.95)
  print(res2)
  
  
  #kendall test 
  res1 <- cor.test(H3_A, H3_B,  method="kendall", exact= FALSE, conf.level = 0.95)
  print(res1)
  
  #spearman
  res2 <- cor.test(H3_A, H3_B,  method="spearman", exact= FALSE, conf.level = 0.95)
  print(res2)
  #kendall test 
  res1 <- cor.test(H4_A, H4_B,  method="kendall", exact= FALSE, conf.level = 0.95)
  print(res1)
  
  #spearman
  res2 <- cor.test(H4_A, H4_B,  method="spearman", exact= FALSE, conf.level = 0.95)
  print(res2)
}
