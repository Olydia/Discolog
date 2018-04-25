basic <- function(donne)
{
  noms <-names(donne)
  print(noms)
  i = 1
  for(col in donne)
  {
   print(noms[i])
   normality1 <- shapiro.test(col)
   print(normality1)
   normality2 <- ad.test(col)
   print(normality2)
    # cat(noms[i],";", mean(col), "; " , sd(col), "\n")
    # 
    # i = i+1
  }
}
