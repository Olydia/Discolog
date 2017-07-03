#file path + name
fullCalcul <- function(csvfile){
  
  my_array <- read.table(csvfile, header = T, sep = ";")
  attach(my_array)
  
  value1 <- effectSize(H1_A, H1_B)
  cat("H1",  value1,"\n")

  value2 <- effectSize(H2_A, H2_B)
  cat ("H2", value2,"\n")
  
  
  value3 <- effectSize(H3_A, H3_B)
  cat("H3",value3,"\n")
  
  
 value4 <- effectSize(H4_A, H4_B)
 cat("H4", value4,"\n")
}
