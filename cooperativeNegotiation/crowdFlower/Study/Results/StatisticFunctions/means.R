means<- function(csvname)
{
  
  my_array <- read.table(csvname, header = T, sep = ";")
  View(my_array)
  noms <-names(my_array)
  cat("Hypothese", ";" , "mean" , ";" , "standard deviation", "\n")
  for(j in 1:dim(my_array)[2])
  {
     value<- my_array[,j]
     cat(noms[j], ";" , mean(value), ";" , sd(value), "\n")

  }
}
