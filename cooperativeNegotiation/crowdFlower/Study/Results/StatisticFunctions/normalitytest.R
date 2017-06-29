normalityTest <- function(csvname)
{
  my_array <- read.table(csvname, header = T, sep = ";")
  View(my_array)
  noms <-names(my_array)
  for(j in 1:dim(my_array)[2])
  {
    
    value<- my_array[,j]
    normality1 <- shapiro.test(value)
    cat("1." , noms[j], ";")
    print(normality1)
    
    normality2 <- ad.test(value)
    cat("2." , noms[j], ";")
    print(normality2)
    

  }
}